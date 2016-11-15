package com.yoursecondworld.secondworld.modular.main.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseFragmentAct;
import com.yoursecondworld.secondworld.common.ColorUtil;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.ImUtil;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ItemMenuEventEntity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ReFreshIMUserInfoEvent;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ShareEventEntity;
import com.yoursecondworld.secondworld.modular.main.find.fragment.FindFragment;
import com.yoursecondworld.secondworld.modular.main.home.fragment.HomeFragment;
import com.yoursecondworld.secondworld.modular.main.master.fragment.MasterFragment;
import com.yoursecondworld.secondworld.modular.main.my.fragment.MyFragment;
import com.yoursecondworld.secondworld.modular.main.popupWindow.itemMenu.ItemMenu;
import com.yoursecondworld.secondworld.modular.main.popupWindow.newShare.PopupShare;
import com.yoursecondworld.secondworld.modular.prepareModule.login.view.LoginAct;
import com.yoursecondworld.secondworld.modular.selectPostGame.view.SelectPostGameAct;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;
import com.yoursecondworld.secondworld.modular.userDetail.presenter.UserDetailPresenter;
import com.yoursecondworld.secondworld.modular.userDetail.view.IUserDetailView;

import org.greenrobot.eventbus.Subscribe;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;

/**
 * 主界面
 */
@Injection(R.layout.act_main)
public class MainAct extends BaseFragmentAct implements IUserDetailView, IUserView {

    /**
     * 用于显示Fragment
     */
    @Injection(R.id.fl_act_main_content)
    private FrameLayout fl_content = null;

    /**
     * 导航栏的首页
     */
    @Injection(value = R.id.rl_act_main_foot_home_container, click = "clickView")
    private RelativeLayout rl_home = null;

    /**
     * 首页的图标
     */
    @Injection(R.id.iv_act_main_foot_home)
    private ImageView iv_home = null;

    /**
     * 首页的文字
     */
    @Injection(R.id.tv_act_main_foot_home)
    private TextView tv_home = null;

    /**
     * 导航栏的Master
     */
    @Injection(value = R.id.rl_act_main_foot_master_container, click = "clickView")
    private RelativeLayout rl_master = null;

    /**
     * 首页的图标
     */
    @Injection(R.id.iv_act_main_foot_master)
    private ImageView iv_master = null;

    /**
     * 首页的文字
     */
    @Injection(R.id.tv_act_main_foot_master)
    private TextView tv_master = null;

    /**
     * 导航栏正中间的加号
     */
    @Injection(value = R.id.iv_act_main_foot_add, click = "clickView")
    private ImageView iv_postDynamics = null;

    /**
     * 导航栏的发现
     */
    @Injection(value = R.id.rl_act_main_foot_find_container, click = "clickView")
    private RelativeLayout rl_find = null;

    /**
     * 发现的图标
     */
    @Injection(R.id.iv_act_main_foot_find)
    private ImageView iv_find = null;

    /**
     * 发现的文字
     */
    @Injection(R.id.tv_act_main_foot_find)
    private TextView tv_find = null;

    /**
     * 导航栏的我的
     */
    @Injection(value = R.id.rl_act_main_foot_my_container, click = "clickView")
    private RelativeLayout rl_my = null;

    /**
     * 我的图标
     */
    @Injection(R.id.iv_act_main_foot_my)
    private ImageView iv_my = null;

    /**
     * 我的文字
     */
    @Injection(R.id.tv_act_main_foot_my)
    private TextView tv_my = null;

    /**
     * 导航栏中的文字选中的时候的颜色值
     */
    private int footTextSelectedColor;

    /**
     * 导航栏中的文字没有选中的时候的颜色值
     */
    private int footTextUnSelectedColor;

    @Override
    public void initView() {
        super.initView();

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) fl_content.getLayoutParams();
        lp.topMargin = statusHeight;

        initSystemInfo();

        //初始化颜色值
        footTextSelectedColor = ColorUtil.getColor(context, R.color.common_app_color);
        footTextUnSelectedColor = ColorUtil.getColor(context, R.color.black);

        changeToHome();

    }

    /**
     * 初始化系统信息的
     */
    private void initSystemInfo() {

        //初始化推送的
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //连接聊天的服务器
        if (ImUtil.isConnected()) {
//            RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "158579**627",
//                    TextMessage.obtain("我是消息内容"), null, null, null, null);
        } else {
            //尝试连接
            ImUtil.connect(context, null);
        }
    }

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    /**
     * 点击的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.rl_act_main_foot_home_container: //切换到主页

                setAllTabUnSelectState();

                //设置导航栏的首页的选中效果
                iv_home.setImageResource(R.mipmap.home_selected1);
                tv_home.setTextColor(footTextSelectedColor);

                //切换到主页
                changeToHome();

                break;
            case R.id.rl_act_main_foot_find_container: //切换到发现

                setAllTabUnSelectState();

                //设置导航栏的发现的选中效果
                iv_find.setImageResource(R.mipmap.find_selected1);
                tv_find.setTextColor(footTextSelectedColor);

                //切换到发现
                changeToFind();

                break;
            case R.id.rl_act_main_foot_master_container: //切换到Master

                setAllTabUnSelectState();

                iv_master.setImageResource(R.mipmap.master_selected1);
                tv_master.setTextColor(footTextSelectedColor);

                changeToMaster();

                break;

            case R.id.rl_act_main_foot_my_container: //切换到我的

                setAllTabUnSelectState();

                //设置导航栏的发现的选中效果
                iv_my.setImageResource(R.mipmap.my_selected1);
                tv_my.setTextColor(footTextSelectedColor);

                //切换到发现
                changeToMy();

                break;

            case R.id.iv_act_main_foot_add: //正中间的加号

                ActivityUtil.startActivity(context, SelectPostGameAct.class);

//                try {
//                    List<String> gameLabels = StaticDataStore.newUser.getGames();
//                    if (gameLabels == null || gameLabels.size() == 0) {
//                        T.showLong(context, "请先在个人信息设置界面选择自己喜欢的游戏标签吧\n" +
//                                "我的->用户详情->添加喜爱的游戏标签");
//                        return;
//                    }
//                    PostDynamicsPopupWindow postDynamicsPopupWindow = new PostDynamicsPopupWindow(context);
//                    postDynamicsPopupWindow.setHeight(ScreenUtils.getScreenHeightPixels(context) - ScreenUtils.getStatusHeight(context));
//                    postDynamicsPopupWindow.showAtLocation(iv_postDynamics, Gravity.BOTTOM, 0, 0);
//                    break;
//                } catch (Exception e) {
//                    L.s(TAG, "主页弹出选择游戏挂掉了");
//                    e.printStackTrace();
//                }

        }
    }


    /**
     * 设置底部的导航栏的所有选项卡变为没有选中的状态
     */
    private void setAllTabUnSelectState() {

        //设置导航栏的首页的未选中效果
        iv_home.setImageResource(R.mipmap.home_unselected1);
        tv_home.setTextColor(footTextUnSelectedColor);

        //设置导航栏的发现的未选中效果
        iv_find.setImageResource(R.mipmap.find_unselected1);
        tv_find.setTextColor(footTextUnSelectedColor);

        //设置导航栏的master的未选中效果
        iv_master.setImageResource(R.mipmap.master_unselected1);
        tv_master.setTextColor(footTextUnSelectedColor);

        //设置导航栏的我的未选中效果
        iv_my.setImageResource(R.mipmap.my_unselected1);
        tv_my.setTextColor(footTextUnSelectedColor);

    }

    /**
     * 更换fragment
     *
     * @param fragment
     */
    private void changeFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_act_main_content, fragment);
        ft.commit();
    }

    /**
     * Master的fragment
     */
    private MasterFragment masterFragment;

    /**
     * 切换到Master
     */
    private void changeToMaster() {
//        if (masterFragment == null) {
        masterFragment = new MasterFragment();
//        }
        changeFragment(masterFragment);
    }

    /**
     * 缓存发现页
     */
    private FindFragment findFragment;

    /**
     * 切换到发现页
     */
    private void changeToFind() {
        if (findFragment == null) {
            findFragment = new FindFragment();
        }
        changeFragment(findFragment);
    }

    /**
     * 我的
     */
    private MyFragment myFragment;

    /**
     * 切换到我的
     */
    private void changeToMy() {
        if (myFragment == null) {
            myFragment = new MyFragment();
        }
        changeFragment(myFragment);
    }

    /**
     * 缓存主页页
     */
    private HomeFragment homeFragment;

    /**
     * 切换到主页
     */
    private void changeToHome() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        changeFragment(homeFragment);
    }

    /**
     * 弹出分享的窗口,MainAct上面挂载的{@link HomeFragment} 和 {@link FindFragment}
     * 是有动态展示的,另外还有一个显示动态的地方是个新信息里面,但是不归这个MainAct所以这里不对他关心了
     * 两个Fragment中利用Event传递过来要分享的信息
     */
    @Subscribe
    public void popupShareWindow(ShareEventEntity shareEventEntity) {

        PopupShare popopShare = new PopupShare(context);

        popopShare.show();
        popopShare.getSb().setLinkUrl(Constant.DYNAMICS_SHARE_URL_PREFIX + shareEventEntity.dynamicsId);
        popopShare.getSb().setContent(shareEventEntity.content);

    }

    /**
     * 弹出点击item中的下拉小箭头之后的被调用
     *
     * @param item
     */
    @Subscribe
    public void onEventShowDynamicsItemMenu(ItemMenuEventEntity item) {
        if (item.from == ItemMenuEventEntity.FROM_HOME || item.from == ItemMenuEventEntity.FROM_FIND) {
            ItemMenu itemMenu = new ItemMenu(context, item);
            itemMenu.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销和结束资源
        RongIM.getInstance().logout();
        //断开聊天系统的连接
        RongIM.getInstance().disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(context);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(context);
    }

    private UserDetailPresenter userDetailPresenter = new UserDetailPresenter(this);

    private String preUpdateIMIconUserId;

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            userDetailPresenter.getUserInfo();
        }
    };

    /**
     * 刷新荣云的头像
     *
     * @param reFreshIMUserInfoEvent
     */
    @Subscribe
    public void onEventReFreshIMUserInfo(ReFreshIMUserInfoEvent reFreshIMUserInfoEvent) {
        preUpdateIMIconUserId = reFreshIMUserInfoEvent.userId;
        h.sendEmptyMessage(0);
    }

    @Override
    public void onLoadUserInfoSuccess(UserResult userResult) {
        //刷新容云的服务器
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userResult.getUser().getUser_id(), userResult.getUser().getUser_nickname(), Uri.parse(userResult.getUser().getUser_head_image() + Constant.HEADER_SMALL_IMAGE)));
    }

    /**
     * 当sessionId失效了
     */
    @Subscribe
    public void onSessionIdInvalid(SessionInvalidEvent event) {
        AppConfig.exitApp(context);
        //跳转到登陆的界面
        context.startActivity(new Intent(context, LoginAct.class));
        finish();
    }

    @Override
    public String getTargetUserId() {
        return preUpdateIMIconUserId;
    }


    @Override
    public void onFollowSuccess(Object... obs) {
    }

    @Override
    public void onUnFollowSuccess(Object... obs) {
    }

    @Override
    public void onBlockSuccess() {
    }

    @Override
    public void onUnBlockSuccess() {
    }

    private int pressCount = 0;

    /**
     * 实现返回两次退出应用的功能
     *
     * @param keyCode 键值
     * @param event   键的事件
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pressCount == 0) {
                tip("再按一次退出应用");
                pressCount++;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                        pressCount = 0;
                    }
                }.start();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSessionInvalid() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MasterFragment.REQUEST_GAME_NAME_CODE && resultCode == RESULT_OK && data != null) { //如果是Master界面选择游戏返回了数据
            //拿到游戏名称
            String gameName = data.getStringExtra("data");
            masterFragment.setGameName(gameName);
        }
    }



}
