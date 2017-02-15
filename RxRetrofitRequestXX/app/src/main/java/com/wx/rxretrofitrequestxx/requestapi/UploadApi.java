package com.wx.rxretrofitrequestxx.requestapi;

import com.wx.rxretrofitlibrary.api.BaseApi;
import com.wx.rxretrofitrequestxx.service.RequestService;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

public class UploadApi extends BaseApi{

    private MultipartBody.Part part;

    public UploadApi() {
        setBaseUrl("https://www.izaodao.com/Api/");
        setMothedUrl("AppYuFaKu/uploadHeadImg");
    }

    public MultipartBody.Part getPart() {
        return part;
    }

    public void setPart(MultipartBody.Part part) {
        this.part = part;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        RequestService httpService = retrofit.create(RequestService.class);
        RequestBody uid= RequestBody.create(MediaType.parse("text/plain"), "4811420");
        RequestBody key = RequestBody.create(MediaType.parse("text/plain"), "2bd467f727cdf2138c1067127e057950");
        return httpService.uploadImage(uid,key,getPart());
    }

}
