package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHotelSelectBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.HotelSelectListAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelOrderCreateModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.popwindows.SelectUseDayPopup;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chengdai.ehealthproject.uitls.DateUtil.DATE_YMD;

/**房间选择
 * Created by 李先俊 on 2017/6/13.
 */

public class HotelSelectActivity extends AbsStoreBaseActivity {

    private ActivityHotelSelectBinding mBinding;
    private HotelSelectListAdapter hotelAdapter;

    private String mHotelAddress;
    private String mHotelName;

    private List<String> mImgs=new ArrayList<>();
    private TextView tvIntoData;
    private TextView tvOutData;
    private TextView tvDateSum;

    private Date mStartDate;
    private Date mEndDate;

    private int mPageStart=1;

  private String mStoreCode;
    private Banner bannerView;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, String mHotelAddress, String imgs,String name,String code){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HotelSelectActivity.class);

        intent.putExtra("mHotelAddress",mHotelAddress);
        intent.putExtra("mImages",imgs);
        intent.putExtra("hotelName",name);
        intent.putExtra("code",code);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hotel_select, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("酒店选择");

        setSubLeftImgState(true);

        getIntentData();

        initViews();

        getHotelDataRequest(this);
    }

    /**
     *
     */
    private void initViews() {


        mBinding.springview.setType(SpringView.Type.FOLLOW);
        mBinding.springview.setGive(SpringView.Give.TOP);
        mBinding.springview.setHeader(new DefaultHeader(this));
        mBinding.springview.setFooter(new DefaultFooter(this));

        mBinding.springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getHotelDataRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getHotelDataRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }
        });


        LinearLayout mHeadView = initHeadViews();

        mBinding.listHotelSelect.addHeaderView(mHeadView,null,false);

        hotelAdapter = new HotelSelectListAdapter(this,new ArrayList<>());

        mBinding.listHotelSelect.setAdapter(hotelAdapter);

/*        mBinding.listHotelSelect.setOnItemClickListener((parent, view, position, id) -> {
           if(hotelAdapter !=null){
               hotelAdapter.setSelectPosition(position-mBinding.listHotelSelect.getHeaderViewsCount());
           }

       });*/

        mBinding.btnBookHotel.setOnClickListener(v -> {

            if(hotelAdapter.getSelectItem() ==null){
                showToast("暂无可用房间");
                return;
            }

            if(mStartDate ==null){

                showToast("请选择入住时间");

                return;
            }

            if(mEndDate == null){

                showToast("请选择离开时间");

                return;
            }

            if(DateUtil.isNewer2(mStartDate,mEndDate)){
                showToast("离开时间不能小于入住时间");
                return;
            }

           if(DateUtil.isNewer( DateUtil.parse(DateUtil.format(new Date(),DateUtil.DATE_YMD)),mStartDate)){
                showToast("入住时间不能小于当前时间");
                return;
            }

            HotelOrderCreateModel hmodel=new
                    HotelOrderCreateModel(tvIntoData.getText().toString(),tvOutData.getText().toString(),
                    DateUtil.getDatesBetweenTwoDate(mStartDate,mEndDate).size()-1
                    ,mHotelAddress,hotelAdapter.getSelectItem());

            hmodel.setStartData(DateUtil.format(mStartDate,DATE_YMD));
            hmodel.setEndDate(DateUtil.format(mEndDate,DATE_YMD));

            hmodel.setHotelName(mHotelName);

            HotelOrderCreateActivity.open(this,hmodel);
        });

    }

    /**
     * 初始化HeaderView
     * @return
     */
    @NonNull
    private LinearLayout initHeadViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout mHeadView = (LinearLayout) inflater.inflate(R.layout.layout_hotel_select_head, null);//得到头部的布局


        //出发日期点击

        RelativeLayout intoLayout= (RelativeLayout) mHeadView.findViewById(R.id.relayout_into_data);
        tvIntoData = (TextView) mHeadView.findViewById(R.id.tv_into_select_data);
        tvOutData = (TextView) mHeadView.findViewById(R.id.tv_out_select_data);
        tvDateSum = (TextView) mHeadView.findViewById(R.id.tv_datesum);

//        tvIntoData.setText(DateUtil.format(new Date(),DATE_FMT_YMD));
//        tvOutData.setText(DateUtil.format(new Date(),DATE_FMT_YMD));

        intoLayout.setOnClickListener(v -> {
            new SelectUseDayPopup(this,false)
                    .setSureOnClick((selected, showData) -> {
                        mStartDate = DateUtil.parse(selected,DATE_YMD);
                        tvIntoData.setText(showData);
                        if(mEndDate != null && mStartDate!=null){
                            int sum=DateUtil.getDatesBetweenTwoDate(mStartDate,mEndDate).size()-1;
                            tvDateSum.setText("共"+sum+"晚");
                        }

            }).showPopupWindow();
        });

       //离开
        RelativeLayout outLayout= (RelativeLayout) mHeadView.findViewById(R.id.relayout_out_data);

        outLayout.setOnClickListener(v -> {
            new SelectUseDayPopup(this,false)
                    .setSureOnClick((selected, showData) -> {
                        mEndDate = DateUtil.parse(selected,DATE_YMD);
                        tvOutData.setText(showData);
                        if(mEndDate != null && mStartDate!=null){
                            int sum=DateUtil.getDatesBetweenTwoDate(mStartDate,mEndDate).size()-1;
                            tvDateSum.setText("共"+sum+"晚");
                        }

                    }).showPopupWindow();
        });


        bannerView = (Banner) mHeadView.findViewById(R.id.banner);
        //设置图片集合
        bannerView.setImages(mImgs);
        bannerView.setImageLoader(new GlideImageLoader());
        //banner设置方法全部调用完毕时最后调用
        bannerView.start();
        bannerView.startAutoPlay();
        return mHeadView;
    }


    @Override
    protected void onDestroy() {
        if(bannerView!=null){
            bannerView.stopAutoPlay();
        }
        super.onDestroy();
    }

    /**
     * 获取商户详情
     */
    public void getHotelDataRequest(Context context){
        Map map=new HashMap();

//        map.put("category","FL2017061016211611994528");
//        map.put("type","FL2017061219492431865712");
        map.put("start",mPageStart);
        map.put("status","2");
        map.put("limit","10");
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);
        map.put("storeCode",mStoreCode);

        mSubscription.add(RetrofitUtils.getLoaderServer().GetHotelList("808415",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(hotelListModel -> {

                    if(mPageStart==1){
                        if(hotelListModel==null || hotelListModel.getList()==null){
                            return;
                        }
                        hotelAdapter.setData(hotelListModel.getList());
                        return;
                    }
                    if(mPageStart>1){
                        if(hotelListModel==null || hotelListModel.getList()==null || hotelListModel.getList().size()==0){
                            mPageStart--;
                            return;
                        }
                        hotelAdapter.addData(hotelListModel.getList());
                    }


                },Throwable::printStackTrace));

    }


    public void getIntentData() {

        if(getIntent()!=null){

            mHotelAddress=getIntent().getStringExtra("mHotelAddress");
            mHotelName=getIntent().getStringExtra("hotelName");

            mImgs=StringUtils.splitAsList(getIntent().getStringExtra("mImages"),"\\|\\|");

            mStoreCode=getIntent().getStringExtra("code");

        }

    }
}
