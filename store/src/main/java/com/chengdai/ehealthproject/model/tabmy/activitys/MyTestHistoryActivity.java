package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.model.tabmy.model.MyTestHistoryListModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cdkj.baselibrary.utils.DateUtil.DEFAULT_DATE_FMT;


/**自测历史
 * Created by 李先俊 on 2017/6/16.
 */

public class MyTestHistoryActivity extends AbsStoreBaseActivity {

    private CommonRecycleerBinding mBinding;

    private int mPageStart=1;

    private EmptyWrapper mEmptyWrapper;

    private List<MyTestHistoryListModel.ListBean> mDatas;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,MyTestHistoryActivity.class);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.common_recycleer, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("自测历史");
        setSubLeftImgState(true);

        initViews();

        getTestHistoryListRequest(this);
    }

    private void initViews() {
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mBinding.springvew.setBackgroundColor(ContextCompat.getColor(this, R.color.gray));

        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(this));
        mBinding.springvew.setFooter(new DefaultFooter(this));

        mDatas=new ArrayList<>();

        mEmptyWrapper = new EmptyWrapper(new CommonAdapter<MyTestHistoryListModel.ListBean>(this,R.layout.item_my_test_history,mDatas) {
            @Override
            protected void convert(ViewHolder holder, MyTestHistoryListModel.ListBean listBean, int position) {
                if(listBean == null){
                    return;
                }

                holder.setText(R.id.tv_name,listBean.getTitle());
                holder.setText(R.id.tv_time, DateUtil.formatStringData(listBean.getCreateDatetime(),DEFAULT_DATE_FMT));
                holder.setText(R.id.tv_info,listBean.getContent());

            }

        });

        mEmptyWrapper.setEmptyView(R.layout.empty_view);
        mBinding.cycler.setAdapter(mEmptyWrapper);



        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getTestHistoryListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getTestHistoryListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });
    }

    public void getTestHistoryListRequest(Context c){

        Map<String,String> map=new HashMap<>();
        map.put("userId",SPUtilHelpr.getUserId());
        map.put("wjKind","questionare_kind_2");
        map.put("start",mPageStart+"");
        map.put("limit","10");

        mSubscription.add(RetrofitUtils.getLoaderServer().getTestHistoryList("621245", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(c))
                .subscribe(s -> {
                    if(mPageStart == 1){
                        if(s==null || s.getList()!=null){
                            mDatas.clear();
                            mDatas.addAll(s.getList());
                            mEmptyWrapper.notifyDataSetChanged();
                        }

                        if(mDatas.size()==0){
                            mEmptyWrapper.setEmptyView(R.layout.empty_view);
                        }

                    }else if(mPageStart >1){
                        if(s==null ||s.getList()==null || s.getList().size()==0){
                            mPageStart--;
                            return;
                        }
                        mDatas.addAll(s.getList());
                        mEmptyWrapper.notifyDataSetChanged();
                    }
                },Throwable::printStackTrace));

    }

}
