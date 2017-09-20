package com.chengdai.ehealthproject.weigit.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.uitls.DensityUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;


/**用于我的管理页面显示角标
 * Created by Administrator on 2016-07-04.
 */
public class BadgeView extends FrameLayout {

    private ImageView img_bg;
    private Context context;

    public BadgeView(Context context) {
        this(context,null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }


    private void initView(Context context,AttributeSet attrs){
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.badge_layout, this, true);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BadgeView);
        String titleString = ta.getString(R.styleable.BadgeView_badge_title);
        ta.recycle();

        img_bg= (ImageView) findViewById(R.id.img_bg);

    }


    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int count = getChildCount();
        // 临时ViewGroup大小值
        int viewGroupWidth = 0;
        int viewGroupHeight = 0;
        if (count > 0) {
            // 遍历childView
            for (int i = 0; i < count; i++) {
                // childView
                View child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                //测量childView包含外边距
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                // 计算父容器的期望值
                viewGroupWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                viewGroupHeight += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            }

            // ViewGroup内边距
            viewGroupWidth += getPaddingLeft() + getPaddingRight();
            viewGroupHeight += getPaddingTop() + getPaddingBottom();

            //和建议最小值进行比较
            viewGroupWidth = Math.max(viewGroupWidth, getSuggestedMinimumWidth());
            viewGroupHeight = Math.max(viewGroupHeight, getSuggestedMinimumHeight());
        }
        setMeasuredDimension(resolveSize(viewGroupWidth, widthMeasureSpec), resolveSize(viewGroupHeight, heightMeasureSpec));
    }


    public void setImg(int id)
    {
        Glide.with(context).load(id).into(img_bg);
    }

}
