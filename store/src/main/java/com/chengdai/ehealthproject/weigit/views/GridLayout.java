package com.chengdai.ehealthproject.weigit.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.chengdai.ehealthproject.R;


/**
 * 模仿 GridView 布局
 */
public class GridLayout extends ViewGroup {

    /**
     * tiem点击事件
     */
    private ItemOnclickListener listener;
    /**
     * 垂直间距
     */
    private int verticalSpacing;
    /**
     * 水平间距
     */
    private int horizontalSpacing;
    /**
     * item宽度
     */
    private int itemWidth;
    /**
     * item高度
     */
    private int itemHeight;

    /**
     * 列数
     */
    private int numColumns;

    /**
     * 子View 点击事件监听
     */
    public interface ItemOnclickListener {
        void onClick(int position);
    }

    public GridLayout(Context context) {
        this(context, null);
    }

    public GridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridLayout(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {

        DisplayMetrics dm = getResources().getDisplayMetrics();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GridLayout);
        horizontalSpacing = ta.getInt(R.styleable.GridLayout_horizontalSpacing, 5);
        verticalSpacing = ta.getInt(R.styleable.GridLayout_verticalSpacing, 5);
        itemWidth = ta.getInt(R.styleable.GridLayout_itemWidth, 00);
        itemHeight = ta.getInt(R.styleable.GridLayout_itemHeight, 00);
        numColumns = ta.getInt(R.styleable.GridLayout_numColumns, 1);

        horizontalSpacing = dip2px(context, horizontalSpacing);
        verticalSpacing = dip2px(context, verticalSpacing);

        if(itemWidth==0){
            itemWidth=(dm.widthPixels - 2 * horizontalSpacing) / numColumns -dip2px(context,15);//暂时硬编码 xml控件边距是15dp
            itemHeight=itemWidth;
        }

        ta.recycle();

    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int cCount = getChildCount();

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        itemWidth = (sizeWidth - 2 * horizontalSpacing) / numColumns;
        itemHeight = itemWidth;


        int width = 0;  //子view累加宽度
        int height = 0; //子View累加高度

        int lineWidth = 0;
        int lineHeight = 0;

        int index = 0;

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();
/*
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin; */

            int childWidth = itemWidth + lp.leftMargin
                    + lp.rightMargin;

            int childHeight = itemWidth + lp.topMargin
                    + lp.bottomMargin;
            //子view并列 width累加 大于 屏幕宽度 时 或列数达到时换行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight() || (index % numColumns == 0 && index >1)) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight + verticalSpacing; //根据子View高度累加 + 垂直间距
                lineHeight = childHeight;
                index = 1;
            } else {
                lineWidth += childWidth + horizontalSpacing;  //根据子View宽度累加 + 水平间距
                lineHeight = Math.max(lineHeight, childHeight);
                index++;
            }

            if (i == cCount - 1)    //测量到最后一个子View 时
            {
                width = Math.max(lineWidth, width) - horizontalSpacing;// - horizontalSpacing 间距个数比列数少1
                height += lineHeight;
            }
        }

        //设置测量值
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());//

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int r, int b) {

        int mViewGroupWidth = getMeasuredWidth();  //当前ViewGroup的总宽度

        int mPainterPosX = 0; //当前绘图光标横坐标位置
        int mPainterPosY = 0;  //当前绘图光标纵坐标位置
        int index = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);

            int width = childView.getMeasuredWidth();
            int height = childView.getMeasuredHeight();

            LayoutParams margins = (LayoutParams) (childView.getLayoutParams());

            //ChildView占用的width  = width+leftMargin+rightMargin
            //ChildView占用的height = height+topMargin+bottomMargin
            //如果剩余的空间不够，则移到下一行开始位置
            if (mPainterPosX + width + margins.leftMargin + margins.rightMargin > mViewGroupWidth - getPaddingLeft() - getPaddingRight() || (index % numColumns == 0 && index >1)) {
                mPainterPosX = 0;
                mPainterPosY += height + margins.topMargin + margins.bottomMargin + verticalSpacing;
                index = 1;
            } else {
                index++;
            }

            //执行ChildView的绘制
            childView.layout(mPainterPosX + margins.leftMargin, mPainterPosY + margins.topMargin, mPainterPosX + margins.leftMargin + width, mPainterPosY + margins.topMargin + height);

            mPainterPosX += width + margins.leftMargin + margins.rightMargin + horizontalSpacing;
        }
    }
    /**
     * 绑定View
     *
     * @param mAdapter
     */
    public void bindView(Adapter mAdapter) {

        if (this.getChildCount() > 0) {
            this.removeAllViews();
        }
        final int count = mAdapter.getCount();

        LayoutParams layoutParams = new LayoutParams(itemWidth, itemHeight);
        for (int i = 0; i < count; i++) {
            View v = mAdapter.getView(i, null, null);
            addView(v, layoutParams);
        }

        setItemOnclickListener(listener);

        requestLayout();
        invalidate();

    }

    /**
     * 自适应宽高
     *
     * @param mAdapter
     */
    public void bindViewNoWidth(Adapter mAdapter) {

        if (this.getChildCount() > 0) {
            this.removeAllViews();
        }
        final int count = mAdapter.getCount();

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < count; i++) {
            View v = mAdapter.getView(i, null, null);
            addView(v, layoutParams);
        }

        setItemOnclickListener(listener);
    }

    /**
     * 设置View的点击事件
     *
     * @param listener
     */
    public void setItemOnclickListener(final ItemOnclickListener listener) {

        if (listener == null) {
            return;
        }

        this.listener = listener;

        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            final int finalI = i;
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(finalI);
                }
            });
        }
    }


    /**
     * 用于储存子ViewMargin信息
     */
    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

    }


    @Override
    public ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new GridLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(
            ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}