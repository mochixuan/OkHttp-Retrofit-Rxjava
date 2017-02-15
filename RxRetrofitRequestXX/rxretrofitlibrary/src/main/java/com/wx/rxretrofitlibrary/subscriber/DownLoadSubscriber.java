package com.wx.rxretrofitlibrary.subscriber;

import com.wx.rxretrofitlibrary.bean.DownBean;
import com.wx.rxretrofitlibrary.dao.GreenDaoManager;
import com.wx.rxretrofitlibrary.enums.DownState;
import com.wx.rxretrofitlibrary.listener.HttpDownloadListener;
import com.wx.rxretrofitlibrary.listener.HttpProgressListener;
import com.wx.rxretrofitlibrary.manager.HttpDownManager;

import java.lang.ref.SoftReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class DownLoadSubscriber<T> extends Subscriber<T> implements HttpDownloadListener{

    private SoftReference<HttpProgressListener> mProgressListener;
    private DownBean downBean;

    public DownLoadSubscriber(DownBean downBean) {
        this.downBean = downBean;
        this.mProgressListener = new SoftReference<>(downBean.getListener());
    }

    @Override
    public void onStart() {
        if (mProgressListener.get()!=null) {
            mProgressListener.get().onStart();
        }
        downBean.setDownState(DownState.START);
    }

    @Override
    public void update(long read, long count, boolean isfinish) {
        if (downBean.getCountLength()>count) {
            read=downBean.getCountLength()-count+read;
        } else {
            downBean.setCountLength(count);
        }
        downBean.setReadLength(read);
        if (mProgressListener.get() != null) {
            Observable.just(read).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            if (downBean.getDownState()==DownState.PAUSE ||
                                    downBean.getDownState()==DownState.REMOVE)
                                return;
                            downBean.setDownState(DownState.DOWN);
                            mProgressListener.get().onProgress(aLong,downBean.getCountLength());
                        }
                    });
        }
    }

    @Override
    public void onCompleted() {
        if (mProgressListener.get()!=null) {
            mProgressListener.get().onComplete();
        }
        HttpDownManager.getInstance().clearRequest(downBean);
        downBean.setDownState(DownState.FINISH);
        GreenDaoManager.getInstance().updata(downBean);
    }

    @Override
    public void onError(Throwable e) {
        if (mProgressListener.get() != null) {
            mProgressListener.get().onError(e);
        }
        HttpDownManager.getInstance().clearRequest(downBean);
        downBean.setDownState(DownState.ERROR);
        GreenDaoManager.getInstance().updata(downBean);
    }

    @Override
    public void onNext(T t) {

    }

}
