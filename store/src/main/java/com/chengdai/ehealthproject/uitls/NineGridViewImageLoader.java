package com.chengdai.ehealthproject.uitls;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengdai.ehealthproject.R;
import com.lzy.ninegrid.NineGridView;

/**
 * Created by LeiQ on 2017/4/17.
 */

public class NineGridViewImageLoader implements NineGridView.ImageLoader {


    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        ImgUtils.loadGridDeful(context,url,imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
