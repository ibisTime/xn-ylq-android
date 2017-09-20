package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.model.tabmy.model.BankCardModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**银行卡列表
 * Created by 李先俊 on 2017/6/29.
 */

public class BackCardListActivity extends AbsStoreBaseActivity {

    private CommonRecycleerBinding mBinding;

    private EmptyWrapper mEmptyWrapper;

    private List<BankCardModel> mDatas;

    private boolean mIsselect;//用户打开类型是否是选择银行卡

    /**
     *
     * @param context
     */
    public static void open(Context context,boolean isSelect){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,BackCardListActivity.class);
        intent.putExtra("isSelect",isSelect);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.common_recycleer, null, false);
        addMainView(mBinding.getRoot());

        if(getIntent()!=null){
         mIsselect=getIntent().getBooleanExtra("isSelect",true);
        }

        setTopTitle("我的银行卡");

        setSubLeftImgState(true);

        mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mDatas=new ArrayList<>();
        mEmptyWrapper=new EmptyWrapper(new CommonAdapter<BankCardModel>(this,R.layout.item_my_bank_card,mDatas) {
            @Override
            protected void convert(ViewHolder holder, BankCardModel bankCardModel, int position) {
                if(bankCardModel == null) return;

                holder.setOnClickListener(R.id.img_back_bg,v -> {
                    if(mIsselect){//如果是选择银行卡
                        EventBus.getDefault().post(bankCardModel);
                        finish();
                    }else{
                        UpdateBackCardActivity.open(mContext,bankCardModel);
                    }
                });

                holder.setText(R.id.txt_name,bankCardModel.getBankName());
                if(!TextUtils.isEmpty(bankCardModel.getBankcardNumber()) && bankCardModel.getBankcardNumber().length()> 5){
                 holder.setText(R.id.txt_number,bankCardModel.getBankcardNumber().substring(bankCardModel.getBankcardNumber().length()-4,bankCardModel.getBankcardNumber().length()));
                }else{
                    holder.setText(R.id.txt_number,bankCardModel.getBankcardNumber());
                }
                int logoId = mContext.getResources().getIdentifier("logo_"+bankCardModel.getBankCode().toLowerCase(), "mipmap" , mContext.getPackageName());
                int backId = mContext.getResources().getIdentifier("back_"+bankCardModel.getBankCode().toLowerCase(), "mipmap" , mContext.getPackageName());

                ImgUtils.loadBankLogo(mContext,logoId,holder.getView(R.id.img_bankCart));
                ImgUtils.loadBankBg(mContext,backId,holder.getView(R.id.img_back_bg));


            }

        });

        mEmptyWrapper.setEmptyView(R.layout.empty_view);
        mBinding.cycler.setAdapter(mEmptyWrapper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListData();
    }

    public void getListData() {

        Map<String,String> object=new HashMap<>();

        object.put("systemCode", MyConfig.SYSTEMCODE);
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("userId",SPUtilHelpr.getUserId());
        object.put("start", "1");
        object.put("limit", "10");

        mSubscription.add(RetrofitUtils.getLoaderServer().getCardListData("802015", StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(myBankCardListMode -> myBankCardListMode!=null)
                .map(myBankCardListMode -> myBankCardListMode.getList())
                .subscribe(list -> {
                    mDatas.clear();
                    if(list !=null && list.size()>0){
                        mDatas.addAll(list);
                    }
                    mEmptyWrapper.notifyDataSetChanged();

                    if(mDatas.size()>0){
                        setSubRightTitleAndClick("",null);
                    }else{
                        setSubRightTitleAndClick("添加",v -> {
                            AddBackCardActivity.open(this);
                        });
                    }

                },Throwable::printStackTrace));

    }
}
