package com.example.cuibowen.animatior;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.text.TextUtils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;

/*
 * Author: Lucifer
 * 
 * Created Date:2015-4-7
 * Copyright @ 2015 BU
 * Description: 文件工具类
 *
 * History:
 */
public class FileUtil {

    public static String appFile = "app";// 默认app文件目录
    public static final String baseFile = Environment
            .getExternalStorageDirectory() + File.separator;
    public static final String appBaseFile = baseFile + appFile
            + File.separator;
    boolean sdCardExist = Environment.getExternalStorageState().equals(
            Environment.MEDIA_MOUNTED);// 是否有存储设备

    public FileUtil() throws Exception {
        if (!sdCardExist)
            throw new Exception("请插入外部SD存储卡");
        File fileBase = new File(appBaseFile);
        if (!fileBase.exists())
            fileBase.mkdir();
    }

    /**
     * 创建一个文件目录
     *
     * @return void
     * @author Lucifer 2015-4-8 下午8:02:31
     */
    public static File creatFileDirectory(String path) {
        File file = new File(appBaseFile + File.separator + path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 创建一个文件
     *
     * @param path
     * @param fileName
     * @return File
     * @author Lucifer 2015-4-8 下午8:28:54
     */
    public static File creatNewFile(String path, String fileName) {
        File file = null;
        creatFileDirectory(path);
        file = new File(appBaseFile + File.separator + path + File.separator
                + fileName);
        return file;
    }

    /**
     * 将图片写入当前文件中
     *
     * @param photo
     * @param path
     * @param fileName
     * @return File
     * @author Lucifer 2015-4-8 下午8:38:17
     */
    public static File saveBitmapToFile(Bitmap photo, String path, String fileName) {
        File file = null;
        file = creatNewFile(path, fileName);
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        photo.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 写到sd卡
     * @param path
     * @param fileName
     * @param inputStream
     * @return File
     * @author luxf 2015-5-26 下午2:37:43
     */
    public static File write2SDFormInput(String path, String fileName,
                                  InputStream inputStream) {
        // 创建文件
        File file = creatNewFile(path, fileName);
        OutputStream outputStream = null;
        try {
            // 创建输出流
            outputStream = new FileOutputStream(file);
            // 创建缓冲区
            byte buffer[] = new byte[4 * 1024];
            // 写入数据
            while ((inputStream.read(buffer)) != -1) {
                outputStream.write(buffer);
            }
            // 清空缓存
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return long
     * @throws Exception
     * @author Administrator 2015-6-14 下午8:22:02
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 转换文件 大小
     *
     * @param fileS
     * @return String
     * @author Administrator 2015-6-14 下午8:22:14
     */
    public static String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0) {
            fileSizeString = "0.0B";
        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @param fileName
     * @return boolean
     * @author Administrator 2015-6-16 下午10:32:40
     */
    public static boolean isFileExistes(String path, String fileName) {
        try {
            File f = new File(path + fileName);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 读取Assets目录下的citylist.json文件
     * @param context
     * @return
     */
    public static String readCityListJson(Context context) {
        String json="";
        try {
            json=readAssetsFile(context,"");
        } catch (IOException e) {

            e.printStackTrace();
        }
        return json;
    }

    /**
     * 读取Assets目录下的fileName文件
     * @param context
     * @return
     */
    public static String readCityListJson(Context context,String fileName) {
        String json="";
        try {
            json=readAssetsFile(context,fileName);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return json;
    }

    /**
     * 读取Assets目录下的文件
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String readAssetsFile(Context context,String fileName) throws IOException {
        String fileContent = "";
        if(TextUtils.isEmpty(fileName)){
            fileName = "citylist.json";
        }
        long start = SystemClock.currentThreadTimeMillis();
        InputStream inputStream = context.getAssets().open(fileName);
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        int len;
        int offset = 0;
        int bufLen = 8192;
        char[] buf = new char[bufLen];
//        while ((line = bufReader.readLine()) != null) {
//            fileContent += line;
//        }
        /*
        int len;
        while ((len=bufReader.read(buf, offset, bufLen)) != -1) {
            stringBuffer.append(buf, 0, len);
            offset += len;
            LogUtil.e("time====","len======"+len);
        }*/
        while ((len = bufReader.read(buf, offset, bufLen)) != -1) {
            stringBuffer.append(buf,0,len);
        }
        inputStream.close();
        fileContent = stringBuffer.toString();
        long end= SystemClock.currentThreadTimeMillis();


        return fileContent;
    }
}
