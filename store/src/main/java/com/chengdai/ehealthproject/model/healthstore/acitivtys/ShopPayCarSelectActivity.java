package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsStoreBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityPayCarSelectBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopPayCarListAdApter;
import com.chengdai.ehealthproject.model.healthstore.models.PayCarListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.chengdai.ehealthproject.weigit.appmanager.MyConfig.IMGURL;

/**购物车查看
 * Created by 李先俊 on 2017/6/15.
 */

public class ShopPayCarSelectActivity extends AbsStoreBaseActivity {


    private ActivityPayCarSelectBinding mBinding;


    private int mPageStart=1;//分页索引
    private ShopPayCarListAdApter mAdapter;




    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopPayCarSelectActivity.class);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pay_car_select, null, false);
        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_pay_car));
        setSubLeftImgState(true);

        initViews();

    }


    private void initViews() {

        mBinding.springview.setType(SpringView.Type.FOLLOW);
        mBinding.springview.setGive(SpringView.Give.TOP);
        mBinding.springview.setHeader(new DefaultHeader(this));
        mBinding.springview.setFooter(new DefaultFooter(this));


        mBinding.springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getCarListRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getCarListRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }
        });

        mAdapter = new ShopPayCarListAdApter(this,new ArrayList<>());
        mBinding.listAddress.setAdapter(mAdapter);

        mBinding.listAddress.setOnItemClickListener((parent, view, position, id) -> {
            mAdapter.setmSelectPosition(position);
            mBinding.txtDiscountMoney.setText(mAdapter.getSelectPriceShowString());

        });

        mBinding.txtPay.setOnClickListener(v ->{

            if(mAdapter.getCount() ==0){
                showToast("请选择结算订单");
                return;
            }

            PayCarListModel.ListBean selectData=mAdapter.getmSelectPositionItem();
            if(selectData == null || selectData.getProductSpecs()==null || selectData.getProduct()==null){
                showToast("请选择结算订单");
                return;
            }
            ShopListModel.ListBean.ProductSpecsListBean bean=new ShopListModel.ListBean.ProductSpecsListBean();
            bean.setCode(selectData.getCode());
            bean.setPrice1(selectData.getProductSpecs().getPrice1());
            bean.setName(selectData.getProduct().getName());

            ShopCarPayActivity.open(this,bean,selectData.getQuantity(),IMGURL+selectData.getProduct().getSplitAdvPic());

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCarListRequest(this);
    }

    private  void  getCarListRequest(Context context){

       Map<String,String> map=new HashMap<>();

       map.put("userId", SPUtilHelpr.getUserId());
       map.put("start", mPageStart+"");
       map.put("limit", "10");

      mSubscription.add ( RetrofitUtils.getLoaderServer().GetPayCarList("808045",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(data -> {

                    if(mPageStart==1){
                        if(data != null && data.getList() != null){
                            mAdapter.setData(data.getList());
                        }
                        if(mAdapter.getCount()<=0){
                            showErrorView("您还没有添加商品");
                        }

                    }else {
                        if(data != null && data.getList() != null && data.getList().size()>0){
                            mAdapter.addData(data.getList());
                        }else if(mPageStart > 1){
                            mPageStart--;
                        }
                    }

                    mBinding.txtDiscountMoney.setText(mAdapter.getSelectPriceShowString());
                },Throwable::printStackTrace));

    }

    //ShopPayCarListAdapter  刷新数据
    @Subscribe
    public  void CarRefrshRequestEvent(EventBusModel model){
        if(model != null && "ShopPayCarSelectActivityFresh" .equals(model.getTag()) && mAdapter!=null){
            mBinding.txtDiscountMoney.setText(mAdapter.getSelectPriceShowString());
        }
    }

}
