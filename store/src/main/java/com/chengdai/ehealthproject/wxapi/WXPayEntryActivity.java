package com.chengdai.ehealthproject.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseStoreActivity;
import com.chengdai.ehealthproject.model.common.model.pay.PaySucceedInfo;
import com.chengdai.ehealthproject.uitls.payutils.PayUtil;
import com.chengdai.ehealthproject.uitls.payutils.WXUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends BaseStoreActivity implements IWXAPIEventHandler {


    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixinpay);
        api = WXAPIFactory.createWXAPI(WXPayEntryActivity.this, WXUtils.SpId, false);
        // 将该app注册到微信
        api.registerApp(WXUtils.SpId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq baseReq) {
    }

    /*
  0  展示成功页面
  -1 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
  -2 用户取消
*/
    @Override
    public void onResp(BaseResp resp) {


        int code=resp.errCode;

        PaySucceedInfo paySucceedInfo=new PaySucceedInfo();

        paySucceedInfo.setPayType(PayUtil.WEIXINPAY);

        if(code==0)
        {   paySucceedInfo.setPaySucceed(true);

        }else if(code==-1)
        {
            paySucceedInfo.setPaySucceed(false);
        }

        paySucceedInfo.setCallType(PayUtil.WEIXINPAY);
        paySucceedInfo.setTag(PayUtil.CALLWXPAYTAG);
        EventBus.getDefault().post(paySucceedInfo);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        WXUtils.SpId=null;
        PayUtil.CALLWXPAYTAG=null;
    }



}
