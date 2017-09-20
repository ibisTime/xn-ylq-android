package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.FragmentManagerFirstBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthcircle.adapters.LuntanListAdapter;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/21.
 */

public class MyLuntanActivity extends AbsStoreBaseActivity {

    private FragmentManagerFirstBinding mBinding;

//    private ActivityPersonalLuntanBinding mHeadViewBinding;

    private LuntanListAdapter mAdapter;

    private String mUserId;

    private int mPageStart=1;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String userid){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,MyLuntanActivity.class);
        intent.putExtra("userid",userid);
        context.startActivity(intent);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_manager_first, null, false);

        addMainView(mBinding.getRoot());


        if(getIntent()!=null){
            mUserId=getIntent().getStringExtra("userid");
        }

        setTopTitle("个人帖子");

        setSubLeftImgState(true);

        initSpringView();

        initListView();

        getDataRequest(this);

        getUserInfoRequest();

    }

    private void initListView() {

//       mHeadViewBinding=  DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_personal_luntan, null, false);
//
//        mBinding.lvManagerFirst.addHeaderView(mHeadViewBinding.getRoot(),null,false);
//        mBinding.lvManagerFirst.setEmptyView(mBinding.tvEmpty);
        mBinding.tvTitle.setVisibility(View.GONE);
        mAdapter=new LuntanListAdapter(this,new ArrayList<>(),true);
        mBinding.lvManagerFirst.setAdapter(mAdapter);
    }

    private void initSpringView() {

        mBinding.springview.setType(SpringView.Type.FOLLOW);
        mBinding.springview.setGive(SpringView.Give.TOP);
        mBinding.springview.setHeader(new DefaultHeader(this));
        mBinding.springview.setFooter(new DefaultFooter(this));
        mBinding.springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getDataRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getDataRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }
        });

    }

    @Subscribe
    public void PersonalLuntanActivityEnent(EventBusModel model){
        if(model== null){
            return;
        }
        if(TextUtils.equals(model.getTag(),"LuntanDzRefeshEnent") || TextUtils.equals(model.getTag(),"LuntanPinglunRefeshEnent")){//点赞刷新
            mPageStart=1;
            getDataRequest(null);

        }
    }

    /**
     * 获取帖子数据
     * @param context
     */
    public void getDataRequest(Context context){
        Map<String ,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("start",mPageStart+"");
        map.put("limit","10");

        mSubscription.add(RetrofitUtils.getLoaderServer().GetArticleLisData("621042", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(s -> {
                    if(mPageStart == 1){
                        if(s.getList()!=null){
                            mAdapter.setData(s.getList());
                        }
                    if(mAdapter.getCount()<=0){
                        showErrorView("暂无数据");
                    }

                    }else if(mPageStart >1){
                        if(s.getList()==null || s.getList().size()==0){
                            mPageStart--;
                            return;
                        }
                        mAdapter.addData(s.getList());
                    }

                },throwable -> {

                }));
    }


    /**
     * 获取用户信息请求
     */
    private  void getUserInfoRequest(){

/*
        Map<String,String> map=new HashMap<>();

        map.put("userId",mUserId);
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().GetUserInfo("805056", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))

                .filter(r -> r!=null)

                .subscribe(r -> {
//                    mHeadViewBinding.tvName.setText(r.getNickname());

                    if(r.getUserExt() == null) return;

                    ImgUtils.loadImgLogo(this, MyConfig.IMGURL+r.getUserExt().getPhoto(),mHeadViewBinding.imgLogo);
                    if(MyConfig.GENDERMAN.equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(this,R.mipmap.man,mHeadViewBinding.imgSex);
                    }else if (MyConfig.GENDERWOMAN.equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(this,R.mipmap.woman,mHeadViewBinding.imgSex);
                    }

                },Throwable::printStackTrace));
*/


    }

}
