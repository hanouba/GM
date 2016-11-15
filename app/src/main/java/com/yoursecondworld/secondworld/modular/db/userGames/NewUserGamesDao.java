package com.yoursecondworld.secondworld.modular.db.userGames;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/9/6.
 */
public class NewUserGamesDao {

    private NewUserGamesDb db;

    public NewUserGamesDao(NewUserGamesDb db) {
        this.db = db;
    }

    /**
     * 保存一个用户的游戏标签,保存的时候会先删除全部然后再插入
     *
     * @param games
     */
    public void save(List<String> games) {
        deleteAll();
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "insert into newUserGames (game_name) values (?)";
        for (int i = 0; i < games.size(); i++) {
            String gameName = games.get(i);
            database.execSQL(sql, new Object[]{gameName});
        }
        database.close();
    }

    public List<String> query() {
        List<String> games = null;
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select * from newUserGames";
        Cursor c = database.rawQuery(sql, new String[]{});
        if (c != null) {
            games = new ArrayList<String>();
            while(c.moveToNext()){
                games.add(c.getString(c.getColumnIndex("game_name")));
            }
        }
        c.close();
        database.close();
        return games;
    }

    /**
     * 删除全部
     */
    public void deleteAll() {
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "delete from newUserGames";
        database.execSQL(sql, new Object[]{});
        database.close();
    }

}
