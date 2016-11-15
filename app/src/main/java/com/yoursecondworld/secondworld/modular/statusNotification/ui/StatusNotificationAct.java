package com.yoursecondworld.secondworld.modular.statusNotification.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;

import com.yoursecondworld.secondworld.common.BaseFragmentAct;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.statusNotification.fragment.CommentFragment;
import com.yoursecondworld.secondworld.modular.statusNotification.fragment.ZanFragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import xiaojinzi.annotation.Injection;

/**
 * 状态通知的界面
 */
@Injection(R.layout.act_status_notifi)
public class StatusNotificationAct extends BaseFragmentAct {

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

    @Injection(value = R.id.tv_menu, click = "clickView")
    private TextView tv_menu = null;

    //=============================================================================

    @Injection(R.id.tl_act_status_notifi_tabhost)
    private TabLayout tabLayout;

    @Injection(R.id.vp_act_status_notifi_content)
    private ViewPager vp = null;

    /**
     * ViewPgaer使用的fragment
     */
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    /**
     * 适配器
     */
    private FragmentPagerAdapter adapter = null;

    @Override
    public void initView() {
        super.initView();

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("状态通知");

//        tabLayout.addTab(tabLayout.newTab().setText("评论").setTag(0));
//        tabLayout.addTab(tabLayout.newTab().setText("赞").setTag(1));

        fragments.add(new CommentFragment());
        fragments.add(new ZanFragment());

        //创建适配器
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        //设置适配器
        vp.setAdapter(adapter);

        tabLayout.setupWithViewPager(vp);

        tabLayout.getTabAt(0).setText("评论");
        tabLayout.getTabAt(1).setText("赞");

    }

    public void clickView(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.iv_back:
                finish();
                break;

        }
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
