package com.cdkj.ylq.adapters;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.model.PorductListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**借贷产品列表
 * Created by 李先俊 on 2017/8/12.
 */

public class BorrowMoneyProductAdapter extends BaseQuickAdapter<PorductListModel.ListBean, BaseViewHolder> {

    public BorrowMoneyProductAdapter( @Nullable List<PorductListModel.ListBean> data) {
        super(R.layout.item_borrowmoney_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PorductListModel.ListBean item) {
        if (item == null) return;
        CardView cardView = helper.getView(R.id.cardview);
        try {
            cardView.setCardBackgroundColor(Color.parseColor(item.getUiColor()));
        } catch (Exception e) {
            cardView.setCardBackgroundColor(Color.parseColor("#0cb8ae"));//颜色解析失败时设置默认颜色
        }

        if (TextUtils.equals("1", item.getIsLocked())) { //锁中状态
            cardView.setAlpha((float) 0.5);
            helper.setVisible(R.id.tv_state,true);
            helper.setVisible(R.id.tv_level_satate,true);
            helper.setVisible(R.id.tv_state,false);

        } else {
            cardView.setAlpha(1);
            helper.setVisible(R.id.img_state, false);
            helper.setVisible(R.id.tv_state, true);
            helper.setVisible(R.id.tv_level_satate,false);
        }

        helper.setText(R.id.tv_money, MoneyUtils.showPrice(item.getAmount()) + "");
        helper.setText(R.id.tv_make_day, item.getDuration() + "天");
        helper.setText(R.id.tv_state, getState(item.getUserProductStatus()));
        helper.setText(R.id.tv_level, "Lv"+item.getLevel());
        helper.setText(R.id.tv_level_satate, item.getSlogan());

        if(canUseCancle(item.getUserProductStatus()) && TextUtils.equals("0", item.getIsLocked())){
            helper.setVisible(R.id.tv_cancle,true);
            helper.setVisible(R.id.tv_level_satate,false);
            helper.setOnClickListener(R.id.tv_cancle,v -> {
                EventBus.getDefault().post(item);
            });
        }else{
            helper.setVisible(R.id.tv_cancle,false);
            helper.setVisible(R.id.tv_level_satate,true);
            helper.setOnClickListener(R.id.tv_cancle,null);
        }
    }



    /**
     * 能否使用取消按钮
     * @param state
     * @return
     */
    private boolean canUseCancle(String state){
        return  TextUtils.equals(state,"1") || TextUtils.equals(state,"2")
                || TextUtils.equals(state,"3") || TextUtils.equals(state,"4");
    }


    //("0", "可申请"),("1", "认证中"),("2", "人工审核中"),( "3", "已驳回"),("4", "已有额度"),("5", "等待放款中"),( "6", "生效中"),("7", "已逾期")
    private String getState(String state) {

        if(TextUtils.isEmpty(state)){
            return "";
        }

        String stateStr = "";
        ;
        switch (state) {
            case "0":
                stateStr = "可申请";
                break;
            case "1":
                stateStr = "认证中";
                break;
            case "2":
                stateStr = "人工审核中";
                break;
            case "3":
                stateStr = "已驳回";
                break;
            case "4":
                stateStr = "已有额度";
                break;
            case "5":
                stateStr = "等待放款中";
                break;
            case "6":
                stateStr = "生效中";
                break;
            case "7":
                stateStr = "已逾期";
                break;
        }

        return stateStr;
    }
}
