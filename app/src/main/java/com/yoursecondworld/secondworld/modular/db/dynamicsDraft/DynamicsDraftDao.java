package com.yoursecondworld.secondworld.modular.db.dynamicsDraft;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsContentEntity;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.GameLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxj on 2016/8/24.
 * 动态草稿的数据库操作对象
 */
public class DynamicsDraftDao {

    private DynamicsDraftDb db;

    public DynamicsDraftDao(DynamicsDraftDb db) {
        this.db = db;
    }

    /**
     * 根据userID保存一个草稿,支持了分用户,不同用户之间看不到
     *
     * @param userId 用户的userId
     * @param entity
     */
    public void save(String userId, NewDynamics entity) {
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "insert into dynamicsDraft (userId,gameTag,topic,money,content,images,videoUrl,saveTime) values (?,?,?,?,?,?,?,?)";
        String images = null;
        List<String> picture_list = entity.getPicture_list();
        if (picture_list != null && picture_list.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < picture_list.size(); i++) {
                if (i == 0) {
                    sb.append(picture_list.get(i));
                } else {
                    sb.append(Constant.DYNAMICS_IMAGEPATH_SPLIT_CHAR + picture_list.get(i));
                }
            }
            images = sb.toString();
        }

        //视频的路径
        String videoPath = (entity.getVideo_list() == null || entity.getVideo_list().size() == 0) ? null : entity.getVideo_list().get(0);

        database.execSQL(sql, new Object[]{userId, entity.getGame_tag(), entity.getType_tag(), entity.getMoney(), entity.getContent(), images, videoPath, entity.getTime()});
        database.close();

    }

    /**
     * 根据id删除一个草稿
     *
     * @param id
     */
    public void delete(Integer id) {
        SQLiteDatabase database = db.getWritableDatabase();
        String sql = "delete from dynamicsDraft where _id = ?";
        database.execSQL(sql, new Object[]{id});
        database.close();
    }

    /**
     * 更新一个实体
     *
     * @param entity
     */
    public void update(NewDynamics entity) {
        SQLiteDatabase database = db.getWritableDatabase();
        //更新的sql语句
        String sql = "update dynamicsDraft set gameTag = ?,topic = ?,money = ?,content = ?,images = ?,videoUrl = ?,saveTime = ? where _id = ?";
        String images = null;
        List<String> picture_list = entity.getPicture_list();
        if (picture_list != null && picture_list.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < picture_list.size(); i++) {
                if (i == 0) {
                    sb.append(picture_list.get(i));
                } else {
                    sb.append(Constant.DYNAMICS_IMAGEPATH_SPLIT_CHAR + picture_list.get(i));
                }
            }
            images = sb.toString();
        }

        //视频的路径
        String videoPath = (entity.getVideo_list() == null || entity.getVideo_list().size() == 0) ? null : entity.getVideo_list().get(0);

        database.execSQL(sql, new Object[]{entity.getGame_tag(), entity.getType_tag(), entity.getMoney(), entity.getContent(), images, videoPath, entity.getTime(), entity.getId()});
        database.close();

    }

    /**
     * 根据id查询草稿
     *
     * @param id
     * @return
     */
    public NewDynamics queryById(int id) {
        //拿到数据库
        SQLiteDatabase database = db.getReadableDatabase();
        //书写sql语句
        String sql = "select _id,userId,gameTag,topic,money,content,images,videoUrl,saveTime from dynamicsDraft where _id = ?";
        //拿到结果集对象
        Cursor c = database.rawQuery(sql, new String[]{id + ""});
        if (c.moveToNext()) {
            NewDynamics newDynamics = converse(c);
            c.close();
            database.close();
            return newDynamics;
        }
        c.close();
        database.close();
        return null;
    }

    /**
     * 查询所有的草稿
     *
     * @return
     */
    public List<NewDynamics> queryAll() {
        //创建返回的结果对象
        List<NewDynamics> dynamicsContentEntities = new ArrayList<NewDynamics>();
        //拿到数据库
        SQLiteDatabase database = db.getReadableDatabase();
        //书写查询的sql语句
        String sql = "select _id,userId,gameTag,topic,money,content,images,videoUrl,saveTime from dynamicsDraft";
        //拿到结果集对象
        Cursor c = database.rawQuery(sql, null);
        while (c.moveToNext()) {

            NewDynamics entity = converse(c);

            //添加到集合中
            dynamicsContentEntities.add(entity);
        }
        c.close();
        //返回集合
        return dynamicsContentEntities;
    }

    /**
     * 根据用户查询所有的草稿
     *
     * @return
     */
    public List<NewDynamics> queryAllByUserId(String userId) {
        //创建返回的结果对象
        List<NewDynamics> dynamicsContentEntities = new ArrayList<NewDynamics>();
        //拿到数据库
        SQLiteDatabase database = db.getReadableDatabase();
        //书写查询的sql语句
        String sql = "select _id,userId,gameTag,topic,money,content,images,videoUrl,saveTime from dynamicsDraft where userId = ?";
        //拿到结果集对象
        Cursor c = database.rawQuery(sql, new String[]{userId});
        while (c.moveToNext()) {

            NewDynamics entity = converse(c);

            //添加到集合中
            dynamicsContentEntities.add(entity);
        }
        c.close();
        //返回集合
        return dynamicsContentEntities;
    }

    @NonNull
    private NewDynamics converse(Cursor c) {
        //创建一条记录对应的实体对象
        NewDynamics entity = new NewDynamics();

        //映射属性
        entity.setId(c.getInt(c.getColumnIndex("_id")));
        String gameTag = c.getString(c.getColumnIndex("gameTag"));
        entity.setGame_tag(gameTag);

        entity.setType_tag(c.getString(c.getColumnIndex("topic")));
        int money = c.getInt(c.getColumnIndex("money"));
        entity.setMoney(money);
        entity.setContent(c.getString(c.getColumnIndex("content")));
        String images = c.getString(c.getColumnIndex("images"));
        if (!TextUtils.isEmpty(images)) {
            String[] arrs = images.split(Constant.DYNAMICS_IMAGEPATH_SPLIT_CHAR + "");
            for (int i = 0; i < arrs.length; i++) {
                entity.getPicture_list().add(arrs[i]);
            }
        }

        //拿到视频的地址
        String videoUrl = c.getString(c.getColumnIndex("videoUrl"));

        //拿到保存的时间
        String saveTime = c.getString(c.getColumnIndex("saveTime"));
        entity.setTime(saveTime);

        if (!TextUtils.isEmpty(videoUrl)) {
            entity.getVideo_list().add(videoUrl);
        }
        return entity;
    }


}
