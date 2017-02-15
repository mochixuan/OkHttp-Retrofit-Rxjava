package com.wx.rxretrofitlibrary.responsebody;

import com.wx.rxretrofitlibrary.listener.UploadProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class UploadRequestBody extends RequestBody{

    private RequestBody mRequestBody;

    private UploadProgressListener mProgressListener;

    private BufferedSink mBufferedSink;

    public UploadRequestBody(RequestBody requestBody,UploadProgressListener listener) {
        this.mRequestBody = requestBody;
        this.mProgressListener = listener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink==null) {
            mBufferedSink = Okio.buffer(sink(sink));
        }
        mRequestBody.writeTo(mBufferedSink);
        mBufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            long writtenBytesCount = 0L;
            long totalBytesCount = 0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                writtenBytesCount += byteCount;
                if (totalBytesCount == 0) {
                    totalBytesCount = contentLength();
                }
                Observable.just(writtenBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mProgressListener.onProgress(writtenBytesCount, totalBytesCount);
                    }
                });
            }
        };
    }

}
