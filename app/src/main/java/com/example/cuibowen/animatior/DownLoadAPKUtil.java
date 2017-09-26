package com.example.cuibowen.animatior;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @desc 下载apk
 * Created by yang on 2016/4/29.
 */
public class DownLoadAPKUtil extends  Thread{
    private static final String TAG = DownLoadAPKUtil.class.getSimpleName();
    public static final int BUF_LEN = 1024;
    public static final int MSG_NET_RES_OK = 1000;
    public static final int MSG_NET_RES_ERR = 1001;
    public static final int MSG_NET_RES_GET = 1002;
    public static final int MSG_DOWNLOAD_OK = 1005;

    private Handler mHandler = null;
    private String mUrl = null;
    private String mFileName = "";
    private String mPath;


    public DownLoadAPKUtil(){

    }

    public DownLoadAPKUtil(Handler handler, String url, String localPath, String fileName) {
        this.mHandler=handler;
        this.mUrl=url;
        this.mFileName=fileName;
        this.mPath=localPath;

    }


    @Override
    public void run() {

        InputStream inputStream;
        try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.setConnectTimeout(5 * 1000);	//设置连接超时时间为5秒钟
            connection.setRequestMethod("GET");	//设置请求的方法为GET
            connection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");	//设置客户端可以接受的返回数据类型
            connection.setRequestProperty("Accept-Language", "zh-CN");	//设置客户端使用的语言问中文
            connection.setRequestProperty("Referer", mUrl); 	//设置请求的来源，便于对访问来源进行统计
            connection.setRequestProperty("Charset", "UTF-8");	//设置通信编码为UTF-8
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");	//客户端用户代理
            connection.setRequestProperty("Connection", "Keep-Alive");	//使用长连接


            if(connection.getResponseCode()==200){
                int fileSize = connection.getContentLength()/1024;
                Message msg;
                inputStream = connection.getInputStream();
                if (inputStream == null) {
                    throw new RuntimeException("stream is null");
                }

                makeFilePath(mPath,mFileName);

                File dFile = new File(mPath, mFileName);

                FileOutputStream fos = new FileOutputStream(dFile);
                byte buf[] = new byte[1024];
                int total = 0;
                inputStream = connection.getInputStream();
                int len = 0;
                while ((len = inputStream.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    total += (len / 1024);
                    Log.i("eee",total * 100 / fileSize+"");
                    msg = mHandler.obtainMessage(MSG_NET_RES_GET, total * 100 / fileSize);
                    mHandler.sendMessage(msg);
                }
                fos.flush();
                fos.close();
                inputStream.close();
                msg = mHandler.obtainMessage(MSG_DOWNLOAD_OK, 100);
                mHandler.sendMessage(msg);
                msg = mHandler.obtainMessage(MSG_NET_RES_OK, "complete");
                mHandler.sendMessage(msg);
            }

        } catch (Exception e) {
            Message msg = mHandler.obtainMessage(MSG_NET_RES_ERR,
                    e.toString());
            mHandler.sendMessage(msg);
        }




    }



    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }



}
