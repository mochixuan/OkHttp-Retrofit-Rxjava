package com.wx.rxretrofitlibrary.manager;

import com.wx.rxretrofitlibrary.apiservice.HttpDownService;
import com.wx.rxretrofitlibrary.bean.DownBean;
import com.wx.rxretrofitlibrary.dao.GreenDaoManager;
import com.wx.rxretrofitlibrary.enums.DownState;
import com.wx.rxretrofitlibrary.exception.RetryWhenNetworkException;
import com.wx.rxretrofitlibrary.interceptor.DownloadInterceptor;
import com.wx.rxretrofitlibrary.subscriber.DownLoadSubscriber;
import com.wx.rxretrofitlibrary.utils.LibraryUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HttpDownManager {

    private volatile static HttpDownManager INSTANCE;
    private HashMap<String,DownLoadSubscriber> mSubscribers;
    private Set<DownBean> mDownBeans;
    private static final int connect_time=10;
    private static final int read_time=10;
    private static final int write_time=10;

    private HttpDownManager() {
        mDownBeans = new HashSet<>();
        mSubscribers = new HashMap<>();
    }

    public static HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }

    public void start(final DownBean downBean) {

        if (downBean==null || mSubscribers.get(downBean.getUrl())!= null) {
            return;
        }

        DownLoadSubscriber subscriber = new DownLoadSubscriber(downBean);
        mSubscribers.put(downBean.getUrl(),subscriber);
        HttpDownService httpService;

        if (mDownBeans.contains(downBean)) {
            httpService = downBean.getService();
        } else {
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(connect_time, TimeUnit.SECONDS)
                    .readTimeout(read_time,TimeUnit.SECONDS)
                    .writeTimeout(write_time,TimeUnit.SECONDS)
                    .addInterceptor(interceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(LibraryUtil.getBasUrl(downBean.getUrl()))
                    .build();

            httpService = retrofit.create(HttpDownService.class);
            downBean.setService(httpService);
            mDownBeans.add(downBean);

            GreenDaoManager.getInstance().insert(downBean);
        }

        httpService.download("bytes=" + downBean.getReadLength() + "-",downBean.getUrl())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RetryWhenNetworkException())
                .map(new Func1<ResponseBody, DownBean>() {
                    @Override
                    public DownBean call(ResponseBody responseBody) {
                        LibraryUtil.writeCache(responseBody,downBean.getSavePath(),downBean);
                        return downBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    //移除下载
    public void remove(DownBean downBean) {
        if (downBean==null)
            return;
        downBean.setDownState(DownState.REMOVE);
        downBean.getListener().onRemove();
        if (mSubscribers.containsKey(downBean.getUrl())) {
            DownLoadSubscriber subscriber = mSubscribers.get(downBean.getUrl());
            subscriber.unsubscribe();
            mSubscribers.remove(downBean.getUrl());
        }
        if (mDownBeans.contains(downBean)) {
            mDownBeans.remove(downBean);
        }
        GreenDaoManager.getInstance().delete(downBean);
        //可自行添加删除文件操作
        LibraryUtil.deleteDirectory(downBean.getSavePath());
    }

    public void pause(DownBean downBean) {
        if (downBean==null)
            return;
        downBean.setDownState(DownState.PAUSE);
        downBean.getListener().onPause();
        if (mSubscribers.containsKey(downBean.getUrl())) {
            DownLoadSubscriber subscriber = mSubscribers.get(downBean.getUrl());
            subscriber.unsubscribe();
            mSubscribers.remove(downBean.getUrl());
        }
        GreenDaoManager.getInstance().updata(downBean);
    }

    public void clearRequest(DownBean downBean) {
        if (downBean != null) {
            mSubscribers.remove(downBean.getUrl());
            mDownBeans.remove(downBean);
        }
    }

    public void removeAll() {
        for (DownBean downBean:mDownBeans) {
            remove(downBean);
        }
        mSubscribers.clear();
        mDownBeans.clear();
    }

    public void pauseAll() {
        for (DownBean downBean:mDownBeans) {
            pause(downBean);
        }
        mSubscribers.clear();
    }

    public Set<DownBean> getAllDown() {
        return mDownBeans;
    }

}
