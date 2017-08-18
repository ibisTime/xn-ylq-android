package com.cdkj.baselibrary.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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

}
