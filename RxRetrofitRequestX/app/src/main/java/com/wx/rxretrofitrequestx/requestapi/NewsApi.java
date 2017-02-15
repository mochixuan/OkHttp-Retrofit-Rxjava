package com.wx.rxretrofitrequestx.requestapi;

import com.wx.rxretrofitlibrary.api.BaseApi;
import com.wx.rxretrofitrequestx.constant.DataConstant;
import com.wx.rxretrofitrequestx.service.RequestService;

import retrofit2.Retrofit;
import rx.Observable;

public class NewsApi extends BaseApi{

    private String mBaseUrl;

    public NewsApi(String baseUrl) {
        setBaseUrl(DataConstant.BASE_URL);
        this.mBaseUrl = baseUrl;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        RequestService requestService = retrofit.create(RequestService.class);
        return requestService.getStringData("http://v.juhe.cn/toutiao/index?type=keji&key=418462c99158a07722671d0e54be7a1c");
    }

}
