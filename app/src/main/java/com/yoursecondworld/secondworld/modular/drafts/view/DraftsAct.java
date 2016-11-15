package com.yoursecondworld.secondworld.modular.drafts.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.db.dynamicsDraft.DynamicsDraftDao;
import com.yoursecondworld.secondworld.modular.db.dynamicsDraft.DynamicsDraftDb;
import com.yoursecondworld.secondworld.modular.drafts.adapter.DraftsActAdapter;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.postDynamics.view.PostDynamicsAct;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.SystemUtil;

/**
 * 动态的草稿箱,存放发布的时候放弃的草稿
 */
@Injection(R.layout.act_drafts)
public class DraftsAct extends BaseAct {

    /**
     * 标题栏的容器
     */
    @Injection(R.id.rl_act_titlebar_container)
    private RelativeLayout rl_titlebarContainer = null;

    /**
     * 标题栏
     */
    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    /**
     * 返回按钮
     */
    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    /**
     * 标题栏的文本
     */
    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    //============================================================================

    /**
     * 数据库操作对象
     */
    private DynamicsDraftDao draftDao;

    @Injection(R.id.rv)
    private RecyclerView rv = null;

    /**
     * 草稿箱中的内容的集合
     */
    private List<NewDynamics> dynamicsContentEntities;

    /**
     * 适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    /**
     * 点击了条目去发布页面的item位置
     */
    private int draftIdToPostDynamics = -1;

    @Override
    public void initView() {
        super.initView();

        //初始化基本信息
        initBase();

        //初始化数据库框架
        draftDao = new DynamicsDraftDao(new DynamicsDraftDb(context));

    }

    /**
     * 初始化基本信息
     */
    private void initBase() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("草稿箱");

        //针对白色的标题栏需要做的操作
        boolean b = SystemUtil.FlymeSetStatusBarLightMode(getWindow(), true);
        if (!b) {
            b = SystemUtil.MIUISetStatusBarLightMode(getWindow(), true);
            if (!b) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
                    //设置标题栏背景为黑色
                    rl_titlebarContainer.setBackgroundColor(Color.BLACK);
                }
            }
        }
    }

    @Override
    public void initData() {
        super.initData();

        //拿到保存的草稿
        dynamicsContentEntities = draftDao.queryAllByUserId(StaticDataStore.newUser.getUser_id());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = AutoUtils.getPercentHeightSize(2);
            }
        });

        rv.setLayoutManager(manager);

        adapter = new DraftsActAdapter(this, dynamicsContentEntities);

        rv.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (draftIdToPostDynamics > -1) {
            NewDynamics newDynamics = draftDao.queryById(dynamicsContentEntities.get(draftIdToPostDynamics).getId());
            if (newDynamics != null) {
                dynamicsContentEntities.set(draftIdToPostDynamics, newDynamics);
                adapter.notifyItemChanged(draftIdToPostDynamics);
            }
        }
    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        adapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
            @Override
            public void onViewInItemClick(View v, int position) {
                int id = v.getId();
                switch (id) {
                    case R.id.iv_delete: //删除按钮

                        //弹出是否真的删除的对话框
                        popupDeleteTip(position);

                        break;
                }
            }
        }, R.id.iv_delete);

        /**
         * 设置item点击事件
         */
        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //拿到草稿对象
                NewDynamics newDynamics = dynamicsContentEntities.get(position);

                //创建跳转的意图
                Intent i = new Intent(context, PostDynamicsAct.class);

                //图片
                ArrayList<String> picture_list = (ArrayList<String>) newDynamics.getPicture_list();
                int size = picture_list.size();
                for (int j = size - 1; j >= 0; j--) { //如果草稿这里点击到发布的界面,如果文件路径对应的文件已经不存在了,那就直接删除这个图片,然后再传过去
                    File f = new File(picture_list.get(j));
                    if (!f.exists()) {
                        picture_list.remove(j);
                    }
                }
                i.putStringArrayListExtra(PostDynamicsAct.IMAGES_TAG, picture_list);

                //视频
                List<String> video_list = newDynamics.getVideo_list();
                if (video_list.size() > 0) {
                    String videoPath = video_list.get(0);
                    File f = new File(videoPath);
                    if (f.exists()) { //如果视频的文件还存在,那就传递过去路径信息,如果不存在就放弃传递
                        i.putExtra(PostDynamicsAct.VIDEO_TAG, videoPath);
                    }
                }

                //内容
                i.putExtra(PostDynamicsAct.CONTENT_TAG, newDynamics.getContent());

                //两个标签
                i.putExtra(PostDynamicsAct.GAME_TAG, newDynamics.getGame_tag());
                i.putExtra(PostDynamicsAct.TYPE_TAG, newDynamics.getType_tag());

                //剁手金额
                i.putExtra(PostDynamicsAct.MONEY_TAG, newDynamics.getMoney());

                //传递草稿的id过去
                i.putExtra(PostDynamicsAct.DRAFT_ID_TAG, newDynamics.getId());

                //跳转到发布的界面
                context.startActivity(i);

                //记录当前点击的这个item的位置
                draftIdToPostDynamics = position;

            }
        });

    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        //获取控件的id
        int id = v.getId();

        //对id的筛选然后做处理
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
        }

    }

    /**
     * 弹出是否真的删除的提示
     */
    private void popupDeleteTip(final int position) {

        new AlertDialog.Builder(context)
                .setMessage("真的要删除这条草稿么?")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //拿到草稿的id
                        Integer draftId = dynamicsContentEntities.get(position).getId();
                        //删除这个id对应的草稿
                        draftDao.delete(draftId);
                        //试图中移除数据
                        dynamicsContentEntities.remove(position);
                        //通知试图改变
                        adapter.notifyItemRemoved(position);
                    }
                })
                .show();
    }

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    /**
     * 当sessionId失效了
     */
    @Subscribe
    public void onSessionIdInvalid(SessionInvalidEvent event) {
        finish();
    }

}
