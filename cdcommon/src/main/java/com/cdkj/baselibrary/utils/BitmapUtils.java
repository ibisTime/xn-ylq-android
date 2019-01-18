package com.cdkj.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.cdkj.baselibrary.appmanager.MyConfig.CACHDIR;


/**
 * Bitmap操作
 * Created by cdkj on 2017/11/7.
 */

public class BitmapUtils {

    public static final int picWidth = 720;
    public static final int picHeight = 1280;

    public void BitmapUtils() {
    }

    /**
     * 图片质量压缩法
     *
     * @param
     * @return
     */
    public static byte[] compressImage(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        final BitmapFactory.Options boptions = new BitmapFactory.Options();
        boptions.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, boptions);
        // 计算缩放比
        boptions.inSampleSize = calculateInSampleSize(boptions, picWidth, picHeight);
        // 完整解析图片返回bitmap
        boptions.inJustDecodeBounds = false;

        Bitmap image = BitmapFactory.decodeFile(filePath, boptions);

        image = rotaingImageView(getBitmapDegree(filePath), image); //旋转图片

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (image != null) {
            int quality = 100;
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 90;
            while ((baos.toByteArray().length / 1024) > 150) {  //循环判断如果压缩后图片是否大于150kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
                if (options <= 10) {
                    break;
                }
            }
        }
        byte[] byteArray = baos.toByteArray();
        try {
            if (baos != null) {
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogUtil.E("图片压缩");

        return byteArray;
    }


    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        try {
            //旋转图片 动作
            Matrix matrix = new Matrix();
            ;
            if (angle > 0) {
                matrix.postRotate(angle);
            }
            // 创建新的图片
            Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return resizedBitmap;
        } catch (Exception e) {

        }
        return bitmap;
    }

    /**
     * 读取bitmap并旋转
     *
     * @param path
     * @return
     */
    public static Bitmap decodeFileAndRotaing(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return BitmapUtils.rotaingImageView(getBitmapDegree(path), BitmapFactory.decodeFile(path));
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            LogUtil.E("图片旋转角度异常" + e);
        }
        LogUtil.E("图片旋转角度" + degree);
        return degree;
    }

/*

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
*/

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    //来自luban的压缩算法
    private static int calculateInSampleSize(BitmapFactory.Options options) {
        int srcWidth = options.outWidth % 2 == 1 ? options.outWidth + 1 : options.outWidth;
        int srcHeight = options.outHeight % 2 == 1 ? options.outHeight + 1 : options.outHeight;

        int longSide = Math.max(srcWidth, srcHeight);
        int shortSide = Math.min(srcWidth, srcHeight);

        float scale = ((float) shortSide / longSide);
        if (scale <= 1 && scale > 0.5625) {
            if (longSide < 1664) {
                return 1;
            } else if (longSide >= 1664 && longSide < 4990) {
                return 2;
            } else if (longSide > 4990 && longSide < 10240) {
                return 4;
            } else {
                return longSide / 1280 == 0 ? 1 : longSide / 1280;
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            return longSide / 1280 == 0 ? 1 : longSide / 1280;
        } else {
            return (int) Math.ceil(longSide / (1280.0 / scale));
        }
    }

    /**
     * 压缩Bitmap的大小
     *
     * @param imagePath     图片文件路径
     * @param requestWidth  压缩到想要的宽度
     * @param requestHeight 压缩到想要的高度
     * @return Bitmap
     */
    public static Bitmap decodeBitmapFromFile(String imagePath, int requestWidth, int requestHeight) {
        if (TextUtils.isEmpty(imagePath)) {
            return null;
        }
        if (requestWidth <= 0 || requestHeight <= 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            return bitmap;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载图片到内存，仅获得图片宽高
        BitmapFactory.decodeFile(imagePath, options);
        if (options.outHeight == -1 || options.outWidth == -1) {
            try {
                ExifInterface exifInterface = new ExifInterface(imagePath);
                int height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的高度
                int width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);//获取图片的宽度
                options.outWidth = width;
                options.outHeight = height;

            } catch (IOException e) {
            }
        }
        int degree = getBitmapDegree(imagePath);//获取旋转角度
        options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight); //计算获取新的采样率
        LogUtil.E("图片缩放比例" + options.inSampleSize);
        options.inJustDecodeBounds = false;

        if (degree == 0) {
            return BitmapFactory.decodeFile(imagePath, options);
        } else {
            return rotaingImageView(degree, BitmapFactory.decodeFile(imagePath, options));
        }
    }


    /**
     * 保存bitmap 图片
     *
     * @param bitmap
     * @param imageName
     * @return
     */
    public static String saveBitmapFile(Bitmap bitmap, String imageName) {

        File file1 = new File(getDirectory());
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
                .format(new Date());
        String imagename = filename + imageName + ".jpg";
        File file = new File(file1, imagename);
        try {
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
        }

        return file.getPath();
    }


    /**
     * 获得缓存目录
     **/
    public static String getDirectory() {
        String dir = getSDPath() + "/" + CACHDIR;
        LogUtil.E("拍照图片路径" + dir);
        return dir;
    }


    /**
     * 取SD卡路径
     **/
    private static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
        }
        if (sdDir != null) {
            return sdDir.toString();
        } else {
            return "";
        }
    }


    public static String getImageWidthHeight(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        String imageWidth = options.outWidth + "";
        String imageHeight = options.outHeight + "";
        String size = "_" + imageWidth + "_" + imageHeight;

        System.out.print("size = _" + imageWidth + "_" + imageHeight);
        return size;
    }

    //保存图片到内存卡,并且通知图库,加载到图库中
    public static void saveImageToGallery(Context context, Bitmap bmp, String fileName) {
        if (bmp == null || TextUtils.isEmpty(fileName)) {
            return;
        }
        String filePath = saveBitmapFile(bmp, fileName);
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }
    //下面是bitmap转换的操作

    /**
     * byte[] 转 bitmap
     *
     * @param b
     * @return
     */
    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    /**
     * bitmap转Drawabl
     *
     * @param bitmap
     * @return
     */
    public static Drawable bitmap2Drawabl(Bitmap bitmap) {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        return bd;
    }

//    /**
//     *
//     * @param id
//     * @return
//     */
//    public static Drawable drawable2Bitmap(int id) {
//        BitmapDrawable bd = new BitmapDrawable(id, bitmap);
//        return bd;
//    }

    /**
     * drawable 转 bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        // 取 drawable 的颜色格式,Bitmap.createBitmap 第三个参数
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        //将drawable内容画到画布中
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 网络图片转  bitmap
     *
     * @param imgurl
     * @return
     * @throws Exception
     */
    public static void getImage(final Activity activity, final String imgurl, final HttpCallBackListener listener) {
//        URL murl = new URL(url);
//        HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
//        InputStream is = conn.getInputStream();
//        return BitmapFactory.decodeStream(is);


//        try {
//            URL url = new URL(imgurl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            // Log exception
//            return null;
//        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageUrl = null;
                try {
                    imageUrl = new URL(imgurl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
//                    Bitmap bitmap1= createBitmapThumbnail(bitmap,false);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onFinish(bitmap);
                            }
                        }
                    });

                    is.close();
                } catch (final IOException e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onError(e);
                            }
                        }
                    });

                    e.printStackTrace();
                }
            }

        }).start();


    }

    //自定义一个接口
    public interface HttpCallBackListener {
        void onFinish(Bitmap bitmap);

        void onError(Exception e);
    }


}
