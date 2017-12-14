package com.bwei.demo.utils.download.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ${张健}
 * @date 2017/11/14/14:33
 */

public class FileService {

    private DBOpenHelper openHelper;

    public FileService(Context context) {
        openHelper = new DBOpenHelper(context);
    }

    /**
     * 获取每条线程已经下载的文件长度
     *
     * @param path
     * @return
     */
    public Map<Integer, Integer> getData(String path) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor = db
                .rawQuery(
                        "select threadid, downlength from filedownlog where downpath=?",
                        new String[] { path });
        Map<Integer, Integer> data = new HashMap<Integer, Integer>();
        while (cursor.moveToNext()) {
            data.put(cursor.getInt(0), cursor.getInt(1));
        }
        cursor.close();
        db.close();
        return data;
    }

    /**
     * 保存每条线程已经下载的文件长度
     *
     * @param path
     * @param map
     */
    public void save(String path, Map<Integer, Integer> map) {// int threadid,
        // int position
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                db.execSQL(
                        "insert into filedownlog(downpath, threadid, downlength) values(?,?,?)",
                        new Object[] { path, entry.getKey(), entry.getValue() });
            }
            //设置一个事务成功的标志,如果成功就提交事务,如果没调用该方法的话那么事务回滚
            //就是上面的数据库操作撤销
            db.setTransactionSuccessful();
        } finally {
            //结束一个事务
            db.endTransaction();
        }
        db.close();
    }

    /**
     * 实时更新每条线程已经下载的文件长度
     *
     * @param path
     * @param map
     */
    public void update(String path, int threadId, int pos) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL(
                "update filedownlog set downlength=? where downpath=? and threadid=?",
                new Object[] { pos, path, threadId });
        db.close();
    }

    /**
     * 当文件下载完成后，删除对应的下载记录
     *
     * @param path
     */
    public void delete(String path) {
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.execSQL("delete from filedownlog where downpath=?",
                new Object[] { path });
        db.close();
    }

}
