package com.wx.rxretrofitlibrary.listener;

public interface HttpDownloadListener {

    void update(long read,long count,boolean isfinish);

}
