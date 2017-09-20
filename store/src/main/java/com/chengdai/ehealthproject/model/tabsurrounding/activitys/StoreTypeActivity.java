package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityStoreTypeBinding;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.model.common.model.activitys.SearchActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.StoreTypeListAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.DZUpdateModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreListModel;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.chengdai.ehealthproject.weigit.appmanager.MyConfig.HOTELTYPE;

/**商户类型列表
 * Created by 李先俊 on 2017/6/12.
 */

public class StoreTypeActivity extends AbsStoreBaseActivity {

    private ActivityStoreTypeBinding mBinding;
    private StoreTypeListAdapter mAdapter;

    private int mStoreStart=1;

    private String mCategory; //接口查询大类

    private String mType;//接口查询小类


    /**
     * 打开当前页面
     * @param context
     * @param mCategory 大类
     * @param mType 小类
     */
    public static void open(Context context,String mCategory,String mType){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,StoreTypeActivity.class);
        intent.putExtra("mCategory",mCategory);
        intent.putExtra("mType",mType);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_store_type, null, false);


        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.store_type_list));

        if(getIntent() !=  null){
            mType=getIntent().getStringExtra("mType");
            mCategory=getIntent().getStringExtra("mCategory");
        }

        initViews();

        setSubLeftImgState(true);

        getStoreListRequest(this,1);
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout leftHeadView = (LinearLayout) inflater.inflate(R.layout.layout_search_no_input, null);//得到头部的布局

        leftHeadView.setOnClickListener(v -> {
            SearchActivity.open(this,"周边搜索",SearchActivity.type2);
        });

        mBinding.lvStoreType.addHeaderView(leftHeadView,null,false);

        mAdapter = new StoreTypeListAdapter(this,new ArrayList<>(),true);
        mBinding.lvStoreType.setAdapter(mAdapter);


        mBinding.lvStoreType.setOnItemClickListener((parent, view, position, id) -> {

            StoreListModel.ListBean model= (StoreListModel.ListBean) mAdapter.getItem(position-mBinding.lvStoreType.getHeaderViewsCount());

            LogUtil.E("type"+model.getType());

            if(HOTELTYPE.equals(model.getType())){  //酒店类型
                HoteldetailsActivity.open(this,model.getCode());
            }else{
                StoredetailsActivity.open(this,model.getCode());
            }
        });


        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(this));
        mBinding.springvew.setFooter(new DefaultFooter(this));


        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mStoreStart=1;
                getStoreListRequest(null,1);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mStoreStart++;
                getStoreListRequest(null,2);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });

    }


    /**
     * 获取商户列表
     */
    public void getStoreListRequest(Context context,int loadType){
        LocationModel locationModel =SPUtilHelpr.getLocationInfo();
        Map map=new HashMap();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("category", mCategory);
        map.put("type", mType);

        if(locationModel !=null){
            map.put("province", locationModel.getProvinceName());
            map.put("city", locationModel.getCityName());
//            map.put("area", locationModel.getAreaName());
            map.put("longitude", locationModel.getLatitude());
            map.put("latitude", locationModel.getLongitud());
        }else if(!TextUtils.isEmpty(SPUtilHelpr.getResetLocationInfo().getCityName())){
            map.put("city", SPUtilHelpr.getResetLocationInfo().getCityName());
        }

        map.put("status","2");
        map.put("start",mStoreStart+"");
        map.put("limit","10");
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);
        map.put("orderDir","asc");
        map.put("orderColumn","ui_order");
       mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreList("808217", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(context))

               .filter(storeListModel -> storeListModel!=null)

                .subscribe(storeListModel -> {

                    if(loadType==1){
                        if(storeListModel.getList()==null ){ //分页

                            return;
                        }
                        mAdapter.setData(storeListModel.getList());

                    }else{
                        if(storeListModel.getList()==null || storeListModel.getList().size()==0 ){ //分页
                            if(mStoreStart>1){
                                mStoreStart--;
                            }
                            return;
                        }

                        mAdapter.addData(storeListModel.getList());
                    }


                },Throwable::printStackTrace));


    }

    /**
     * 点赞效果刷新
     * @param
     */
    @Subscribe
    public void dzUpdate(DZUpdateModel dzUpdateModel){
        if(mAdapter!=null){
            mAdapter.setDzInfo(dzUpdateModel);
        }

    }

}
