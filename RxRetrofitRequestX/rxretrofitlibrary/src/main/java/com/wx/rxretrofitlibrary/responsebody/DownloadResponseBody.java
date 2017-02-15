package com.wx.rxretrofitlibrary.responsebody;

import com.wx.rxretrofitlibrary.listener.HttpDownloadListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class DownloadResponseBody extends ResponseBody{

    private ResponseBody responseBody;
    private HttpDownloadListener listener;
    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody responseBody, HttpDownloadListener listener) {
        this.responseBody = responseBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null){
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    public Source source(Source source) {
        return new ForwardingSource(source) {
            long totalRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalRead += bytesRead!=-1?bytesRead:0;
                if (listener != null) {
                    listener.update(totalRead,responseBody.contentLength(),bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }

}
