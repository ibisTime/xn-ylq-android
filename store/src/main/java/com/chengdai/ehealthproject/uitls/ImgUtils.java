package com.chengdai.ehealthproject.uitls;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.uitls.glidetransforms.GlideCircleTransform;
import com.chengdai.ehealthproject.uitls.glidetransforms.GlideRoundTransform;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;


/**
 * 图片加载工具类
 * Created by Administrator on 2016-09-14.
 */
public class ImgUtils {

    public static void loadImgId(Context context, int imgid, ImageView img) {

        if (context == null || img == null) {
            return;
        }
        try {
            Glide.with(context).load(imgid).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadBankBg(Context context, int imgid, ImageView img) {

        if (context == null || img == null) {
            return;
        }
        try {
            Glide.with(context).load(imgid).error(R.mipmap.back_default).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadBankLogo(Context context, int imgid, ImageView img) {

        if (context == null || img == null || imgid == 0) {
            return;
        }
        try {
            Glide.with(context).load(imgid).error(R.mipmap.back_logo_defalut).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadImgId(Activity context, int imgid, ImageView img) {
        if (context == null || img == null || imgid == 0) {
            return;
        }
        try {
            Glide.with(context).load(imgid).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadImgURL(Context context, String URL, ImageView img) {
        if (context == null || img == null || TextUtils.isEmpty(URL)) {
            return;
        }
        try {
            Glide.with(context).load(URL).error(R.mipmap.default_pic).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadImgURL(Activity activity, String URL, ImageView img) {
        if (activity == null || img == null || TextUtils.isEmpty(URL)) {
            return;
        }
        try {
            Glide.with(activity).load(URL).error(R.mipmap.default_pic).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadImgIdforRound(Context activity, String URL, ImageView img) {
        if (activity == null || img == null) {
            return;
        }
        try {
            Glide.with(activity).load(URL).error(R.mipmap.default_pic).placeholder(R.mipmap.default_pic).transform(new GlideRoundTransform(activity)).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadDeful(Context context, String url, ImageView img) {
        if (context == null || img == null) {
            return;
        }
        try {
            Glide.with(context).load(url).error(R.mipmap.default_pic).placeholder(R.mipmap.default_pic).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadGridDeful(Context context, String url, ImageView img) {
        if (context == null || img == null) {
            return;
        }
        try {
            Glide.with(context).load(url + MyConfig.IMGURLCOMPRESS).error(R.mipmap.default_pic).placeholder(R.mipmap.default_pic).into(img);
        } catch (Exception e) {

        }

    }

    public static void loadImgIdforCircle(Context activity, String URL, ImageView img) {
        if (activity == null || img == null) {
            return;
        }
        try {
            Glide.with(activity).load(URL).error(R.mipmap.default_pic).transform(new GlideCircleTransform(activity)).into(img);
        } catch (Exception x) {

        }

    }

    public static void loadImgLogo(Context activity, String URL, ImageView img) {
        if (activity == null || img == null) {
            return;
        }

        try {
            Glide.with(activity).load(URL).error(R.mipmap.icon).transform(new GlideCircleTransform(activity)).into(img);
        } catch (Exception e) {

        }

//        Glide.with(activity).load(URL).placeholder(R.mipmap.icon).error(R.mipmap.photo_default) .transform(new GlideCircleTransform(activity)).into(img);
    }


}
