package com.yoursecondworld.secondworld.modular.db.dynamicsDraft;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cxj on 2016/8/24.
 * 动态的草稿箱的数据库对象
 */
public class DynamicsDraftDb extends SQLiteOpenHelper {

    /**
     * 动态的草稿箱的数据库文件名字
     */
    private static String dynamicsDraftDb = "dynamicsDraft.db";

    /**
     * 动态的草稿箱的数据库版本号
     */
    private static int dynamicsDraftVersion = 1;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public DynamicsDraftDb(Context context) {
        super(context, dynamicsDraftDb, null, dynamicsDraftVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建动态草稿箱表
        String sql = "create table dynamicsDraft(" +
                "_id integer primary key autoincrement," +
                "userId text," +
                "gameTag text," +
                "topic text," +
                "content text," +
                "images text," +
                "money integer," +
                "saveTime text," +
                "videoUrl text)";
        //执行sql语句创建表
        sqLiteDatabase.execSQL(sql);
//        System.out.println("创建了表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
