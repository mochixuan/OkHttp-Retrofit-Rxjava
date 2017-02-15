package com.wx.rxretrofitrequestxx.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.wx.rxretrofitlibrary.bean.DownBean;
import com.wx.rxretrofitlibrary.enums.DownState;
import com.wx.rxretrofitlibrary.listener.HttpProgressListener;
import com.wx.rxretrofitlibrary.manager.HttpDownManager;
import com.wx.rxretrofitrequestxx.R;

import java.io.File;
import java.util.List;

public class MiAdapter extends RecyclerView.Adapter<MiAdapter.MiViewHolder>{

    private List<DownBean> mDownBeans;
    private Activity activity;

    public MiAdapter(List<DownBean> mDownBeans, Activity context) {
        this.mDownBeans = mDownBeans;
        this.activity = context;
    }

    @Override
    public MiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_down,parent,false);
        MiViewHolder miAdapter = new MiViewHolder(view);
        return miAdapter;
    }

    @Override
    public void onBindViewHolder(MiViewHolder holder, final int position) {

        final DownBean downBean = mDownBeans.get(position);

        if (holder.mProgressBar.getProgress()<=0 && downBean.getCountLength()>0) {
            if (downBean.getCountLength()>10000) {
                holder.mProgressBar.setMax((int) downBean.getCountLength()/100);
                if (downBean.getReadLength()<=0) {
                    holder.mProgressBar.setProgress(0);
                } else {
                    holder.mProgressBar.setProgress((int) downBean.getReadLength()/100);
                }
            } else {
                holder.mProgressBar.setMax((int) downBean.getCountLength());
                holder.mProgressBar.setProgress((int) downBean.getReadLength());
            }
        }

        downBean.setListener(produceListener(downBean,holder.mProgressBar,holder.mTvMsg));

        holder.mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick(DownState.START,downBean);
            }
        });
        holder.mBtnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick(DownState.PAUSE,downBean);
            }
        });
        holder.mBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick(DownState.REMOVE,downBean);
            }
        });
        holder.mBtnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick(DownState.FINISH,downBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDownBeans.size();
    }

    private void onclick(DownState downState,DownBean downBean) {
        if (downState==DownState.START) {
            HttpDownManager.getInstance().start(downBean);
        } else if(downState==DownState.PAUSE){
            HttpDownManager.getInstance().pause(downBean);
        } else if (downState==DownState.REMOVE) {
            downBean.setReadLength(0);
            HttpDownManager.getInstance().remove(downBean);
        } else if (downState==DownState.FINISH) {
            if (downBean.getReadLength()==downBean.getCountLength() && downBean.getCountLength() !=0) {
                installApp(downBean.getSavePath());
            }
        }
    }

    public HttpProgressListener produceListener(final DownBean downBean, final NumberProgressBar progressBar, final TextView textView) {

        return new HttpProgressListener() {
            @Override
            public void onStart() {
                textView.setText("开始下载");
            }

            @Override
            public void onComplete() {
                textView.setText("下载完成");
                System.out.println("===============>>"+downBean.getSavePath());
            }

            @Override
            public void onProgress(long readLength, long countLength) {

                //进度条有问题改进

                if (countLength>10000) {
                    progressBar.setMax((int) countLength/100);
                    if (downBean.getReadLength()<=0) {
                        progressBar.setProgress(0);
                    } else {
                        progressBar.setProgress((int) readLength/100);
                    }
                } else {
                    progressBar.setMax((int) countLength);
                    progressBar.setProgress((int) readLength);
                }

            }

            @Override
            public void onError(Throwable throwable) {
                textView.setText("下载错误"+throwable.getMessage());
            }

            @Override
            public void onPause() {
                textView.setText("暂停下载");
            }

            @Override
            public void onRemove() {
                super.onRemove();
                progressBar.setProgress((int) 0);
                textView.setText("移除下载");
            }

        };
    }

    //测试
    private void installApp(String path){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    public class MiViewHolder extends RecyclerView.ViewHolder{

        private NumberProgressBar mProgressBar;
        private Button mBtnStart;
        private Button mBtnPause;
        private Button mBtnRemove;
        private Button mBtnInstall;
        private TextView mTvMsg;

        public MiViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (NumberProgressBar) itemView.findViewById(R.id.progress_bar);
            mBtnStart = (Button) itemView.findViewById(R.id.btn_start);
            mBtnPause = (Button) itemView.findViewById(R.id.btn_pause);
            mBtnRemove = (Button) itemView.findViewById(R.id.btn_remove);
            mBtnInstall = (Button) itemView.findViewById(R.id.btn_install);
            mTvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
        }

    }

}
