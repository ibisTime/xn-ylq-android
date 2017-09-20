package com.chengdai.ehealthproject.base;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.cdkj.baselibrary.dialog.CommonDialog;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.uitls.ToastUtil;

import java.io.File;

/**
 * 选择地图Activity
 * Created by mxy on 2016/11/16.
 */

public class SelectMapActivity extends Activity implements View.OnClickListener {

    private View empty_view;//空白view
    private TextView txtGaode;//高德地图
    private TextView txtBaidu;//百度地图
    private TextView txtGoogle;//google地C图
    private TextView txtCancel;//取消

    public static final String NAVI_LAT = "navi_lat";//地图导航纬度
    public static final String NAVI_LNG = "navi_lng";//地图导航经度

    private String lat;//纬度
    private String lng;//经度

    //跳转选择地图
    public static void lunch(Activity activity, String lat, String lng) {
        activity.startActivity(new Intent(activity, SelectMapActivity.class).putExtra(NAVI_LAT, lat).putExtra(NAVI_LNG, lng));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);
        //初始化
        initView();
        //初始化监听
        initListener();
        //获取经纬度
        lat = getIntent().getStringExtra(NAVI_LAT);
        lng = getIntent().getStringExtra(NAVI_LNG);
    }

    //初始化view
    private void initView() {
        empty_view = findViewById(R.id.empty_view);
        txtGaode = (TextView) findViewById(R.id.tv_gaode_map);
        txtBaidu = (TextView) findViewById(R.id.tv_baidu_map);
        txtGoogle = (TextView) findViewById(R.id.tv_google_map);
        txtCancel = (TextView) findViewById(R.id.tv_cancel);
    }

    //初始化监听
    private void initListener() {
        empty_view.setOnClickListener(this);
        txtGaode.setOnClickListener(this);
        txtBaidu.setOnClickListener(this);
        txtGoogle.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_cancel || i == R.id.empty_view) {
            finish();

        } else if (i == R.id.tv_gaode_map) {
            if (isInstallByread("com.autonavi.minimap")) {
                openGaoDeNavi();
            } else {
                //market为路径，id为包名
                //显示手机上所有的market商店
//                    ToastUtil.show(this, "您尚未安装高德地图");
                new CommonDialog(this).builder()
                        .setTitle("提示").setContentMsg("您没有安装高德地图")
                        .setNegativeBtn("确定", new CommonDialog.OnNegativeListener() {
                            @Override
                            public void onNegative(View view) {
/*                                    Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);*/
                            }
                        }, false).show();
            }

        } else if (i == R.id.tv_baidu_map) {
            if (isInstallByread("com.baidu.BaiduMap")) {
                openBaiduNavi();
            } else {
                //market为路径，id为包名
                //显示手机上所有的market商店
//                    ToastUtil.show(this, "您尚未安装百度地图");
                new CommonDialog(this).builder()
                        .setTitle("提示").setContentMsg("您没有安装百度地图")
                        .setNegativeBtn("确定", new CommonDialog.OnNegativeListener() {
                            @Override
                            public void onNegative(View view) {
                                  /*  Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);*/
                            }
                        }, false).show();
            }

        } else if (i == R.id.tv_google_map) {
            if (isInstallByread("com.google.android.apps.maps")) {
                openGoogleNavi();
            } else {//打开googleweb导航
//                    ToastUtil.show(this, "请安装百度地图客户端");
//                    openWebGoogleNavi();
                GoogleMap();
            }


        }
    }


    //判断是否安装了应用
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 启动高德App进行导航
     * sourceApplication 必填 第三方调用应用名称。如 amap
     * poiname           非必填 POI 名称
     * dev               必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * style             必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    private void openGaoDeNavi() {

        try {
            StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                    .append("EHealthProject").append("&lat=").append(lat)
                    .append("&lon=").append(lng)
                    .append("&dev=").append(1)
                    .append("&style=").append(0);
            ;
//        if (!TextUtils.isEmpty(poiname)) {
//            stringBuffer.append("&poiname=").append(poiname);
//        }
            Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(stringBuffer.toString()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setPackage("com.autonavi.minimap");
            startActivity(intent);
        } catch (Exception e) {
            ToastUtil.show(this, "高德地图启动失败");
        }
    }

    /**
     * 打开百度地图导航客户端
     * intent = Intent.getIntent("baidumap://map/navi?location=34.264642646862,108.95108518068&type=BLK&src=thirdapp.navi.you
     * location	坐标点	location与query二者必须有一个，当有location时，忽略query
     * query	搜索key	同上
     * type	路线规划类型	BLK:躲避拥堵(自驾);TIME:最短时间(自驾);DIS:最短路程(自驾);FEE:少走高速(自驾);默认DIS
     */
    private void openBaiduNavi() {
        try {
            //打开百度路线规划 (注意：坐标先纬度，后经度
            Intent i1 = new Intent();
            i1.setData(Uri.parse("baidumap://map/direction?destination=" + lat + "," + lng + "&mode=driving"));
            startActivity(i1);
//驾车打开导航
//            StringBuffer stringBuffer = new StringBuffer("baidumap://map/navi?location=")
//                    .append(lat).append(",").append(lng).append("&type=TIME");
////        StringBuffer stringBuffer = new StringBuffer("baidumap://map/navi?location=30.183826,120.145454&type=TIME");
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
//            intent.setPackage("com.baidu.BaiduMap");
//            startActivity(intent);
        } catch (Exception e) {
            ToastUtil.show(this, "百度地图启动失败");
        }
    }

    /**
     * 打开google Web地图导航
     */
    private void openWebGoogleNavi() {
        try {
            StringBuffer stringBuffer = new StringBuffer("http://ditu.google.cn/maps?hl=zh&mrt=loc&q=").append(lat).append(",").append(lng);
//        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ditu.google.cn/maps?hl=zh&mrt=loc&q=30.183826,120.145454"));
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
            startActivity(i);
        } catch (Exception e) {
            ToastUtil.show(this, "Google地图启动失败");
        }

    }

    /**
     * 打开google地图客户端开始导航
     * q:目的地
     * mode：d驾车 默认
     */
    private void openGoogleNavi() {
        try {
            StringBuffer stringBuffer = new StringBuffer("google.navigation:q=").append(lat).append(",").append(lng).append("&mode=d");
//        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=30.183826,120.145454"));
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
            i.setPackage("com.google.android.apps.maps");
            startActivity(i);
        } catch (Exception e) {
            ToastUtil.show(this, "Google地图启动失败");
        }
    }

    /*
    google地图操作
     */
    private void GoogleMap() {
        new CommonDialog(this).builder()
                .setTitle("系统提示").setContentMsg("您尚未安装谷歌地图,是否打开Google Web地图?")

                .setPositiveBtn("确定", new CommonDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(View view) {
                        openWebGoogleNavi();
                    }
                })
                .setNegativeBtn("取消", new CommonDialog.OnNegativeListener() {
                    @Override
                    public void onNegative(View view) {
       /*                 Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);*/
                    }
                }, false).show();
    }
}
