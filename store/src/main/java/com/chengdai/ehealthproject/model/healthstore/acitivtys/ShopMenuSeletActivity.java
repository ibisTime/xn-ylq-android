package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySurroundingMenuselectBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.SurroundingMenuLeftAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**周边商店类型选择
 * Created by 李先俊 on 2017/6/10.
 */

public class ShopMenuSeletActivity extends AbsStoreBaseActivity {

    private ActivitySurroundingMenuselectBinding mBinding;

    private SurroundingMenuLeftAdapter mAdapterLeftMenuList;
    private SurroundingMenuLeftAdapter mAdapterRightMenuList;

    private String mTypeName;

    private String mCategory; //用户点击大类


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String typeName){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,ShopMenuSeletActivity.class);
        intent.putExtra("typeName",typeName);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_surrounding_menuselect, null, false);

        if(getIntent()!=null) {
            mTypeName= getIntent().getStringExtra("typeName");
            mCategory =mTypeName;
        }

        addMainView(mBinding.getRoot());

        setTopTitle("商城分类");

        setSubLeftImgState(true);

        initRightList();

        leftMenuRequest();

    }

    /**
     * 一级菜单请求
     */
    private void leftMenuRequest() {


        Map<String,String> map=new HashMap();

        map.put("parentCode","0");
        map.put("type","1");
        map.put("status","1");
        map.put("companyCode", MyConfigStore.COMPANYCODE);
        map.put("systemCode", MyConfigStore.SYSTEMCODE);

        //先请求一级菜单，再请求二级菜单
       mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreType("808007", StringUtils.getJsonToString(map))

                .compose(RxTransformerListHelper.applySchedulerResult(null))

               .filter(storeTypeModels -> storeTypeModels!=null)

                .flatMap(storeTypeModels -> {
                    LayoutInflater inflater = LayoutInflater.from(this);
                    LinearLayout  leftHeadView = (LinearLayout) inflater.inflate(R.layout.list_menu_left_head, null);//得到头部的布局

                    mBinding.listMenuLeft.addHeaderView(leftHeadView,null,false);

                   mAdapterLeftMenuList=new SurroundingMenuLeftAdapter(this,R.layout.item_textview_16sp,storeTypeModels,mTypeName);
                   mBinding.listMenuLeft.setAdapter(mAdapterLeftMenuList);
                   return Observable.fromIterable(storeTypeModels);})

                .filter(m -> m!=null && mTypeName.equals(m.getCode()))

                .map(storeTypeModel -> storeTypeModel.getCode())

                .subscribe(code-> {

                    rightMenuRequest(code);

                },Throwable::printStackTrace));



    }

    private void initRightList() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout rightHeadView = (LinearLayout) inflater.inflate(R.layout.list_menu_right_head, null);//得到头部的布局

        mBinding.listMenuRight.addHeaderView(rightHeadView,null,false);

        mAdapterRightMenuList =new SurroundingMenuLeftAdapter(this, R.layout.item_textview_14sp,new ArrayList<>(),"");

        mBinding.listMenuRight.setAdapter(mAdapterRightMenuList);

        mBinding.listMenuRight.setOnItemClickListener((parent, view, position, id) -> {

            int newPosition=position-mBinding.listMenuRight.getHeaderViewsCount();

            StoreTypeModel model= (StoreTypeModel) mAdapterRightMenuList.getItem(newPosition);

            if(model !=null){
                ShopTypeListSelectActivity.open(this,mCategory,model.getCode());
            }

        });

        mBinding.listMenuLeft.setOnItemClickListener((parent, view, position, id) -> {

            int newPosition=position-mBinding.listMenuLeft.getHeaderViewsCount();

            StoreTypeModel model= (StoreTypeModel) mAdapterLeftMenuList.getItem(newPosition);
            if( model != null) {
                mCategory=model.getCode();
                mAdapterLeftMenuList.setTypeName(model.getCode());
                rightMenuRequest(model.getCode());
            }

        });

    }


    /**
     * 二级菜单数据请求
     * @param parentCode
     */
    private void rightMenuRequest(String parentCode) {
        Map<String,String> map2=new HashMap();
        map2.put("parentCode",parentCode);
        map2.put("type","1");
        map2.put("status","1");
        map2.put("companyCode", MyConfigStore.COMPANYCODE);
        map2.put("systemCode", MyConfigStore.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().GetStoreType("808007", StringUtils.getJsonToString(map2))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .filter(storeTypeModels -> storeTypeModels!=null )
                .subscribe(names -> {
                    mAdapterRightMenuList.setDatas(names);

        },Throwable::printStackTrace));
    }
}
