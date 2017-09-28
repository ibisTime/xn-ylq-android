package com.cdkj.ylq.adapters;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;

import com.cdkj.baselibrary.utils.MoneyUtils;
import com.cdkj.ylq.R;
import com.cdkj.ylq.appmanager.BusinessSings;
import com.cdkj.ylq.model.PorductListModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 借贷产品列表
 * Created by 李先俊 on 2017/8/12.
 */

public class BorrowMoneyProductAdapter extends BaseQuickAdapter<PorductListModel.ListBean, BaseViewHolder> {

    public BorrowMoneyProductAdapter(@Nullable List<PorductListModel.ListBean> data) {
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
            helper.setVisible(R.id.img_state, true);
            helper.setVisible(R.id.tv_state, false);
            helper.setText(R.id.tv_level_satate, item.getSlogan());
        } else {
            cardView.setAlpha(1);
            helper.setVisible(R.id.img_state, false);
            helper.setVisible(R.id.tv_state, true);
            helper.setText(R.id.tv_level_satate, "极速放款");
        }

        helper.setText(R.id.tv_money, MoneyUtils.showPrice(item.getAmount()) + "");
        helper.setText(R.id.tv_make_day, item.getDuration() + "天");
        helper.setText(R.id.tv_state, BusinessSings.getProductState(item.getUserProductStatus()));
        helper.setText(R.id.tv_level, "Lv" + item.getLevel());


        if (canUseCancle(item.getUserProductStatus()) && TextUtils.equals("0", item.getIsLocked())) {  //没有锁中
            helper.setVisible(R.id.tv_cancle, true);
            helper.setVisible(R.id.tv_level_satate, false);
            helper.setOnClickListener(R.id.tv_cancle, v -> {
                EventBus.getDefault().post(item);
            });
        } else {
            helper.setVisible(R.id.tv_cancle, false);
            helper.setVisible(R.id.tv_level_satate, true);
            helper.setOnClickListener(R.id.tv_cancle, null);
        }
    }


    /**
     * 能否使用取消按钮
     *
     * @param state
     * @return
     */
    private boolean canUseCancle(String state) {
        return TextUtils.equals(state, "1") || TextUtils.equals(state, "2")
                || TextUtils.equals(state, "3");
    }

}
