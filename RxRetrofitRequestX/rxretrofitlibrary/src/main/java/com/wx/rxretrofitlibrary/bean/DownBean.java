package com.wx.rxretrofitlibrary.bean;


import com.wx.rxretrofitlibrary.apiservice.HttpDownService;
import com.wx.rxretrofitlibrary.enums.DownState;
import com.wx.rxretrofitlibrary.listener.HttpProgressListener;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import static android.R.attr.id;

@Entity
public class DownBean {

    @Id
    private String url;

    private String savePath;

    private long countLength;

    private long readLength;

    @Transient
    private HttpDownService service;

    @Transient
    private HttpProgressListener listener;

    private int stateInte;

    public DownBean(String url) {
        this.url = url;
    }

    public DownBean(String url,HttpProgressListener listener) {
        this.url = url;
        this.listener = listener;
    }

    @Generated(hash = 957211172)
    public DownBean(String url, String savePath, long countLength, long readLength,
            int stateInte) {
        this.url = url;
        this.savePath = savePath;
        this.countLength = countLength;
        this.readLength = readLength;
        this.stateInte = stateInte;
    }

    @Generated(hash = 2113458446)
    public DownBean() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public DownState getDownState() {
        switch (getStateInte()){
            case 0:
                return DownState.START;
            case 1:
                return DownState.DOWN;
            case 2:
                return DownState.PAUSE;
            case 3:
                return DownState.REMOVE;
            case 4:
                return DownState.ERROR;
            case 5:
            default:
                return DownState.FINISH;
        }
    }

    public void setDownState(DownState state) {
        setStateInte(state.getState());
    }


    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }

    public HttpDownService getService() {
        return service;
    }

    public void setService(HttpDownService service) {
        this.service = service;
    }

    public HttpProgressListener getListener() {
        return listener;
    }

    public void setListener(HttpProgressListener listener) {
        this.listener = listener;
    }

}
