package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityStoreTypeBinding;
import com.chengdai.ehealthproject.databinding.LayoutSearchNoInputBinding;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopTypeListAdapter;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**商店类型列表
 * Created by 李先俊 on 2017/6/12.
 */

public class ShopTypeListSelectActivity extends AbsStoreBaseActivity {

    private ActivityStoreTypeBinding mBinding;
    private ShopTypeListAdapter mAdapter;

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
        Intent intent=new Intent(context,ShopTypeListSelectActivity.class);
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

        getStoreListRequest(this);
    }

    private void initViews() {
        /*LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout leftHeadView = (LinearLayout) inflater.inflate(R.layout.layout_search_no_input, null);//得到头部的布局
*/

        LayoutSearchNoInputBinding mHeadViewBinding=DataBindingUtil.inflate( LayoutInflater.from(this),R.layout.layout_search_no_input,null,false);

        mHeadViewBinding.editSerchView.setHint("请输入您感兴趣的商品");
        mHeadViewBinding.editSerchView.setOnClickListener(v -> {
            SearchShopActivity.open(this,"商城搜索","请输入您感兴趣的商品");
        });

        mBinding.lvStoreType.addHeaderView(mHeadViewBinding.getRoot(),null,false);

        mAdapter = new ShopTypeListAdapter(this,new ArrayList<>());
        mBinding.lvStoreType.setAdapter(mAdapter);


        mBinding.lvStoreType.setOnItemClickListener((parent, view, position, id) -> {

            ShopListModel.ListBean model= (ShopListModel.ListBean) mAdapter.getItem(position-mBinding.lvStoreType.getHeaderViewsCount());

            ShopDetailsActivity.open(this,model);

 /*           if(HOTELTYPE.equals(model.getType())){  //酒店类型
                HoteldetailsActivity.open(this,model.getCode());
            }else{
                StoredetailsActivity.open(this,model.getCode());
            }*/

        });


        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(this));
        mBinding.springvew.setFooter(new DefaultFooter(this));


        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mStoreStart=1;
                getStoreListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mStoreStart++;
                getStoreListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });

    }


    /**
     * 获取商城列表
     * @param act
     */
    public void getStoreListRequest(Activity act){
        Map map=new HashMap();

        map.put("kind","1"); //1 标准商城 2 积分商城

        map.put("status","3");//已上架
        map.put("category",mCategory+"");
        map.put("type",mType+"");
        map.put("start",mStoreStart+"");
        map.put("limit","10");
        map.put("companyCode", MyConfigStore.COMPANYCODE);
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("orderDir","asc");
        map.put("orderColumn","order_no");

        mSubscription.add(  RetrofitUtils.getLoaderServer().GetShopList("808025", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(act))

                .filter(storeListModel -> storeListModel!=null)

                .subscribe(storeListModel -> {
                    if(mStoreStart==1){
                        if(storeListModel.getList()==null){ //分页
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

}
