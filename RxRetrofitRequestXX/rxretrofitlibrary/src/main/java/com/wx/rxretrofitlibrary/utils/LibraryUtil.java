package com.wx.rxretrofitlibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wx.rxretrofitlibrary.bean.DownBean;
import com.wx.rxretrofitlibrary.exception.HttpRunException;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import okhttp3.ResponseBody;

public class LibraryUtil {

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public static String getBasUrl(String url) {
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        return head + url;
    }

    public  static  void writeCache(ResponseBody responseBody, String savePath, DownBean downBean){
        try {
            File file = new File(savePath);
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            long allLength;
            if (downBean.getCountLength()==0){
                allLength=responseBody.contentLength();
            }else{
                allLength=downBean.getCountLength();
            }
            FileChannel channelOut = null;
            RandomAccessFile randomAccessFile = null;
            randomAccessFile = new RandomAccessFile(file, "rwd");
            channelOut = randomAccessFile.getChannel();
            MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, downBean.getReadLength(),allLength-downBean.getReadLength());
            byte[] buffer = new byte[1024*8];
            int len;
            while ((len = responseBody.byteStream().read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
            }
            responseBody.byteStream().close();
            if (channelOut != null) {
                channelOut.close();
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        } catch (IOException e) {
            throw new HttpRunException(e.getMessage());
        }
    }

    public static void deleteDirectory(String path){
        if(path==null)  return;
        final File file = new File(path);
        if (!file.exists()) return ;
        deletFile(file);
    }

    private static void deletFile(File file){
        if(file.isFile()){
            file.delete();
        }else if(file.isDirectory()){
            for (File fl : file.listFiles()) {
                if (fl.isFile()){
                    fl.delete();                              // 删除所有文件
                } else if (fl.isDirectory()){
                    deletFile(fl);                            // 递规的方式删除文件夹
                }
            }
            file.delete();                                    // 删除目录本身
        }
    }

}
