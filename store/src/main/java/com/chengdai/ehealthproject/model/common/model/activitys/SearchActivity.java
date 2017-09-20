package com.chengdai.ehealthproject.model.common.model.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySearchBinding;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.HoteldetailsActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.StoredetailsActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.StoreTypeListAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreListModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by 李先俊 on 2017/6/15.
 */

public class SearchActivity extends AbsStoreBaseActivity {


    private ActivitySearchBinding mBinding;
    private StoreTypeListAdapter mStoreTypeAdapter;

    private int mStoreStart=1;
    private int mType;

    public static int type =2;//民宿搜索
    public static int type2 =1;//周边搜索;

    /**
     * 打开当前页面
     * @param context
     * @param title 页面标题
     */
    public static void open(Context context, String title,int type){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,SearchActivity.class);

        intent.putExtra("title",title);
        intent.putExtra("type",type);

        context.startActivity(intent);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_search, null, false);
        addMainView(mBinding.getRoot());

        if(getIntent() != null){
            setTopTitle(getIntent().getStringExtra("title"));
            mType=getIntent().getIntExtra("type",2);
        }else{
            setTopTitle(getString(R.string.search));
        }
        setSubLeftImgState(true);

//        mBinding.search.editSerchView.setHint("请输入您感兴趣的商户");

        mStoreTypeAdapter = new StoreTypeListAdapter(this,new ArrayList<>(),true);
        mBinding.listlayout.listview.setAdapter(mStoreTypeAdapter);


        mBinding.listlayout.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.listlayout.springvew.setGive(SpringView.Give.TOP);
//        mBinding.listlayout.springvew.setHeader(new DefaultHeader(this));
        mBinding.listlayout.springvew.setFooter(new DefaultFooter(this));
        mBinding.listlayout.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {
                mStoreStart++;
                getStoreListRequest(null,mBinding.search.editSerchView.getText().toString());
            }
        });


/*      edit_search.setOnEditorActionListener((v, actionId, event) -> {
            final String str = v.getText().toString();
            if (StringUtil.isEmpty(str) || str.equals(pageState.keyWd)) {
                return false;
            }
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                enterSearch(str);
                hiddenBoard(edit_search);//隐藏键盘
                return true;
            }
            return false;
        });
*/

        mBinding.search.editSerchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                final String str = v.getText().toString();
                if (TextUtils.isEmpty(str) ) {
                    return false;
                }
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getStoreListRequest(SearchActivity.this,str);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);//隐藏键盘
                    return true;
                }
                return false;
            }
        });

        mBinding.listlayout.listview.setOnItemClickListener((parent, view, position, id) -> {
            StoreListModel.ListBean model= (StoreListModel.ListBean) mStoreTypeAdapter.getItem(position);

            if("2".equals(model.getLevel())){  //民宿类型
                HoteldetailsActivity.open(this,model.getCode());
            }else{
                StoredetailsActivity.open(this,model.getCode());
            }
        });

    }

    /**
     * 获取商户列表
     * @param act
     */
    public void getStoreListRequest(Activity act,String name){
        Map map=new HashMap();

        map.put("userId", SPUtilHelpr.getUserId());
        LocationModel  locationModel =SPUtilHelpr.getLocationInfo();
        if(locationModel !=null){
            map.put("province", locationModel.getProvinceName());
            map.put("city", locationModel.getCityName());
//            map.put("area", locationModel.getAreaName());
//            map.put("longitude", locationModel.getLatitude());
//            map.put("latitude", locationModel.getLongitud());
        }else if(!TextUtils.isEmpty(SPUtilHelpr.getResetLocationInfo().getCityName())){
            map.put("city", SPUtilHelpr.getResetLocationInfo().getCityName());
        }
        map.put("status","2");
        map.put("start",mStoreStart+"");
        map.put("limit","10");
        map.put("name",name);
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);
        map.put("orderDir","asc");
        map.put("orderColumn","ui_order");
        map.put("level",mType+""); //1 店铺 2民宿

        //点赞和取消点赞
        mSubscription.add(  RetrofitUtils.getLoaderServer().GetStoreList("808217", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(act))

                .filter(storeListModel -> storeListModel!=null)

                .subscribe(storeListModel -> {

                    if(mStoreStart ==1){

                        if(storeListModel.getList()==null ){ //分页
                            showToast("暂无搜索内容");
                            return;
                        }

                        if(storeListModel.getList().size()==0 ){ //分页
                            showToast("暂无搜索内容");
                        }

                        mStoreTypeAdapter.setData(storeListModel.getList());
                        return;
                    }

                    if(storeListModel.getList()==null || storeListModel.getList().size()==0 ){ //分页
                        if(mStoreStart>1){
                            mStoreStart--;
                        }else{
                            showToast("暂无搜索内容");
                        }
                        return;
                    }

                    mStoreTypeAdapter.addData(storeListModel.getList());

                },Throwable::printStackTrace));


    }

}
