package com.yoursecondworld.secondworld.modular.db.newUser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cxj on 2016/8/24.
 * 动态的草稿箱的数据库对象
 */
public class NewUserDb extends SQLiteOpenHelper {

    /**
     * 动态的草稿箱的数据库文件名字
     */
    private static String newUserDb = "newUser.db";

    /**
     * 动态的草稿箱的数据库版本号
     */
    private static int newUserVersion = 1;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public NewUserDb(Context context) {
        super(context, newUserDb, null, newUserVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建用户表
        String sql = "create table newUser(" +
                "user_id text," +
                "user_nickname text," +
                "user_sex text," +
                "user_head_image text," +
                "user_introduction text)";
        //执行sql语句创建表
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
