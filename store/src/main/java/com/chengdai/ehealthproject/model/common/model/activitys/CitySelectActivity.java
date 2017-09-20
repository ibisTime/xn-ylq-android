package com.chengdai.ehealthproject.model.common.model.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityCitySelectBinding;
import com.chengdai.ehealthproject.model.common.model.CityModel;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.adapters.CityListAdapter;
import com.chengdai.ehealthproject.uitls.db.DBManager;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.jakewharton.rxbinding2.widget.RxTextView;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 李先俊 on 2017/6/16.
 */

public class CitySelectActivity extends AbsStoreBaseActivity {

    private ActivityCitySelectBinding mBinding;

    private List<CityModel> mShowData=new ArrayList<>();
    private CityListAdapter adapter;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,CitySelectActivity.class);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_city_select, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle(getString(R.string.txt_city_select));
        setSubLeftImgState(true);
        DBManager dbManager=new DBManager(this);
        dbManager.copyDBFile();
        mShowData=dbManager.getAllCities();
        adapter = new CityListAdapter(this,mShowData);
        mBinding.lvCitySelect.setAdapter(adapter);

        mBinding.sideBar.setOnTouchingLetterChangedListener(s -> {
            int position = adapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                mBinding.lvCitySelect.setSelection(position );
            }
        });

        //搜索
      mSubscription.add(RxTextView.textChanges(mBinding.editSerchView)
              .subscribe(s -> {
                    if(TextUtils.isEmpty(s.toString())){
                        adapter.setData(mShowData);
                    }else{
                        adapter.setData(dbManager.searchCity(s.toString()));
                    }
              },Throwable::printStackTrace));

           mBinding.lvCitySelect.setOnItemClickListener((parent, view, position, id) -> {
               if(adapter == null || adapter.getItem(position)==null){
                   return;
               }
               SPUtilHelpr.saveRestLocationInfo(adapter.getItem(position).getName());
               SPUtilHelpr.saveLocationInfo("");
               EventBus.getDefault().post(adapter.getItem(position));
               EventBusModel eventBusModel=new EventBusModel();
               eventBusModel.setTag("HealthManagerFragmentRefreshWeahter");//刷新定位天气
               eventBusModel.setEvInfo(adapter.getItem(position).getName());
               EventBus.getDefault().post(eventBusModel);
            finish();
        });


    }

}
