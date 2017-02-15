package com.wx.rxretrofitlibrary.dao;

import com.wx.rxretrofitlibrary.application.RxApplication;
import com.wx.rxretrofitlibrary.bean.DownBean;

import java.util.List;

public class GreenDaoManager {

    private static volatile  GreenDaoManager INSTANCE;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper mDevOpenHelper;

    public static GreenDaoManager getInstance(){
        if(INSTANCE==null){
            synchronized (GreenDaoManager.class){
                if (INSTANCE==null)
                    INSTANCE=new GreenDaoManager();
            }
        }
        return INSTANCE;
    }

    private DaoMaster.DevOpenHelper getDevOpenHelper(){
        if(mDevOpenHelper==null){
            synchronized (GreenDaoManager.class){
                if(mDevOpenHelper==null){
                    mDevOpenHelper = new DaoMaster.DevOpenHelper(RxApplication.getApplicationContext(),"down_data",null);
                }
            }
        }
        return mDevOpenHelper;
    }

    private DaoMaster getDaoMaster(){
        if(mDaoMaster==null){
            synchronized (GreenDaoManager.class){
                if(mDaoMaster==null){
                    mDaoMaster = new DaoMaster(getDevOpenHelper().getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    private DaoSession getDaoSession(){
        if(mDaoSession==null){
            synchronized (GreenDaoManager.class){
                if(mDaoSession==null){
                    mDaoSession=getDaoMaster().newSession();
                }
            }
        }
        return mDaoSession;
    }

    public void insert(DownBean downBean) {

        if (downBean != null && downBean.getUrl() != null){
            if (!isExistBean(downBean.getUrl())) {
                getDaoSession().insert(downBean);
            }
        }
    }

    private boolean isExistBean(String url) {
        List<DownBean> downBeans = getDaoSession().queryBuilder(DownBean.class)
                .where(DownBeanDao.Properties.Url.eq(url))
                .list();

        if (downBeans == null || downBeans.size()==0) {
            return false;
        }

        return true;
    }

    public DownBean queryDown(String url) {
        if (url != null) {
            DownBean downBeans = getDaoSession().queryBuilder(DownBean.class)
                    .where(DownBeanDao.Properties.Url.eq(url))
                    .unique();
            return downBeans;
        }
        return null;
    }

    public void updata(DownBean downBean) {
        if (downBean!=null)
            getDaoSession().update(downBean);
    }

    public void delete(DownBean downBean) {
        if (downBean!=null)
            getDaoSession().delete(downBean);
    }

    public List<DownBean> queryDownAll() {
        List<DownBean> downBeans = getDaoSession().queryBuilder(DownBean.class)
                .list();
        return downBeans;
    }

    public void close(){
        if(mDaoSession!=null){
            mDaoSession.clear();
            mDaoSession = null;
        }
        if(mDevOpenHelper!=null){
            mDevOpenHelper.close();
            mDevOpenHelper = null;
        }
        if (mDaoMaster != null) {
            mDaoMaster = null;
        }
    }

}
