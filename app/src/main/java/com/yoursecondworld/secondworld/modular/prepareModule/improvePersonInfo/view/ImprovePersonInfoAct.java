package com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaojinzi.ximagelib.config.XImgSelConfig;
import com.xiaojinzi.ximagelib.imageView.XSelectAct;
import com.xiaojinzi.ximagelib.utils.XImageLoader;
import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.ColorUtil;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.common.timePicker.DatePickerAct;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDao;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDb;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.eventEntity.SelectImageEventEntity;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.popupWindow.BottomMenuWindow;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.presenter.ImprovePersonInfoPresenter;
import com.yoursecondworld.secondworld.modular.prepareModule.login.view.LoginAct;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.ui.SelectPlayerAct;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.os.T;
import xiaojinzi.base.android.store.SPUtil;
import xiaojinzi.view.CircleImageView;

/**
 * 完善个人信息的界面,用于刚注册的用户
 * 带过来的一个from信息,如果是注册直接过来的,在返回的时候,跳转到登陆的界面
 * 在进入这个界面之前,需要在{@link MyApp}中存储一些信息
 * 1：`  d
 */
@Injection(R.layout.act_improve_person_info)
public class ImprovePersonInfoAct extends BaseAct implements IImprovePersonInfoView {

    /**
     * 请求选择图片的请求码
     */
    public static final int REQUEST_SELECT_IMAGE_CODE = 111 * 6;

    public static final String FROM_FLAG = "from";

    public static final String USERAVATAR_FLAG = "userAvatar";

    /**
     * 标题栏
     */
    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    /**
     * 返回图标
     */
    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    //=================================================================================================

    /**
     * 跟布局
     */
    @Injection(R.id.act_edit_person_info_root)
    private RelativeLayout rl_rootView = null;

    @Injection(R.id.rl_act_edit_person_info_content)
    private RelativeLayout rl_content = null;

    /**
     * 头像
     */
    @Injection(value = R.id.iv_act_edit_person_info_icon, click = "clickView")
    private CircleImageView simpleDraweeView = null;

    /**
     * 昵称
     */
    @Injection(R.id.et_nickname)
    private EditText et_nickname = null;

    //男女性别
    @Injection(value = R.id.ll_man, click = "clickView")
    private LinearLayout ll_man;
    @Injection(value = R.id.ll_women, click = "clickView")
    private LinearLayout ll_women;

    //男女性别
    @Injection(value = R.id.iv_act_edit_person_info_sex_man)
    private ImageView iv_man = null;
    @Injection(value = R.id.tv_sex_man)
    private TextView tv_man = null;
    @Injection(value = R.id.iv_act_edit_person_info_sex_women)
    private ImageView iv_women = null;
    @Injection(value = R.id.tv_sex_women)
    private TextView tv_women = null;

    @Injection(value = R.id.rl_age_container, click = "clickView")
    private RelativeLayout rl_age_container;

    /**
     * 完成的按钮
     */
    @Injection(value = R.id.tv_complete, click = "clickView")
    private TextView tv_complete = null;

    @Injection(value = R.id.tv_age)
    private TextView tv_age = null;

    /**
     * 性别
     */
    private String sex = "male";

    private ImprovePersonInfoPresenter infoPresenter = new ImprovePersonInfoPresenter(this);

    /**
     * 用户的默认头像
     */
    private String userdefultIconUrl = Constant.USER_DEFULT_ICONURL;

    private String localImagePath;

    @Override
    public void initView() {
        super.initView();
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("请设置你的个人资料");

        String mUserAvatar = getIntent().getStringExtra(USERAVATAR_FLAG);
        if (!TextUtils.isEmpty(mUserAvatar)) {
            userdefultIconUrl = mUserAvatar;
        }

        //设置图片的地址
        Glide.with(this).load(userdefultIconUrl).into(simpleDraweeView);

        if (StaticDataStore.newUser != null && StaticDataStore.newUser.getUser_nickname() != null) {
            //设置昵称
            et_nickname.setText(StaticDataStore.newUser.getUser_nickname());
            et_nickname.setSelection(et_nickname.getText().length());
        }

        //默认的
        tv_age.setText(birth);

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();
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
            case R.id.rl_age_container:
                //弹出选择年龄的
                popupAgeSelecter();
                break;
            case R.id.iv_act_edit_person_info_icon: //如果点击的是头像
                openSelectImageMenu();
                break;
            case R.id.ll_man:
                ll_man.setBackgroundColor(ColorUtil.getColor(this, R.color.gray_six));
                ll_women.setBackgroundColor(ColorUtil.getColor(this, R.color.white));
                tv_man.setTextColor((ColorUtil.getColor(this, R.color.gray_serven)));
                tv_women.setTextColor((ColorUtil.getColor(this, R.color.black)));
                iv_man.setImageResource(R.mipmap.sex_man_gray1);
                iv_women.setImageResource(R.mipmap.sex_women_black1);
                sex = "male";
                break;
            case R.id.ll_women:
                ll_man.setBackgroundColor(ColorUtil.getColor(this, R.color.white));
                ll_women.setBackgroundColor(ColorUtil.getColor(this, R.color.gray_six));
                tv_man.setTextColor((ColorUtil.getColor(this, R.color.black)));
                tv_women.setTextColor((ColorUtil.getColor(this, R.color.gray_serven)));
                iv_man.setImageResource(R.mipmap.sex_man_black1);
                iv_women.setImageResource(R.mipmap.sex_women_gray1);
                sex = "female";
                break;
            case R.id.tv_complete: //完成按钮

                NewUser newUser = StaticDataStore.newUser;

                infoPresenter.startImproveUserInfo(!getNickName().equals(newUser.getUser_nickname()));

                break;
        }

    }

    /**
     * 弹出选择年龄
     */
    private void popupAgeSelecter() {

        Intent intent = new Intent(context, DatePickerAct.class);
        startActivityForResult(intent, 222);

    }

    /**
     * 底部的菜单
     */
    private BottomMenuWindow bottomMenuWindow = null;

    /**
     * 关闭菜单
     */
    private void closeSelectImageMenu() {
        bottomMenuWindow.dismiss();
    }

    /**
     * 打开菜单
     */
    private void openSelectImageMenu() {
        if (bottomMenuWindow == null) {
            bottomMenuWindow = new BottomMenuWindow(context);
        }
        bottomMenuWindow.show();
    }

    /**
     * 发送选择图片的意图
     *
     * @param selectImageEventEntity 消息传递的时候的对象
     */
    @Subscribe
    public void sendSelectImageIntent(SelectImageEventEntity selectImageEventEntity) {

        XImageLoader imageLoader = new XImageLoader() {
            @Override
            public void load(Context context, String localPath, ImageView iv) {
                Glide.with(context).load(localPath).into(iv);
            }
        };

        XSelectAct.open(this, new XImgSelConfig.Builder(imageLoader)
                .btnConfirmText("完成")
                .title("图片")
                .isPreview(true)
                .statusBarColor(ColorUtil.getColor(this, R.color.common_app_color))
                .titlebarBgColor(ColorUtil.getColor(this, R.color.common_app_color))
                .maxNum(1)
                .isPreview(false)
                .cropSize(1, 1, 800, 800)
                .isCamera(true)
                .isCrop(true)
                .build(), 123);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //请求图片的请求码
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra("data");

            if (images != null && images.size() > 0) {
                localImagePath = images.get(0);
                disPlayImageToIcon();
            } else {
                T.showShort(context, "更换头像失败");
            }

        } else if (requestCode == 222 && resultCode == RESULT_OK && data != null) { //如果是采集日期的
            this.birth = "" + data.getIntExtra(DatePickerAct.YEAR_FLAG, 1995) + "-" +
                    (data.getIntExtra(DatePickerAct.MONTH_FLAG, 1) + 1) + "-" +
                    data.getIntExtra(DatePickerAct.DAY_FLAG, 1);
            tv_age.setText(this.birth);
        }
    }

    private void disPlayImageToIcon() {
        Glide.with(this).load(localImagePath).into(simpleDraweeView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && bottomMenuWindow != null && bottomMenuWindow.isShowing()) {  //如果是按了返回的按钮
            //关闭菜单
            closeSelectImageMenu();
            //并且阻止正常的返回
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public String getNickName() {
        return et_nickname.getText().toString();
    }

    @Override
    public String getSex() {
        return sex;
    }

    @Override
    public String getDefultImageUrl() {
        return userdefultIconUrl;
    }

    @Override
    public String getLocalImagePath() {
        return localImagePath;
    }


    /**
     * 完善信息成功,接着判断是否有游戏标签,如果没有的话就去选择游戏标签,如果有的话就直接去主界面
     */
    @Override
    public void onProveSuccess() {
        ActivityUtil.startActivity(context, SelectPlayerAct.class);
        saveSessionIdAndObjectId();
        super.finish();
    }


    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getUserId() {
        return StaticDataStore.newUser.getUser_id();
    }

    //默认的生日
    private String birth = "1995-01-01";

    @Override
    public String getBirth() {
        return birth;
    }

    @Override
    public void finish() {
//        if (fromTag.equals(RegisterAct.TAG)) {
        ActivityUtil.startActivity(context, LoginAct.class);
//        }
        super.finish();
    }

    private void saveSessionIdAndObjectId() {
        SPUtil.put(context, Constant.RESULT_SESSION_ID, StaticDataStore.session_id);
        SPUtil.put(context, Constant.RESULT_OBJECT_ID, StaticDataStore.newUser.getUser_id());

//        NewUserDao userDao = new NewUserDao(new NewUserDb(this));

        //保存最新的用户信息
//        userDao.deleteByUserId();
//        userDao.insert(MyApp.newUser);

        //保存游戏标签
        NewUserGamesDao userGamesDao = new NewUserGamesDao(new NewUserGamesDb(this));
        userGamesDao.save(StaticDataStore.newUser.getGames());

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
