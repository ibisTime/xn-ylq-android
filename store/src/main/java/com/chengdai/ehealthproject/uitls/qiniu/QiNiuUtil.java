package com.chengdai.ehealthproject.uitls.qiniu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.cdkj.baselibrary.utils.StringUtils;
import com.chengdai.ehealthproject.model.common.model.qiniu.QiniuGetTokenModel;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfigStore;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by LeiQ on 2016/12/29.
 */

public class QiNiuUtil {

    private static final String ANDROID = "ANDROID";
    private static  final String IOS = "IOS";
    private String token = "";
    private Context context;

    static String size = "";
    static String imageWidth = "";
    static String imageHeight = "";


    public QiNiuUtil(Context context){
        this.context = context;
    }

    /**
     * 图片单张上传
     * @param callBack
     * @param url
     */
    private void uploadSingle(final QiNiuCallBack callBack , String url,String token){

        if(url.indexOf(ANDROID) == -1 || url.indexOf(IOS) == -1){

            Configuration config = new Configuration.Builder().build();
            UploadManager uploadManager = new UploadManager(config);
            String key = ANDROID + timestamp() + getImageWidthHeight(url) + ".jpg";


            uploadManager.put(url, key, token,

                  (key1, info, res) -> {

                      //res包含hash、key等信息，具体字段取决于上传策略的设置
                      if(info !=null && info.isOK())
                      {
                          Observable.just("")
                                  .observeOn(AndroidSchedulers.mainThread())
                                  .subscribe(new Consumer<String>() {
                                      @Override
                                      public void accept(String s) throws Exception {
                                          callBack.onSuccess(key, info, res);
                                      }
                                  });

                      } else{
                          Observable.just("token失败")
                                  .observeOn(AndroidSchedulers.mainThread())
                                  .subscribe(new Consumer<String>() {
                                      @Override
                                      public void accept(String s) throws Exception {
                                          callBack.onFal(s);
                                      }
                                  });
                          Log.i("QiNiu", "Upload Fail");
                          Log.i("QiNiu", "key="+key);
                          Log.i("QiNiu", "res="+res);
                          Log.i("QiNiu", "info="+info);
                      }

                  },null);
        }

    }


    /**
     * 获取七牛token
     * @return
     */
    public Observable<QiniuGetTokenModel> getQiniuToeknRequest(){
        Map<String,String> object=new HashMap<>();
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyConfigStore.SYSTEMCODE);
        return RetrofitUtils.getLoaderServer().GetQiniuTOken("807900", StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(null));
    }

    /**
     * 获取七牛url
     * @param callBack
     */
    public void getQiniuURL(QiNiuCallBack callBack,String data){

        getQiniuToeknRequest()
                .subscribe(r -> {
                    if(r==null ||  TextUtils.isEmpty(r.getUploadToken())){
                        return;
                    }
                    token=r.getUploadToken();
                    Compressor(callBack,data,token);

                },throwable -> {
                    callBack.onFal("图片上传失败,请选择正确的图片");
                });

    }


    //多张图片上传
    public void updataeImage(List<String> dataList,String mToekn,QiNiuCallBack callBack){

        for(int i=0;i<dataList.size();i++) {
            String imgPath = dataList.get(i);
            if(TextUtils.isEmpty(imgPath)){
                continue;
            }

            try {

                Compressor(callBack,imgPath,mToekn);
            }catch (Exception e){
                if(callBack!=null){
                    callBack.onFal("图片上传失败,请选择正确的图片");
                }
            }

        }

    }





    public static String getImageWidthHeight(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        imageWidth = options.outWidth + "";
        imageHeight = options.outHeight + "";
        size = "_" + imageWidth + "_" + imageHeight;

        System.out.print("size = _" + imageWidth + "_" + imageHeight);
        return size;
    }


    private static String timestamp(){
        String time = System.currentTimeMillis()+"";

        return "_"+time;
    }


    private void Compressor(QiNiuCallBack callBack,String data,String token){
        File compressedImageFile = Compressor.getDefault(context).compressToFile(new File(data));
        uploadSingle(callBack,compressedImageFile.getAbsolutePath(),token);

    }

    public interface QiNiuCallBack {
        void onSuccess(String key, ResponseInfo info, JSONObject res);
        void onFal(String info);
    }


/*
    public void getUpimg(final String imagePath) {
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(imagePath, null, Const.uptoken,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                Log.i("qiniu", key + ",\r\n " + info + ",\r\n "
                                        + res);
                                try {
                                    // 七牛返回的文件名
                                    upimg = res.getString("key");
                                    upimg_key_list.add(upimg);//将七牛返回图片的文件名添加到list集合中
                                    //list集合中图片上传完成后，发送handler消息回主线程进行其他操作
                                    if (upimg_key_list.size() == dataList
                                            .size()) {
                                        mHandler.sendEmptyMessage(0x333);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, null);
            }
        }.start();
    }
*/


}
