package com.chengdai.ehealthproject.weigit.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.uitls.DensityUtil;

import java.util.ArrayList;


public class NinePhotoView extends ViewGroup {

    public static final int MAX_PHOTO_NUMBER = 9;

    private int[] constImageIds = {R.mipmap.icon, R.mipmap.icon,
            R.mipmap.icon, R.mipmap.icon, R.mipmap.icon,
            R.mipmap.icon, R.mipmap.icon, R.mipmap.icon,
            R.mipmap.icon};

    // horizontal space among children views
    int hSpace = 10;
    // vertical space among children views
    int vSpace = 10;

    // every child view width and height.
    int childWidth = 0;
    int childHeight = 0;

    // store images res id
    ArrayList<Integer> mImageResArrayList = new ArrayList<Integer>(9);


    public NinePhotoView(Context context) {
        super(context);
    }

    public NinePhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NinePhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout viewf = (LinearLayout) inflater.inflate(R.layout.layout_nine_img, null,false);
        addView(viewf);
  /*      BadgeView  addPhotoView = new BadgeView(context);
        addView(addPhotoView);*/
        mImageResArrayList.add(new Integer(1));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int rw = MeasureSpec.getSize(widthMeasureSpec);
        int rh = MeasureSpec.getSize(heightMeasureSpec);

        childWidth = (rw - 2 * hSpace) / 3;
        childHeight = childWidth;

        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child =  this.getChildAt(i);
            //this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            lParams.left = (i % 3) * (childWidth + hSpace);
            lParams.top = (i / 3) * (childWidth + vSpace);
        }

        int vw = rw;
        int vh = rh;
        if (childCount < 3) {
            vw = childCount * (childWidth + hSpace);
        }
        vh = ((childCount + 3) / 3) * (childWidth + vSpace);
        setMeasuredDimension(vw, vh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = (View) this.getChildAt(i);

            child.setBackgroundColor(Color.RED);

            ImageView img= (ImageView) child.findViewById(R.id.img);

            img.setBackgroundColor(Color.BLACK);

            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            child.layout(lParams.left, lParams.top, lParams.left + childWidth,
                    lParams.top + childHeight);

            if (i == mImageResArrayList.size() - 1 && mImageResArrayList.size() != MAX_PHOTO_NUMBER) {
//                img.setBackgroundResource(R.mipmap.address_choose);
//                child.setImg(R.mipmap.address_choose);
                img.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        addPhotoBtnClick();
                    }
                });
            }else {
//                child.setImg(R.mipmap.address_choose);
//                img.setBackgroundResource(constImageIds[i]);
                img.setOnClickListener(null);
            }
        }
    }

    public void addPhoto() {
        if (mImageResArrayList.size() < MAX_PHOTO_NUMBER) {
//            View newChild = new View(getContext());
            BadgeView badgeView=new BadgeView(getContext());
            LayoutInflater inflater = LayoutInflater.from(getContext());
            LinearLayout viewf = (LinearLayout) inflater.inflate(R.layout.layout_nine_img, null,false);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            addView(viewf,layoutParams);
            mImageResArrayList.add(new Integer(1));
            requestLayout();
            invalidate();
        }
    }

    public void addPhotoBtnClick() {
        addPhoto();
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        public int left = 0;
        public int top = 0;

        public LayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

    }

    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new NinePhotoView.LayoutParams(getContext(), attrs);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(
            android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof NinePhotoView.LayoutParams;
    }
}

