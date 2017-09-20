package com.chengdai.ehealthproject.model.healthstore;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cdkj.baselibrary.utils.ImgUtils;
import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentShopTabBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.activitys.WebViewActivity;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.SearchShopActivity;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopDetailsActivity;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopJfActivity;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopMenuSeletActivity;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopPayCarSelectActivity;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopTypeListAdapter;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.SurroundingStoreTypeAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.BannerModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.utils.DensityUtil;
import com.liaoinstan.springview.widget.SpringView;
import com.youth.banner.BannerConfig;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**商城
 * Created by 李先俊 on 2017/6/8.
 */

public class HealthStoreFragment extends BaseLazyFragment{

    private FragmentShopTabBinding mBinding;

    private boolean isCreate=false;
    private ShopTypeListAdapter mStoreTypeAdapter;
    private SurroundingStoreTypeAdapter mStoreMenuAdapter;

    private int mStoreStart=1;

    private List<String> bannerLinks=new ArrayList<>();


    /**
     * 获得fragment实例
     * @return
     */
    public static HealthStoreFragment getInstanse(){
        HealthStoreFragment fragment=new HealthStoreFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_shop_tab, null, false);

        isCreate=true;

        initBanner();

        initViews();

        initSpringViews();

        initTablayout();

        return mBinding.getRoot();

    }


    private void initTablayout() {
        mBinding.tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false);

        mBinding.recyclerGoodsType.setLayoutManager(linearLayoutManager);

        mBinding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { //点击第一次的tab选项回调
                switch (tab.getPosition()){

                    case 0:
                        getStoreListTypeRequest("2"); //今日特价
                        break;
                    case 1:
                        getStoreListTypeRequest("3");//人气推荐
                        break;
                    case 2:
                        getStoreListTypeRequest("4");//超值热卖
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {  //上一次的tab回调

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { // 再次点击同一个tab的回调

            }
        });


    }


    private void initSpringViews() {

        mBinding.springviewSurrounding.setType(SpringView.Type.FOLLOW);
        mBinding.springviewSurrounding.setGive(SpringView.Give.TOP);
        mBinding.springviewSurrounding.setHeader(new DefaultHeader(getActivity()));
        mBinding.springviewSurrounding.setFooter(new DefaultFooter(getActivity()));

        mBinding.springviewSurrounding.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mStoreStart=1;

                shopMenuRequest(null);
                bannerDataRequest(null);
                getStoreListRequest(null);
                getJfPicRequest(null);
                getStoreListTypeRequest("2");
                mBinding.tablayout.getTabAt(0).select();

                mBinding.springviewSurrounding.onFinishFreshAndLoad();
            }
            @Override
            public void onLoadmore() {
                mStoreStart++;
                getStoreListRequest(null);
                mBinding.springviewSurrounding.onFinishFreshAndLoad();
            }
        });
    }

    /**
     * 设置view
     */
    private void initViews() {

        mStoreMenuAdapter = new SurroundingStoreTypeAdapter(mActivity,new ArrayList<>());
        mBinding.gridStoreType.setAdapter(mStoreMenuAdapter);

        //列表
        mBinding.lvStoreList.setOnItemClickListener((parent, view, position, id) -> {
            ShopListModel.ListBean model= mStoreTypeAdapter.getItem(position-mBinding.lvStoreList.getHeaderViewsCount());
            ShopDetailsActivity.open(mActivity,model);

        });
        //菜单
        mBinding.gridStoreType.setOnItemClickListener((parent, view, position, id) -> {

            if(mStoreMenuAdapter!=null){
                StoreTypeModel model= (StoreTypeModel) mStoreMenuAdapter.getItem(position);
                if(model!=null){
                    ShopMenuSeletActivity.open(mActivity,model.getCode());
                }
            }

        });

        mStoreTypeAdapter = new ShopTypeListAdapter(mActivity,new ArrayList<>());
        mBinding.lvStoreList.setAdapter(mStoreTypeAdapter);

//搜索
        mBinding.search.linSerchtop.setOnClickListener(v -> {
            SearchShopActivity.open(mActivity,"商品搜索","请输入您感兴趣的商品");
        });

        //积分商城
        mBinding.imgJfshopInto.setOnClickListener(v -> {
            ShopJfActivity.open(mActivity);
        });

        //购物车
        mBinding.imgPayCar.setOnClickListener(v -> {
            if(!SPUtilHelpr.isLogin(mActivity)){
                return;
            }

            ShopPayCarSelectActivity.open(mActivity);
        });

        mBinding.search.editSerchView.setHint("请输入您感兴趣的商品");

    }


    private void initBanner() {

        mBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBinding.banner.setIndicatorGravity(BannerConfig.CENTER);

        mBinding.banner.setImageLoader(new GlideImageLoader());

        mBinding.banner.setOnBannerListener((position) -> {  //banner点击事件

            if(!TextUtils.isEmpty(bannerLinks.get(position))){
                WebViewActivity.open(mActivity,bannerLinks.get(position));
            }

        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.banner.stopAutoPlay();
    }
    @Override
    public void onResume() {
        super.onResume();

        if(getUserVisibleHint()){
            mBinding.banner.startAutoPlay();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mBinding.banner.stopAutoPlay();
    }

    @Override
    protected void lazyLoad() {
        if (isCreate){
            getAllData();
            isCreate=false;

        }

    }

    //获取所有接口数据
    private void getAllData() {

        getJfPicRequest(null);

        shopMenuRequest(null);

        bannerDataRequest(null);

        getStoreListTypeRequest("2");

        mBinding.tablayout.getTabAt(0).select();

        getStoreListRequest(mActivity);

        if(mBinding!=null && mBinding.banner!=null){
            mBinding.banner.start();
            mBinding.banner.startAutoPlay();
        }
    }


    @Subscribe
    public void HealthStoreFragmentRefresh(EventBusModel eventBusModel){
        if(eventBusModel == null)return;
        if(TextUtils.equals(eventBusModel.getTag(),"HealthStoreFragmentRefresh")){
            mStoreStart=1;
            getAllData();

        }
    }


    /**
     * 获取banner图片
     */
    private void bannerDataRequest(Context context) {

        Map map=new HashMap();

        map.put("location","1");//0周边1商城
        map.put("type","2"); //(1 菜单 2 banner 3 模块 4 引流)
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().GetBanner("806052", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(context))
                .filter(banners -> banners!=null)
                .map(banners -> {
                    List images=new ArrayList();
                    for(BannerModel ba:banners){
                        if(ba !=null ){
                            images.add(ba.getPic());
                            bannerLinks.add(ba.getUrl());
                        }

                    }
                    return images;

                })
                .subscribe(banners -> {

                    //设置图片集合
                    mBinding.banner.setImages(banners);
                    //banner设置方法全部调用完毕时最后调用
                    mBinding.banner.start();


                },Throwable::printStackTrace));
    }



    /**
     * 获取列表 （今日特价 人气推荐 超值热卖）
     * @param location
     */
    public void getStoreListTypeRequest(String location){
        Map map=new HashMap();

        map.put("kind","1"); //1 标准商城 2 积分商城
        map.put("status","3");//已上架
        map.put("start","1");
        map.put("limit","20");
        map.put("companyCode", MyConfigStore.COMPANYCODE);
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("orderDir","asc");
        map.put("orderColumn","order_no");
        map.put("location",location);  //1推荐 0普通 2 今日特价  3人气推荐 4超值热卖

        mSubscription.add(RetrofitUtils.getLoaderServer().GetShopList("808025",StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(null))

                .filter(storeListModel -> storeListModel!=null)

                .subscribe(storeListModel -> {

                    mBinding.recyclerGoodsType.setAdapter(new CommonAdapter<ShopListModel.ListBean>(mActivity,R.layout.item_shop_tab_goods_type,storeListModel.getList()) {
                        @Override
                        protected void convert(ViewHolder holder, ShopListModel.ListBean bean, int position) {

                            if(bean== null) return;
                            ImageView img=holder.getView(R.id.img_goods);
                            //设置imageView的宽高为屏幕的宽高1/3
                            DisplayMetrics dm = getResources().getDisplayMetrics();
                            FrameLayout.LayoutParams layoutParams= new FrameLayout.LayoutParams(dm.widthPixels/3- DensityUtil.dip2px(mContext,15),dm.widthPixels/3- DensityUtil.dip2px(mContext,15));

                            img.setLayoutParams(layoutParams);

                            ImgUtils.loadFraImgId(HealthStoreFragment.this, MyConfigStore.IMGURL+bean.getSplitAdvPic(),img);

                            img.setOnClickListener(v -> {
                                ShopDetailsActivity.open(mActivity,bean);
                            });

                            if(bean.getProductSpecsList()!=null && bean.getProductSpecsList().size()>0){
                                holder.setText(R.id.tv_price,mContext.getString(R.string.price_sing)+ MoneyUtils.showPrice(bean.getProductSpecsList().get(0).getPrice1()));
                            }
                        }

                    });



                },Throwable::printStackTrace));

    }



    /**
     * 获取商城列表
     * @param act
     */
    public void getStoreListRequest(Activity act){
        Map map=new HashMap();

        map.put("kind","1"); //1 标准商城 2 积分商城

        map.put("status","3");//已上架
        map.put("start",mStoreStart+"");
        map.put("limit","10");
        map.put("companyCode", MyConfigStore.COMPANYCODE);
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("orderDir","asc");
        map.put("orderColumn","order_no");
        map.put("location","1");  //1推荐 0普通

        mSubscription.add(  RetrofitUtils.getLoaderServer().GetShopList("808025",StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(act))

                .filter(storeListModel -> storeListModel!=null)


                .subscribe(storeListModel -> {
                    if(mStoreStart==1){
                        if(storeListModel.getList()==null){ //分页
                            return;
                        }
                        mStoreTypeAdapter.setData(storeListModel.getList());

                    }else{
                        if(storeListModel.getList()==null || storeListModel.getList().size()==0 ){ //分页
                            if(mStoreStart>1){
                                mStoreStart--;
                            }
                            return;
                        }

                        mStoreTypeAdapter.addData(storeListModel.getList());
                    }


                },Throwable::printStackTrace));

    }


    /**
     * 商城菜单
     * @param context
     */
    private void shopMenuRequest(Context context) {
        Map<String,String> map=new HashMap();

        map.put("parentCode","0");
        map.put("type","1");
        map.put("status","1");
        map.put("companyCode", MyConfigStore.COMPANYCODE);
        map.put("systemCode", MyConfigStore.SYSTEMCODE);


        mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreType("808007", StringUtils.getJsonToString(map))

                .compose(RxTransformerListHelper.applySchedulerResult(context))

                .filter(storeTypeModels -> storeTypeModels!=null)

                .subscribe(r -> {
                   mStoreMenuAdapter.setDatas(r);

                },Throwable::printStackTrace));

    }



    /**
     * 商城菜单
     * @param context
     */
    private void getJfPicRequest(Context context) {
        Map map=new HashMap();

        map.put("location","2");//0周边1商城 2
        map.put("type","2"); //(1 菜单 2 banner 3 模块 4 引流)
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().GetBanner("806052", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(context))
                .filter(banners -> banners!=null && banners.size()>0 && banners.get(0)!=null)
                .subscribe(banners -> {
                    ImgUtils.loadFraImgId(HealthStoreFragment.this, MyConfigStore.IMGURL+ banners.get(0).getPic(),mBinding.imgJfshopInto);

                },Throwable::printStackTrace));

    }




    @Override
    protected void onInvisible() {
        if(isCreate && mBinding!=null && mBinding.banner!=null){
            mBinding.banner.stopAutoPlay();
        }
    }



}
