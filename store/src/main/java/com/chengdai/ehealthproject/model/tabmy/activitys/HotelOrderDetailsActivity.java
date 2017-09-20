package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.DateUtil;
import com.cdkj.baselibrary.utils.ImgUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHotelOrderDetails2Binding;
import com.chengdai.ehealthproject.model.tabmy.model.HotelOrderRecordModel;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;

import java.util.Date;

/**民宿订单详情查看 （）
 * Created by 李先俊 on 2017/6/15.
 */

public class HotelOrderDetailsActivity extends AbsStoreBaseActivity {

    private ActivityHotelOrderDetails2Binding mBinding;

    private  HotelOrderRecordModel.ListBean mShowData;


    private int mPayType=1;

    /**
     * 打开当前页面
     * @param context
     * @param data 要显示的数据
     */
    public static void open(Context context, HotelOrderRecordModel.ListBean data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HotelOrderDetailsActivity.class);

        intent.putExtra("data",data);

        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hotel_order_details2, null, false);

        if(getIntent()!=null){

            mShowData= getIntent().getParcelableExtra("data");

        }

        setTopTitle("订单详情");

        setSubLeftImgState(true);

        setShowData();

        addMainView(mBinding.getRoot());

        initPayTypeSelectState();

    }

    private void setShowData() {

        if(mShowData == null){
            return;
        }

        /*0 待支付 1 支付成功 2 已完成 3 已取消 */

       switch(mShowData.getStatus()){
           case "0":
//               mBinding.pay.linPay.setVisibility(View.VISIBLE);
//               mBinding.btnPay.setVisibility(View.VISIBLE);
               mBinding.tvOrderState.setText("待支付");
               break;
           case "1":
               mBinding.tvOrderState.setText("已支付");
               break;
           case "2":
               mBinding.tvOrderState.setText("已完成");
               break;
           case "3":
               mBinding.tvOrderState.setText("已取消");
               break;
       }


        mBinding.tvCode.setText(mShowData.getCode());


        if(!TextUtils.isEmpty(mShowData.getPayDatetime())) {
            mBinding.tvPayDate.setText(DateUtil.format( new Date(mShowData.getPayDatetime()), DateUtil.DATE_YMD));
        }



        if(mShowData.getProduct() !=null){
            mBinding.tvHotelSize.setText(mShowData.getProduct().getName());
            mBinding.tvHotelInfo.setText(mShowData.getProduct().getSlogan());
            ImgUtils.loadActImg(this, MyConfigStore.IMGURL+mShowData.getProduct().getSplitAdvPic(),mBinding.imgHotelInfo);

            if(!TextUtils.isEmpty(mShowData.getStartDate()) && !TextUtils.isEmpty(mShowData.getEndDate())){
                mBinding.tvHotelData.setText(
                        "入住:"+DateUtil.format( new Date(mShowData.getStartDate()),"MM月dd日")
                                +"离开:"+DateUtil.format( new Date(mShowData.getEndDate()),"MM月dd日")
                                +"  "+(DateUtil.getDatesBetweenTwoDate(new Date(mShowData.getStartDate()),new Date(mShowData.getEndDate())).size()-1)+"晚"
                );
            }
        }


    }

    /**
     * 初始化支付方式选择状态
     */
    private void initPayTypeSelectState() {

        ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.pay_select,mBinding.pay.imgBalace);
        ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.un_select,mBinding.pay.imgWeixin);
        ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.un_select,mBinding.pay.imgZhifubao);

        mBinding.pay.linBalace.setOnClickListener(v -> {
            mPayType=1;
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.pay_select,mBinding.pay.imgBalace);
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.un_select,mBinding.pay.imgWeixin);
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.un_select,mBinding.pay.imgZhifubao);
        });
        mBinding.pay.linWeipay.setOnClickListener(v -> {
            mPayType=2;
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.un_select,mBinding.pay.imgBalace);
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.pay_select,mBinding.pay.imgWeixin);
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.un_select,mBinding.pay.imgZhifubao);
        });

        mBinding.pay.linZhifubao.setOnClickListener(v -> {
            mPayType=3;
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.un_select,mBinding.pay.imgBalace);
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.un_select,mBinding.pay.imgWeixin);
            ImgUtils.loadActImgId(HotelOrderDetailsActivity.this,R.mipmap.pay_select,mBinding.pay.imgZhifubao);
        });


    }
    
}
