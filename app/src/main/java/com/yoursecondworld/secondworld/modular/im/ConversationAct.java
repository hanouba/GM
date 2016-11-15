package com.yoursecondworld.secondworld.modular.im;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import xiaojinzi.autolayout.AutoLayoutFragmentActivity;
import xiaojinzi.autolayout.utils.AutoUtils;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.im.popupMenu.PopupMenu;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;


import xiaojinzi.annotation.Injection;
import xiaojinzi.annotation.ViewInjectionUtil;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.ScreenUtils;


/**
 * 聊天的界面,荣云自己启动的,无需管
 */
public class ConversationAct extends AutoLayoutFragmentActivity {

    //跳转过来之前,这个值一定要设置上,用于在弹出小菜单的时候使用的
    public static String targetUserId;

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

    //=========================================================================================================================

    /**
     * 上下文
     */
    private Context context = null;

    @Injection(R.id.rl_act_conversation)
    private RelativeLayout rl_root = null;

    @Injection(R.id.rl_act_conversation_content)
    private RelativeLayout rl_content = null;

    @Injection(value = R.id.iv_menu, click = "clickView")
    private ImageView iv_menu;

    /**
     * 屏幕高度
     */
    private int screenHeight;

    /**
     * 状态栏的高度
     */
    private int stateHeight;

    /**
     * 标题栏高度
     */
    private int titlebarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_conversation);

        context = this;

        //拿到屏幕的高度
        screenHeight = ScreenUtils.getScreenHeightPixels(context);
        //拿到状态栏的高度
        stateHeight = ScreenUtils.getStatusHeight(context);
        //拿到标题栏的高度
        titlebarHeight = (int) getResources().getDimension(R.dimen.titlebar_height);
        //缩放适配到此手机
        titlebarHeight = AutoUtils.getPercentHeightSize(titlebarHeight);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //让注解起作用
        ViewInjectionUtil.injectView(this);

        initView();

        setOnListener();

    }


    public void initView() {

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = ScreenUtils.getStatusHeight(this);

        tv_titleName.setText("聊天");

    }

    /**
     * 设置各种监听
     */
    public void setOnListener() {
        controlKeyboardLayout(rl_root, null);

        RongIM.setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {

            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                //跳转到用户详情
                Intent i = new Intent(context, UserDetailAct.class);
                i.putExtra(UserDetailAct.TARGET_USER_ID_FLAG, userInfo.getUserId());
                startActivity(i);
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });

    }

    /**
     * 在聊天的内容比较多的时候没有问题,当时在内容比较少的时候上面的内容就会看不见,所以这个方法也是不可取的
     *
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private void controlKeyboardLayout(final View root, final View needToScrollView) {

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                ConversationAct.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = ConversationAct.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                ViewGroup.LayoutParams layoutParams = rl_content.getLayoutParams();
                //拿到需要调整的高度,这里需要按照实际情况计算你们自己的高度
                //博主需要的高度是：屏幕宽度-状态栏高度-标题栏高度-键盘的高度
                int height = screenHeight - titlebarHeight - heightDifference - stateHeight;
                //如果计算出来的和原来的不一样,那么就调整一下
                if (height != layoutParams.height) {
                    //设置新的高度
                    layoutParams.height = height;
                    //重新布局
                    rl_root.requestLayout();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        int height = rl_content.getHeight();
        L.s("height = " + rl_content.getMeasuredHeight());
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
            case R.id.iv_menu: //右上角的小菜单

                //弹出举报TA和屏蔽此人的菜单
                PopupMenu popupMenu = new PopupMenu(context);
                popupMenu.show();

                break;
        }

    }

}
