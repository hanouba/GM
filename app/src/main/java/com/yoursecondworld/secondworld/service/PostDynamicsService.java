package com.yoursecondworld.secondworld.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.postDynamics.presenter.PostDynamicsPresenter;
import com.yoursecondworld.secondworld.modular.postDynamics.view.IPostDynamicsView;
import com.yoursecondworld.secondworld.service.event.AppendDynamicsTaskEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Vector;

import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.T;

/**
 * 发布的服务
 */
public class PostDynamicsService extends Service implements IPostDynamicsView {

    /**
     * 线程同步的
     */
    private Vector<NewDynamics> tasks = new Vector<NewDynamics>();

    //发布的主持人
    private PostDynamicsPresenter presenter = new PostDynamicsPresenter(this);

    /**
     * 是否正在工作
     */
    private boolean isRunning;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    public PostDynamicsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        L.s("服务销毁了");
    }

    /**
     * 添加一个任务,这里要能做到多个任务后台能依次做完
     *
     * @param appendDynamicsTaskEvent
     */
    @Subscribe
    public void onEventAppendDyanmicsTask(AppendDynamicsTaskEvent appendDynamicsTaskEvent) {
        //T.showShort(this, "收到任务");
        //添加任务
        tasks.add(appendDynamicsTaskEvent.newDynamics);
        if (!isRunning) {
            startPost();
        }
    }

    private NewDynamics newDynamics;

    /**
     * 开始提交任务
     */
    private void startPost() {
        if (tasks.size() == 0) {
            isRunning = false;
            return;
        }
        isRunning = true;
        //拿出一个任务
        newDynamics = tasks.remove(0);
        presenter.postDynamics();
    }

    @Override
    public ProgressDialog getDialog() {
        return null;
    }

    @Override
    public List<String> getSelectImages() {
        return newDynamics.getPicture_list();
    }

    @Override
    public String getVideoPath() {
        return newDynamics.getVideo_list() == null || newDynamics.getVideo_list().size() == 0 ? null : newDynamics.getVideo_list().get(0);
    }

    @Override
    public String getDynamicsContent() {
        return newDynamics.getContent();
    }

    @Override
    public String getTopic() {
        return newDynamics.getType_tag();
    }

    @Override
    public Integer getMoney() {
        return newDynamics.getMoney();
    }

    @Override
    public String getGameLabel() {
        return newDynamics.getGame_tag();
    }

    @Override
    public String getExtraInfo() {
        return newDynamics.getInfor_type();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishActivity() {
    }

    @Override
    public void showDialog(String content) {
    }

    @Override
    public void closeDialog() {
        //这个方法在无论上一个任务失败还是成功都会被执行一次
        startPost();
    }

    @Override
    public void tip(String content) {
        T.showShort(this, content);
    }

    @Override
    public void onSessionInvalid() {

    }

}
