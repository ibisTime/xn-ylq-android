package com.chengdai.ehealthproject.weigit;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by 李先俊 on 2017/6/10.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load(MyConfig.IMGURL+path).error(R.mipmap.default_pic).into(imageView);
    }
}