package com.example.cuibowen.animatior;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.common_dialogs.dialog.childDialog.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc 版本更新工具类
 * Created by yang on 2016/4/29.
 */
public class VersionUtil  {
    public static String TAG = VersionUtil.class.getSimpleName();
    //    private static VersionUtil instance = null;
    private Activity mContext;
    private String localUrl;
    private MyHandler mHandler;
    private ProgressDialog dialog;
    public OnProgressListener listener;

    public void setListener(OnProgressListener listener) {
        this.listener = listener;
    }

    public void setContent(Activity context){
        this.mContext=context;

        downLoadAPK("http://new.yibaotongapp.com//down/android/app_release_3.1.3.2.apk",1);
    }






    private void downLoadAPK(String apkUrl, int forced) {
//        this.forced=forced;
        String[] strs = apkUrl.split("/");
        String fileName = strs[strs.length - 1];
        File cacheDir = FileUtil.creatFileDirectory("apk");
        localUrl = cacheDir.getAbsolutePath() + File.separator + fileName;
        mHandler = new MyHandler();
        new DownLoadAPKUtil(mHandler, apkUrl, cacheDir.getAbsolutePath(),
                fileName).start();
    }

    private class MyHandler extends Handler {
        public MyHandler() {
            showProgressBar();
        }



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (DownLoadAPKUtil.MSG_NET_RES_OK == msg.what) {
                Toast.makeText(mContext,
                        "下载失败",
                        Toast.LENGTH_SHORT).show();
            } else if (DownLoadAPKUtil.MSG_NET_RES_GET == msg.what) {
                Log.i("eee",(Integer) msg.obj+"");
                dialog.setProgress((Integer) msg.obj);
//                (Integer) msg.obj
            } else if (DownLoadAPKUtil.MSG_DOWNLOAD_OK == msg.what) {
              //ok
            }
        }
    }

    private void showProgressBar() {
        dialog=new ProgressDialog(mContext,true);
        dialog.show();
    }

    private void installVersion(String filename) {
        try {
            File file = new File(filename);
            if (file.isFile()) {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW); // 浏览网页的Action(动作)
                String type = "application/vnd.android.package-archive";
                intent.setDataAndType(Uri.fromFile(file), type); // 设置数据类型
                mContext.startActivity(intent);
                // ((Activity) mActivity).finish();
            }
        } catch (Exception e) {
           //LogUtil.e(TAG, e.getMessage());
        }
    }

    public interface OnProgressListener{
        void progressListener(int progress);
    }



}
