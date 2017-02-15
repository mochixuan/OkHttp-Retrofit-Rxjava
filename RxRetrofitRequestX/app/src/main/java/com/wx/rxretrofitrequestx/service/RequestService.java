package com.wx.rxretrofitrequestx.service;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

public interface RequestService {

    @FormUrlEncoded
    @POST("/boxoffice/rank")
    public Observable<String> getMusicStr(@Field("area") String area, @Field("dtype") String dtype,@Field("key") String key);

    @GET
    public Observable<String> getStringData(@Url String url);

}
