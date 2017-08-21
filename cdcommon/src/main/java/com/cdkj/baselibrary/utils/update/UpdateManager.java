package com.cdkj.baselibrary.utils.update;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.cdkj.baselibrary.base.BaseActivity;
import com.cdkj.baselibrary.dialog.CommonDialog;

/**
 * apk下载更新管理
 * Created by 李先俊 on 2017/8/19.
 */

public class UpdateManager {

    public static void checkNewApp(Context context) {
        showUpdateDialog(context);
    }


    /**
     * 展示更新信息
     */
    private static void showUpdateDialog(final Context context) {

        final String path = ApkLoaderUtil.getFilePath(context);

        new CommonDialog(context).builder().setTitle("发现新版本！").setContentMsg("更新文本xxxxxxxx")
                .setPositiveBtn("立刻更新", new CommonDialog.OnPositiveListener() {
                    @Override
                    public void onPositive(View view) {
                        final DownloadDialog  mDownloadDialog = new DownloadDialog(context, "易途8司导端正在更新");
                        mDownloadDialog.setCancelable(false);
                        mDownloadDialog.show();
                        new ApkLoaderUtil().loadApk(path, "http://imtt.dd.qq.com/16891/3AFA21F3690FB27C82A6AB6024E56852.apk", new APKDownloadListener() {
                            @Override
                            public void loadProgress(float progress) {
                                mDownloadDialog.setProgress(progress);
                            }

                            @Override
                            public void loadCompeted(float progress) {
                                ApkLoaderUtil.installApk(context);
                                mDownloadDialog.setProgress(progress);
                                mDownloadDialog.dismiss();
                            }

                            @Override
                            public void loadLoadError(float progress) {
                                mDownloadDialog.setProgress(progress);
                            }
                        });
                    }
                })
                .setNegativeBtn("取消",null).setCancelable(false).show();


    }

}
