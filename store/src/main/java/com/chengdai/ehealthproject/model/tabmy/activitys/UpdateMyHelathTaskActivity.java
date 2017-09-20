package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityAddHealthTaskBinding;
import com.chengdai.ehealthproject.model.tabmy.model.MyTaskListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.popwindows.SelectMinutesTimePopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**更新健康任务
 * Created by 李先俊 on 2017/6/26.
 */
public class UpdateMyHelathTaskActivity extends AbsStoreBaseActivity {

    private ActivityAddHealthTaskBinding mBinding;

    private MyTaskListModel mData;

    private List<String> mSelectWeekList=new ArrayList<>();

    private String mSelectTime;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,MyTaskListModel data){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,UpdateMyHelathTaskActivity.class);
        intent.putExtra("data",data);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_add_health_task, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("任务设置");

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
            if(mData!=null){
                if(mData.getHealthTask()!=null){
                    ImgUtils.loadImgURL(this, MyConfig.IMGURL+mData.getHealthTask().getLogo(),mBinding.imgTitle);
                    mBinding.tvName.setText(mData.getHealthTask().getName());
                }

                mSelectTime=mData.getTxDatetime();

                mBinding.tvSelectTime.setText(mSelectTime);

                mSelectWeekList.addAll(StringUtils.splitAsList(mData.getTxWeek(),","));
                for(String week:mSelectWeekList){
                    if(TextUtils.equals("2",week)){
                        mBinding.ckWeek1.setChecked(true);
                    }else if(TextUtils.equals("3",week)){
                        mBinding.ckWeek2.setChecked(true);
                    }else if(TextUtils.equals("4",week)){
                        mBinding.ckWeek3.setChecked(true);
                    }else if(TextUtils.equals("5",week)){
                        mBinding.ckWeek4.setChecked(true);
                    }else if(TextUtils.equals("6",week)){
                        mBinding.ckWeek5.setChecked(true);
                    }else if(TextUtils.equals("7",week)){
                        mBinding.ckWeek6.setChecked(true);
                    }else if(TextUtils.equals("1",week)){
                        mBinding.ckWeek7.setChecked(true);
                    }

                }

            }
        }

        setSubLeftImgState(true);

        setSubRightTitleAndClick("确定",v -> {
            if(TextUtils.isEmpty(mSelectTime)){
                showToast("请添加提醒时间");
                return;
            }
            if(mSelectWeekList.size() == 0){
                showToast("请添加提醒周期");
                return;
            }
            addTaskRequest();
        });


        mBinding.tvSelectTime.setOnClickListener(v -> {

            new SelectMinutesTimePopup(this).setSureOnClick(value -> {
                mSelectTime=value;
                mBinding.tvSelectTime.setText(value);
            }).showPopupWindow();

        });

        setWeekListener();

    }


    //添加任务请求
    public void addTaskRequest(){
        if(mData == null || mData.getHealthTask()==null){
            return;
        }
        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());

        map.put("code", mData.getCode());

        map.put("txDatetime", mSelectTime);

        map.put("txWeek", StringUtils.ListToString(mSelectWeekList,","));

        map.put("status", "1");//1 开启 0 关闭

        map.put("hTaskCode", mData.getHealthTask().getCode());//1 开启 0 关闭

      mSubscription.add( RetrofitUtils.getLoaderServer().updateHealthTask("621162",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
              .filter(codeModel -> codeModel!=null &&codeModel.isSuccess())
                .subscribe(codeModel -> {
                    showToast("修改任务成功");
                    finish();
                },Throwable::printStackTrace));

    }

    //星期选择
    private void setWeekListener() {
        mBinding.ckWeek1.setOnClickListener(v -> {
            if(mBinding.ckWeek1.isChecked()){
                mSelectWeekList.add("2");
            }else{
                mSelectWeekList.remove("2");
            }

        });
      mBinding.ckWeek2.setOnClickListener(v -> {
            if(mBinding.ckWeek2.isChecked()){
                mSelectWeekList.add("3");
            }else{
                mSelectWeekList.remove("3");
            }

        });
      mBinding.ckWeek3.setOnClickListener(v -> {
            if(mBinding.ckWeek3.isChecked()){
                mSelectWeekList.add("4");
            }else{
                mSelectWeekList.remove("4");
            }

        });
      mBinding.ckWeek4.setOnClickListener(v -> {
            if(mBinding.ckWeek4.isChecked()){
                mSelectWeekList.add("5");
            }else{
                mSelectWeekList.remove("5");
            }

        });

      mBinding.ckWeek5.setOnClickListener(v -> {
            if(mBinding.ckWeek5.isChecked()){
                mSelectWeekList.add("6");
            }else{
                mSelectWeekList.remove("6");
            }

        });
      mBinding.ckWeek6.setOnClickListener(v -> {
            if(mBinding.ckWeek6.isChecked()){
                mSelectWeekList.add("7");
            }else{
                mSelectWeekList.remove("7");
            }

        });

      mBinding.ckWeek7.setOnClickListener(v -> {
            if(mBinding.ckWeek7.isChecked()){
                mSelectWeekList.add("1");
            }else{
                mSelectWeekList.remove("1");
            }
        });

    }


}
