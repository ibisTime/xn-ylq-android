package com.chengdai.ehealthproject.uitls;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xbb on 2017/5/10.
 */

/*
* 简单的富文本工具类
*
* 使用说明 new UtilMoreText().setClickableSpan(tv2,msg);
* */
public class TextReadUtils {

    private OnSpanTextClickListener mSpanTextClickListener;

    private TextView mTextView;//显示富文本的控件
    private String mOriMsg;//全部文本信息

    private int mStartPos;
    private int mEndPos;

    private int mStartPos2;
    private int mEndPos2;

    private Integer mSpanTextColor;



    public TextReadUtils() {
    }

    public void setShowData(TextView textView, String oriMsg) {
        mTextView = textView;
        mOriMsg = oriMsg;
        textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置否则无效
        textView.setText(getSpannableString(mOriMsg));

    }

    public TextReadUtils setSpanTextColor(int spanTextColor){
        mSpanTextColor = spanTextColor;
        return this;
    }

    public TextReadUtils setOnSpanTextClickListener(OnSpanTextClickListener spanTextClickListener){
        mSpanTextClickListener = spanTextClickListener;
        return this;

    }
    public TextReadUtils setPos(int startPos, int endPos){
        mStartPos = startPos;
        mEndPos = endPos;
        return this;
    }
   public TextReadUtils setPos2(int startPos, int endPos){
        mStartPos2 = startPos;
        mEndPos2 = endPos;
        return this;
    }


    class SpanTextClickable extends ClickableSpan implements View.OnClickListener{

        private int type;
        public SpanTextClickable(int type) {
            super();
            this.type = type;
        }
        @Override
        public void onClick(View widget) {
            if(mSpanTextClickListener!=null){
                mSpanTextClickListener.setSpanText(type);
            }

        }
        @Override
        public void updateDrawState(TextPaint ds) {
           ds.setColor(mSpanTextColor);
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }


    private SpannableString open() {
        String temp = mOriMsg+"收起";
        return getSpannableString(temp);/*11 14 16 19*/
    }

    private SpannableString getSpannableString(CharSequence temp) {
        SpannableString spanableInfo = new SpannableString(temp);
        spanableInfo.setSpan(new  SpanTextClickable(1), mStartPos,mEndPos,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
/*
        spanableInfo.setSpan(new  SpanTextClickable(2), mStartPos2,mEndPos2,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);*/
        return spanableInfo;
    }


    public interface OnSpanTextClickListener {
        void setSpanText(int type);
    }
}