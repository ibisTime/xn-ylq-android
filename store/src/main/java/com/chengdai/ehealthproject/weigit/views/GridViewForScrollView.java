package com.chengdai.ehealthproject.weigit.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 *为解决adapter getView 重复多次执行 继承GridView 增加变量用于判断是否绘制完成，
 * Created by Administrator on 2016-03-17.
 */
public class GridViewForScrollView extends GridView {
    private Boolean isMeasure=false;

    public Boolean getIs() {
        return isMeasure;
    }


    public GridViewForScrollView(Context context) {
        super(context);
    }

    public GridViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        isMeasure=true;
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isMeasure=false;
        super.onLayout(changed, l, t, r, b);
    }
}