package com.wx.rxretrofitlibrary.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.wx.rxretrofitlibrary.bean.DownBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DOWN_BEAN".
*/
public class DownBeanDao extends AbstractDao<DownBean, String> {

    public static final String TABLENAME = "DOWN_BEAN";

    /**
     * Properties of entity DownBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Url = new Property(0, String.class, "url", true, "URL");
        public final static Property SavePath = new Property(1, String.class, "savePath", false, "SAVE_PATH");
        public final static Property CountLength = new Property(2, long.class, "countLength", false, "COUNT_LENGTH");
        public final static Property ReadLength = new Property(3, long.class, "readLength", false, "READ_LENGTH");
        public final static Property StateInte = new Property(4, int.class, "stateInte", false, "STATE_INTE");
    }


    public DownBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DownBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DOWN_BEAN\" (" + //
                "\"URL\" TEXT PRIMARY KEY NOT NULL ," + // 0: url
                "\"SAVE_PATH\" TEXT," + // 1: savePath
                "\"COUNT_LENGTH\" INTEGER NOT NULL ," + // 2: countLength
                "\"READ_LENGTH\" INTEGER NOT NULL ," + // 3: readLength
                "\"STATE_INTE\" INTEGER NOT NULL );"); // 4: stateInte
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DOWN_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DownBean entity) {
        stmt.clearBindings();
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(1, url);
        }
 
        String savePath = entity.getSavePath();
        if (savePath != null) {
            stmt.bindString(2, savePath);
        }
        stmt.bindLong(3, entity.getCountLength());
        stmt.bindLong(4, entity.getReadLength());
        stmt.bindLong(5, entity.getStateInte());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DownBean entity) {
        stmt.clearBindings();
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(1, url);
        }
 
        String savePath = entity.getSavePath();
        if (savePath != null) {
            stmt.bindString(2, savePath);
        }
        stmt.bindLong(3, entity.getCountLength());
        stmt.bindLong(4, entity.getReadLength());
        stmt.bindLong(5, entity.getStateInte());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public DownBean readEntity(Cursor cursor, int offset) {
        DownBean entity = new DownBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // url
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // savePath
            cursor.getLong(offset + 2), // countLength
            cursor.getLong(offset + 3), // readLength
            cursor.getInt(offset + 4) // stateInte
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DownBean entity, int offset) {
        entity.setUrl(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSavePath(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCountLength(cursor.getLong(offset + 2));
        entity.setReadLength(cursor.getLong(offset + 3));
        entity.setStateInte(cursor.getInt(offset + 4));
     }
    
    @Override
    protected final String updateKeyAfterInsert(DownBean entity, long rowId) {
        return entity.getUrl();
    }
    
    @Override
    public String getKey(DownBean entity) {
        if(entity != null) {
            return entity.getUrl();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DownBean entity) {
        return entity.getUrl() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
