package com.yoursecondworld.secondworld.modular.prepareModule.selectGame.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xiaojinzi.autolayout.utils.AutoUtils;

import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseFragmentAct;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.common.resultMsg.Msg;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDao;
import com.yoursecondworld.secondworld.modular.db.userGames.NewUserGamesDb;
import com.yoursecondworld.secondworld.modular.main.ui.MainAct;
import com.yoursecondworld.secondworld.modular.prepareModule.selectGame.adapter.SelectedGameActAdapter;
import com.yoursecondworld.secondworld.modular.prepareModule.selectGame.adapter.UnSelectedGameActAdapter;
import com.yoursecondworld.secondworld.modular.prepareModule.selectGame.entity.GameLabelResultEntity;
import com.yoursecondworld.secondworld.modular.prepareModule.selectGame.entity.GameLabelSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.prepareModule.selectGame.presenter.SelectGamePresenter;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.ui.SelectPlayerAct;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.GameLabel;


import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.T;

/**
 * 选择游戏标签的界面
 */
@Injection(R.layout.act_select_game)
public class SelectGameAct extends BaseFragmentAct implements ISelectGameView {


    //=========================================================两个显示标签列表的控件及其数据集合和适配器=====start===================================
    /**
     * 显示等待选择的标签的列表
     */
    @Injection(R.id.gv_act_select_game)
    private GridView gridView_unSelect = null;

    /**
     * 待选择的游戏标签
     */
    private List<String> unSelectGameLabels = new ArrayList<String>();

    /**
     * 待选择的标签适配器
     */
    private BaseAdapter adapter_unSelecte = null;

    /**
     * 显示选择的标签的列表
     */
    @Injection(R.id.gv_act_select_game_selected)
    private RecyclerView recyclerView_selected = null;

    /**
     * 已经选中的标签的控件的布局管理器
     */
    private LinearLayoutManager selectLabelLayoutManager;

    /**
     * 已经选择的标签的集合
     */
    private List<String> selectGameLabels = new ArrayList<String>();

    /**
     * 显示已经选择的标签的适配器
     */
    private CommonRecyclerViewAdapter adapter_selected = null;

    //=========================================================两个显示标签列表的控件及其数据集合和适配器=======end===================================

    /**
     * 显示已经有几个游戏的文本
     */
    @Injection(R.id.tv_act_select_game_selected)
    private TextView tv_gameCount = null;

    /**
     * 用于实现动画的标签,开始是隐藏的
     */
    @Injection(R.id.rl_flow_label_item)
    private RelativeLayout rl_flowItem = null;

    /**
     * 完成按钮
     */
    @Injection(value = R.id.bt_complete, click = "clickView")
    private Button bt = null;

    /**
     * 其实这个控件式参与动画的控件,是gridview中的选中的标签
     */
    private View animationView = null;

    private SelectGamePresenter presenter = new SelectGamePresenter(this);

    @Override
    public void initView() {
        super.initView();

        //初始化等待选择的标签的展示
        initUnSelectLabel();

        //初始化选择的标签的展示
        initSelectLabel();

        //先隐藏
        rl_flowItem.setVisibility(View.INVISIBLE);

    }

    /**
     * 初始化选择的标签的展示
     */
    private void initSelectLabel() {
        //创建布局管理器
//        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        selectLabelLayoutManager = new LinearLayoutManager(context);
        selectLabelLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置布局管理器
        recyclerView_selected.setLayoutManager(selectLabelLayoutManager);
        //这两个值是根据设计图计算得到
        int vSpacingInPixels = AutoUtils.getPercentHeightSize(44);
        int hSpacingInPixels = AutoUtils.getPercentWidthSize(40);
        //条目间隔的装饰
        GameLabelSpaceItemDecoration gameLabelSpaceItemDecoration = new GameLabelSpaceItemDecoration(vSpacingInPixels, hSpacingInPixels);
        recyclerView_selected.addItemDecoration(gameLabelSpaceItemDecoration);
        adapter_selected = new SelectedGameActAdapter(context, selectGameLabels);
        recyclerView_selected.setAdapter(adapter_selected);
    }

    /**
     * 初始化等待选择的标签的展示
     */
    private void initUnSelectLabel() {
        //创建适配器
        adapter_unSelecte = new UnSelectedGameActAdapter(context, unSelectGameLabels, R.layout.act_select_game_flow_label_item);
        gridView_unSelect.setAdapter(adapter_unSelecte);
    }

    /**
     * 数据显示到界面
     */
    public void disPlayData(List<String> gameLabels) {
        unSelectGameLabels.clear();
        unSelectGameLabels.addAll(gameLabels);
        adapter_unSelecte.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        super.initData();

        //通知主持人去加载数据
        presenter.getAllGameLabel();

    }


    @Override
    public void setOnlistener() {
        super.setOnlistener();

        /**
         * 监听待选标签的点击事件
         */
        gridView_unSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gameLabel = unSelectGameLabels.get(position);
                if (!selectGameLabels.contains(gameLabel)) {
                    selectGameLabels.add(0, gameLabel);
                    tv_gameCount.setText("已添加的游戏(" + selectGameLabels.size() + ")");
                    adapter_selected.notifyItemInserted(0);
                    selectLabelLayoutManager.scrollToPosition(0);
                } else {
                    T.showShort(context, "您已经选择过啦");
                }

            }
        });

        adapter_selected.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                if (position > -1 && position < selectGameLabels.size()) {
                    selectGameLabels.remove(position);
                    tv_gameCount.setText("已添加的游戏(" + selectGameLabels.size() + ")");
                    adapter_selected.notifyItemRemoved(position);
                }
            }
        });

    }


    /**
     * 点击事件集中处理
     *
     * @param view
     */
    public void clickView(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.bt_complete: //完成按钮

                presenter.improveGameLabel();

                break;
        }
    }

    /**
     * 提示用户
     *
     * @param content
     */
    public void tip(String content) {
        T.showShort(context, content);
    }

    /**
     * 获取选择的游戏标签的id的集合
     *
     * @return
     */
    @Override
    public String[] getSelectGameLabelIds() {
        //创建一个整形数组用来存放选择好的游戏标签的id
        String gamaLabelId[] = new String[selectGameLabels.size()];
        for (int i = 0; i < gamaLabelId.length; i++) {
            gamaLabelId[i] = selectGameLabels.get(i);
        }
        return gamaLabelId;
    }

    /**
     * 成功完善游戏标签的数据的时候回调
     */
    @Override
    public void onSuccess(String gamaLabelId[]) {

        if (gamaLabelId != null) {
            StaticDataStore.newUser.getGames().clear();
            for (int i = 0; i < gamaLabelId.length; i++) {
                StaticDataStore.newUser.getGames().add(gamaLabelId[i]);
            }
        }

        L.s(TAG,"游戏标签个数：" + StaticDataStore.newUser.getGames().size());

        //保存游戏标签
        NewUserGamesDao userGamesDao = new NewUserGamesDao(new NewUserGamesDb(this));
        userGamesDao.save(StaticDataStore.newUser.getGames());

        //跳转到关注玩家界面
        ActivityUtil.startActivity(context, SelectPlayerAct.class);

        //结束自己
        finish();

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
