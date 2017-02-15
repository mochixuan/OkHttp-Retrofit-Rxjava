package com.wx.rxretrofitrequestx.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wx.rxretrofitlibrary.bean.DownBean;
import com.wx.rxretrofitlibrary.dao.GreenDaoManager;
import com.wx.rxretrofitrequestx.R;
import com.wx.rxretrofitrequestx.adapter.MiAdapter;

import java.io.File;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    private List<DownBean> mDownBeans;
    private RecyclerView mRecyclerView;

    String directoryPath = Environment.getExternalStorageDirectory().getPath()+"/RxHttp";

    private String[] mUrls = new String[]{"http://s1.music.126.net/download/android/CloudMusic_official_3.8.0_166749.apk",
                                            "http://file.ws.126.net/3g/client/netease_newsreader_android.apk",
                                            "http://downmobile.kugou.com/Android/KugouPlayer/8493/KugouPlayer_219_V8.4.9.apk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mDownBeans = GreenDaoManager.getInstance().queryDownAll();
        if (mDownBeans == null || mDownBeans.size() == 0) {
            for (int i=0;i<mUrls.length;i++) {
                DownBean downBean = new DownBean(mUrls[i]);
                File file = new File(directoryPath,"RxTest"+i+".apk");
                downBean.setSavePath(file.getPath());
                mDownBeans.add(downBean);
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        MiAdapter mAdapter = new MiAdapter(mDownBeans,this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void query(View view) {
        List<DownBean> mDownBeen = GreenDaoManager.getInstance().queryDownAll();
        if (mDownBeen==null){
            System.out.println("=========================>>Null");
        } else if (mDownBeen.size() == 0) {
            System.out.println("=========================>>Size: 0");
        } else {
            for (int i=0;i<mDownBeen.size();i++)
                System.out.println("=====================Data"+i+">>: "+mDownBeen.get(i).getReadLength()+"  "+mDownBeen.get(i).getCountLength());
        }
    }


}
