package com.yoursecondworld.secondworld.modular.blackList.view;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.DateFormat;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.blackList.entity.BlockUser;
import com.yoursecondworld.secondworld.modular.blackList.presenter.BlackListPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.presenter.UserPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;

import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * 黑名单的界面
 */
@Injection(R.layout.act_blacklist)
public class BlacklistAct extends BaseAct implements IBlackListView, IUserView {

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

    @Injection(value = R.id.tv_menu, click = "clickView")
    private TextView tv_menu = null;

    //=============================================================================

    @Injection(R.id.rv)
    private RecyclerView rv = null;

    private CommonRecyclerViewAdapter adapter;

    private List<BlockUser> data = new ArrayList<BlockUser>();

    private BlackListPresenter blackListPresenter = new BlackListPresenter(this);

    private UserPresenter userPresenter = new UserPresenter(this);

    //
    @Override
    public void initView() {
        super.initView();

        initBase();

        initContent();

    }

    /**
     * 初始化内容
     */
    private void initContent() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        adapter = new CommonRecyclerViewAdapter<BlockUser>(context, data) {

            @Override
            public void convert(CommonRecyclerViewHolder h, BlockUser entity, int position) {

                //设置头像
                SimpleDraweeView icon = h.getView(R.id.icon);
                icon.setImageURI(Uri.parse(entity.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

                //设置文本
                h.setText(R.id.name, entity.getUser_nickname());

                TextView tv_date = h.getView(R.id.tv_date);
                //拿到返回的时间
                String time = entity.getBlocked_time();

                try {
                    //解析出日期时间
                    Date date = DynamicsContentRecyclerViewAdapter.dateUtil.parse(time);
                    //设置上去,按照一定的格式
                    tv_date.setText("拉黑时间:" + DateFormat.format(date.getTime()));
                } catch (ParseException e) {
                    tv_date.setText("拉黑时间:--:--");
                }

            }

            @Override
            public int getLayoutViewId(int viewType) {
                return R.layout.act_blacklist_item;
            }
        };

        rv.setAdapter(adapter);

    }

    /**
     * 初始化基本的信息
     */
    private void initBase() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("黑名单");

        //针对白色的标题栏需要做的操作
//        boolean b = SystemUtil.FlymeSetStatusBarLightMode(getWindow(), true);
//        if (!b) {
//            b = SystemUtil.MIUISetStatusBarLightMode(getWindow(), true);
//            if (!b) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                } else {
//                    //设置标题栏背景为黑色
//                    rl_titlebarContainer.setBackgroundColor(Color.BLACK);
//                }
//            }
//        }
    }

    @Override
    public void initData() {
        super.initData();

        blackListPresenter.getBlackList();

    }

    private int mPosition = -1;

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        adapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
            @Override
            public void onViewInItemClick(View v, int position) {
                int id = v.getId();
                switch (id) {
                    case R.id.tv_remove: //如果点击了移出
                        mPosition = position;
                        userPresenter.unblockUser(data.get(position).getUser_id());
                        break;
                }
            }
        }, R.id.tv_remove);

    }

    /**
     * 点击事件的集中处理
     *
     * @param view
     */
    public void clickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onLoadBlackSuccess(List<BlockUser> list) {

        AdapterNotify.notifyFreshData(data, list, adapter);

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
        data.remove(mPosition);
        adapter.notifyItemRemoved(mPosition);
        if (data.size() == 0) {
            finish();
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
