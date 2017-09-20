package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityAmountDetailsListBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.model.tabmy.model.JfDetailsListModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**我的余额
 * Created by 李先俊 on 2017/6/16.
 */

public class MyAmountActivity extends AbsStoreBaseActivity {

    private ActivityAmountDetailsListBinding mBinding;

    private String accountNumber;

    private EmptyWrapper mAdapter;

    private List<JfDetailsListModel.ListBean> mDatas;

    private  int mPageStart=1;

    private UserInfoModel mUserInfo;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String amountsum,String accountNumber,UserInfoModel userInfoModel){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,MyAmountActivity.class);
        intent.putExtra("amountsum",amountsum);//账户余额
        intent.putExtra("accountNumber",accountNumber);
        intent.putExtra("userInfoModel",userInfoModel);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_amount_details_list, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("我的健康币");
        setSubLeftImgState(true);

        if(getIntent()!=null){
            mBinding.tvJfnum.setText(getIntent().getStringExtra("amountsum"));
            accountNumber=getIntent().getStringExtra("accountNumber");
            mUserInfo=getIntent().getParcelableExtra("userInfoModel");
        }

        initViews();

        getJfDetailsList(this);
    }

    private void initViews() {

        //充值
        mBinding.tvRecharge.setOnClickListener(v -> {
//            RechargeActivity.open(this);
            RechargeTabActivity.open(this);
        });
        //提现
        mBinding.tvWithdrawal.setOnClickListener(v -> {

            if(mUserInfo==null){
                return;
            }

            if( TextUtils.equals("0",mUserInfo.getTradepwdFlag())) { //未设置支付密码

                showDoubleWarnListen("您还未设置支付密码,请先设置支付密码",view -> {
                    PayPwdModifyActivity.open(this,false,mUserInfo.getMobile());
                });

            }else if(TextUtils.equals("1",mUserInfo.getTradepwdFlag())){//设置过支付密码
                WithdrawalActivity.open(this,mBinding.tvJfnum.getText().toString(),accountNumber);
            }

        });

        mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(this));
        mBinding.springvew.setFooter(new DefaultFooter(this));

        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getJfDetailsList(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getJfDetailsList(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });

        mDatas=new ArrayList<>();

        mAdapter=new EmptyWrapper(new CommonAdapter<JfDetailsListModel.ListBean>(this, R.layout.item_jf_deails,mDatas) {
            @Override
            protected void convert(ViewHolder holder, JfDetailsListModel.ListBean listBean, int position) {
                if(listBean==null){
                    return;
                }
                holder.setText(R.id.tv_name,listBean.getBizNote());
                holder.setText(R.id.tv_sum, StringUtils.showJF(listBean.getTransAmount()));
                holder.setText(R.id.tv_time, DateUtil.formatStringData(listBean.getCreateDatetime(),DateUtil.DEFAULT_DATE_FMT));

            }
        });

        mAdapter.setEmptyView(R.layout.empty_view);

        mBinding.cycler.setAdapter(mAdapter);
    }

    public void getJfDetailsList(Context c) {

        Map<String,String> map=new HashMap<>();
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("token", SPUtilHelpr.getUserToken());
        map.put("accountNumber", accountNumber);
        map.put("accountType", "C");
        map.put("currency","CNY");
        map.put("start",mPageStart+"");
        map.put("limit","10");

       mSubscription.add( RetrofitUtils.getLoaderServer().getJFDetailsList("802520", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(c))
                .subscribe(s -> {

                    if(mPageStart == 1){
                        if(s==null||s.getList()!=null){
                            mDatas.clear();
                            mDatas.addAll(s.getList());
                            mAdapter.notifyDataSetChanged();
                        }

                    }else if(mPageStart >1){
                        if(s==null||s.getList()==null || s.getList().size()==0){
                            mPageStart--;
                            return;
                        }
                        mDatas.addAll(s.getList());
                        mAdapter.notifyDataSetChanged();
                    }

                },Throwable::printStackTrace));

    }

    @Subscribe
    public void MyAmountActivityEvent(EventBusModel e){
        if(e==null) return;

        if(TextUtils.equals(e.getTag(),"MyAmountActivityFinish")){//结束当前页
            finish();
        }else if(TextUtils.equals(e.getTag(),"SettingActivityUpdate_IsSetPwd")){
            if(mUserInfo!=null){
                mUserInfo.setTradepwdFlag("1");
            }
        }
    }

}
