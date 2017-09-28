package com.cdkj.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.cdkj.baselibrary.R;
import com.cdkj.baselibrary.utils.glidetransforms.GlideCircleTransform;

/**
 * 图片加载工具类
 * Created by Administrator on 2016-09-14.
 */
public class ImgUtils {

    public static void loadActLogo(Activity context, String imgid, ImageView img){
        if (!AppUtils.isActivityExist(context)){
            return;
        }
        if(context==null || img==null)
        {
            return;
        }
        LogUtil.E("图片"+imgid);

        try {
            Glide.with(context).load(imgid).placeholder(R.drawable.photo_default).error(R.drawable.photo_default).transform(new GlideCircleTransform(context)).into(img);
        }catch (Exception e){
            LogUtil.E("图片加载错误");
        }
    }


    public static void  loadActImg(Activity context,String imgid,ImageView img){

        if (!AppUtils.isActivityExist(context)){

            LogUtil.E("图片加载界面销毁");
            return;
        }

        if(context==null || img==null)
        {
            return;
        }

        LogUtil.E("图片"+imgid);

        try {
            Glide.with(context).load(imgid).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(img);
        }catch (Exception e){
            LogUtil.E("图片加载错误");
        }

    }

    public static void  loadActImgListener(Activity context, String imgid, ImageView img, RequestListener<String, GlideDrawable> listener){

        if (!AppUtils.isActivityExist(context)){

            LogUtil.E("图片加载界面销毁");
            return;
        }

        if(context==null || img==null)
        {
            return;
        }

        LogUtil.E("图片"+imgid);

        try {
            Glide.with(context).load(imgid).error(R.drawable.default_pic).listener(listener).into(img);
        }catch (Exception e){
            LogUtil.E("图片加载错误");
        }

    }

    public static void  loadActImgId(Activity context,int imgid,ImageView img){

        if (!AppUtils.isActivityExist(context)){

            LogUtil.E("图片加载界面销毁");
            return;
        }

        if(context==null || img==null)
        {
            return;
        }

        LogUtil.E("图片"+imgid);

        try {
            Glide.with(context).load(imgid).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(img);
        }catch (Exception e){
            LogUtil.E("图片加载错误");
        }

    }


    public static void  loadFraImgId(Fragment context, int imgid, ImageView img){

        if(context==null || img==null)
        {
            return;
        }

        LogUtil.E("图片"+imgid);

        try {
            Glide.with(context).load(imgid).placeholder(R.drawable.default_pic).error(R.drawable.default_pic).into(img);
        }catch (Exception e){
            LogUtil.E("图片加载错误");
        }

    }

    public static void loadBankBg(Context context, int imgid, ImageView img) {

        if (context == null || img == null) {
            return;
        }
        try {
            Glide.with(context).load(imgid).placeholder(R.drawable.back_default).error(R.drawable.back_default).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadBankLogo(Context context, int imgid, ImageView img) {

        if (context == null || img == null || imgid == 0) {
            return;
        }
        try {
            Glide.with(context).load(imgid).placeholder(R.drawable.back_logo_defalut).error(R.drawable.back_logo_defalut).into(img);
        } catch (Exception e) {

        }

    }


}
