package com.yoursecondworld.secondworld.modular.db.userGames;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cxj on 2016/8/24.
 * 动态的草稿箱的数据库对象
 */
public class NewUserGamesDb extends SQLiteOpenHelper {

    /**
     * 动态的草稿箱的数据库文件名字
     */
    private static String dbName = "newUserGames.db";

    /**
     * 动态的草稿箱的数据库版本号
     */
    private static int version = 1;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public NewUserGamesDb(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建游戏标签表
        String sql = "create table newUserGames(" +
                "user_id text," +
                "game_name text)";
        //执行sql语句创建表
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
