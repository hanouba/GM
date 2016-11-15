package com.yoursecondworld.secondworld.modular.db.newUser;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

/**
 * Created by cxj on 2016/9/6.
 * 本身设计用来保存用户信息
 */
public class NewUserDao {

    private NewUserDb db;

    public NewUserDao(NewUserDb db) {
        this.db = db;
    }

    public void insert(NewUser newUser) {
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "insert into newUser (user_id,user_nickname,user_sex,user_head_image,user_introduction) values (?,?,?,?,?)";
        database.execSQL(sql, new Object[]{newUser.getUser_id(), newUser.getUser_nickname(), newUser.getUser_sex(), newUser.getUser_head_image(), newUser.getUser_introduction()});
        database.close();
    }

    public void deleteByUserId() {
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "delete from newUser";
        database.execSQL(sql, null);
        database.close();
    }

    public void update(NewUser newUser) {
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "update newUser set user_id = ?,user_nickname = ?,user_sex = ?,user_head_image = ?,user_introduction = ?) values (?,?,?,?,?)";
        database.execSQL(sql, new Object[]{newUser.getUser_id(), newUser.getUser_nickname(), newUser.getUser_sex(), newUser.getUser_head_image(), newUser.getUser_introduction()});
        database.close();
    }

    public NewUser queryByUserId() {
        SQLiteDatabase database = db.getReadableDatabase();
        String sql = "select * from newUser";
        Cursor c = database.rawQuery(sql, null);

        if (c.moveToNext()) {
            NewUser user = new NewUser();
            user.setUser_id(c.getString(c.getColumnIndex("user_id")));
            user.setUser_nickname(c.getString(c.getColumnIndex("user_nickname")));
            user.setUser_sex(c.getString(c.getColumnIndex("user_sex")));
            user.setUser_head_image(c.getString(c.getColumnIndex("user_head_image")));
            user.setUser_introduction(c.getString(c.getColumnIndex("user_introduction")));
            c.close();
            database.close();
            return user;
        }

        c.close();
        database.close();
        return null;
    }

}
