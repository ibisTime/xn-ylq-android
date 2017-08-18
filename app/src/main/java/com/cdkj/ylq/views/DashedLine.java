package com.cdkj.ylq.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.cdkj.ylq.R;

/**
 * 画虚线
 */
public class DashedLine extends View {


    private int mLineColor;
    private boolean mDrawVLine;

    public DashedLine(Context context) {
        this(context,null);
    }

    public DashedLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashedLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DashedLine);
        mLineColor = ta.getColor(R.styleable.DashedLine_lineColor, ContextCompat.getColor(context,R.color.gray));
        mDrawVLine = ta.getBoolean(R.styleable.DashedLine_drawVertical,false);
        ta.recycle();
    }

    public int getmLineColor() {
        return mLineColor;
    }

    public void setmLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public boolean ismDrawVLine() {
        return mDrawVLine;
    }

    public void setmDrawVLine(boolean mDrawVLine) {
        this.mDrawVLine = mDrawVLine;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mLineColor);
        Path path = new Path();
        path.moveTo(0, 0);
        if(mDrawVLine){
            path.lineTo(0,getHeight());
        }else{
            path.lineTo(getWidth(), 0);
        }
        PathEffect effects = new DashPathEffect(new float[]{4, 4, 4, 4}, 0);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}