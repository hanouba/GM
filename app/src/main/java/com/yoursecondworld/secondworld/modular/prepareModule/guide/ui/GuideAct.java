package com.yoursecondworld.secondworld.modular.prepareModule.guide.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDao;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDb;
import com.yoursecondworld.secondworld.modular.main.ui.MainAct;
import com.yoursecondworld.secondworld.modular.prepareModule.login.view.LoginAct;
import com.yoursecondworld.secondworld.modular.prepareModule.register.view.RegisterAct;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.ui.SelectPlayerAct;

import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.store.SPUtil;

/**
 * 引导界面
 */
@Injection(R.layout.act_guide)
public class GuideAct extends BaseAct {

    @Injection(value = R.id.bt_act_guide_register, click = "clickView")
    private Button bt_register = null;

    @Injection(value = R.id.bt_act_guide_login, click = "clickView")
    private Button bt_login = null;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void initView() {
        super.initView();

//        bt_register.setAlpha(0.2f);

        initSystem();

    }

    /**
     * 初始化MyApp中的东西
     */
    private void initSystem() {

    }

    @Override
    public void initData() {
        super.initData();

        //拿到登陆用的两个信息
        String sessionId = SPUtil.get(context, Constant.RESULT_SESSION_ID, "");
        String objectId = SPUtil.get(context, Constant.RESULT_OBJECT_ID, "");

        if (!TextUtils.isEmpty(sessionId) && !TextUtils.isEmpty(objectId)) {
            StaticDataStore.session_id = sessionId;
            StaticDataStore.newUser.setUser_id(objectId);
            //保存游戏标签
            NewUserGamesDao newUserGamesDao = new NewUserGamesDao(new NewUserGamesDb(context));
            List<String> games = newUserGamesDao.query();
            if (games != null) {
                StaticDataStore.newUser.setGames(games);
            }
            ActivityUtil.startActivity(context, MainAct.class);
//            ActivityUtil.startActivity(context, SelectPlayerAct.class);
            finish();
        }

    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.bt_act_guide_register: //去注册的界面
                ActivityUtil.startActivity(context, RegisterAct.class);
                finish();
                break;
            case R.id.bt_act_guide_login: //去登陆的界面
                ActivityUtil.startActivity(context, LoginAct.class);
                finish();
                break;
        }
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    public boolean isTranslucentNavigation() {
        return false;
    }

    @Override
    public boolean isFullScreen() {
        return true;
    }

}
