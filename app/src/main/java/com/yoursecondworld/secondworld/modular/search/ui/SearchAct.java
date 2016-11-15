package com.yoursecondworld.secondworld.modular.search.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseFragmentAct;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.search.fragment.showHot.ShowHotSearchResultFragment;
import com.yoursecondworld.secondworld.modular.search.fragment.showUser.ShowUserSearchResultFragment;
import com.yoursecondworld.secondworld.modular.search.popup.MenuPopup;
import com.yoursecondworld.secondworld.modular.search.presenter.SearchPresenter;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.view.TextWatcherAdapter;

/**
 * 搜索界面
 */
@Injection(R.layout.act_search)
public class SearchAct extends BaseFragmentAct {

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

    //=============================================================================

    @Injection(R.id.et_search_content)
    private EditText et_content = null;

    @Injection(value = R.id.iv_clear_input, click = "clickView")
    private ImageView iv_clear = null;

    @Injection(value = R.id.tv_search_type, click = "clickView")
    private TextView tv_search_type = null;

    @Injection(value = R.id.iv_header_search, click = "clickView")
    private ImageView iv_search = null;

    @Injection(R.id.vp)
    private ViewPager vp = null;

    private ShowHotSearchResultFragment showHotSearchResultFragment;

    private ShowUserSearchResultFragment showUserSearchResultFragment;

    @Override
    public void initView() {
        super.initView();

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

    }

    @Override
    public void initData() {
        super.initData();

        //挂载的两个fragmetn
        showHotSearchResultFragment = new ShowHotSearchResultFragment();
        showUserSearchResultFragment = new ShowUserSearchResultFragment();

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return position == 0 ? showHotSearchResultFragment : showUserSearchResultFragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tv_search_type.setText(position == 0 ? "热门" : "用户");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        et_content.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                //拿到输入之后的内容
                String content = s.toString();
                if (TextUtils.isEmpty(content)) { //如果是空的
                    if (iv_clear.getVisibility() != View.INVISIBLE) {
                        iv_clear.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (iv_clear.getVisibility() != View.VISIBLE) {
                        iv_clear.setVisibility(View.VISIBLE);
                    }
                }
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
            case R.id.iv_clear_input: //清除图标

                //清空输入的
                et_content.getText().clear();

                break;

            case R.id.tv_search_type:

                showPopupWindow(v);

                break;
            case R.id.iv_header_search: //点击了搜索的按钮

                int currentItem = vp.getCurrentItem();

                if (currentItem == 0) {
                    showHotSearchResultFragment.startSearchDynamics(et_content.getText().toString().trim());
                } else {
                    showUserSearchResultFragment.startSearchUser(et_content.getText().toString().trim());
                }

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

    private void showPopupWindow(View parent) {
        final MenuPopup popupWindow = new MenuPopup(context);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(AutoUtils.getPercentHeightSize(140));
        int locations[] = new int[2];
        parent.getLocationOnScreen(locations);
        popupWindow.showAsDropDown(parent, 0, 0, Gravity.TOP);
        popupWindow.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.tv_tab_text1: //热门

                        vp.setCurrentItem(0);

                        break;
                    case R.id.tv_tab_text2: //用户

                        vp.setCurrentItem(1);

                        break;
                }
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
