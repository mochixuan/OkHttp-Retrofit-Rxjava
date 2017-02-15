package com.wx.rxretrofitlibrary.api;

import retrofit2.Retrofit;
import rx.Observable;

public abstract class BaseApi {

    private boolean cache;

    private String baseUrl="";

    private String mothedUrl="";

    private int readTime = 10;

    private int writeTime = 10;

    private int connectionTime = 10;

    public abstract Observable getObservable(Retrofit retrofit);

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getAllUrl(){
        return baseUrl+mothedUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMothedUrl() {
        return mothedUrl;
    }

    public void setMothedUrl(String mothedUrl) {
        this.mothedUrl = mothedUrl;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    public int getReadTime() {
        return readTime;
    }

    public void setReadTime(int readTime) {
        this.readTime = readTime;
    }

    public int getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(int writeTime) {
        this.writeTime = writeTime;
    }
}
