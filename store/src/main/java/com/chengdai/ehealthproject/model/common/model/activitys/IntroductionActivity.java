package com.chengdai.ehealthproject.model.common.model.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityIntroductionBinding;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.HashMap;
import java.util.Map;

/**介绍页面
 * Created by 李先俊 on 2017/6/16.
 */

public class IntroductionActivity extends AbsStoreBaseActivity {

    private ActivityIntroductionBinding mBinding;

    private String mCode;//用于请求

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String code,String title){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,IntroductionActivity.class);
        intent.putExtra("code",code);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_introduction, null, false);
        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);

        if(getIntent()!=null){
            mCode=getIntent().getStringExtra("code");
            setTopTitle(getIntent().getStringExtra("title"));
        }

        getDataReqeust();

    }

    public void getDataReqeust() {

        Map<String ,String > map=new HashMap<>();
        map.put("ckey",mCode);
        map.put("systemCode", MyConfigStore.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());
       mSubscription.add( RetrofitUtils.getLoaderServer().getInfoByKey("807717", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
               .filter(s-> s!=null && !TextUtils.isEmpty(s.getNote()))
                .subscribe(s -> {
                    mBinding.webView.getSettings().setDefaultTextEncodingName("UTF-8") ;
                    mBinding.webView.loadData(s.getNote(),"text/html;charset=UTF-8", "UTF-8");
//                    RichText.fromHtml(s.getNote()).into(mBinding.tvInfo);
                },Throwable::printStackTrace));
    }
}
