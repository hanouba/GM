package com.yoursecondworld.secondworld.modular.gameTimeDetail.view;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.commonBean.MasterInfo;
import com.yoursecondworld.secondworld.modular.gameTimeDetail.bean.GameTimeDetai;
import com.yoursecondworld.secondworld.modular.gameTimeDetail.presenter.GameTimeDetailPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * 游戏时间明细界面
 */
@Injection(R.layout.act_game_time_detail)
public class GameTimeDetailAct extends BaseAct implements IGameTimeDetailView {

    public static final String YEAR_FLAG = "year_flag";

    public static final String MONTH_FLAG = "month_flag";


    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back;

    @Injection(R.id.tv_title_name)
    private TextView tv_title;

    //=====================================================

    @Injection(R.id.rv)
    private RecyclerView rv;

    /**
     * 要显示的数据
     */
    private List<GameTimeDetai> data = new ArrayList<GameTimeDetai>();

    private CommonRecyclerViewAdapter adapter;

    private GameTimeDetailHeader header = new GameTimeDetailHeader();

    private GameTimeDetailPresenter presenter = new GameTimeDetailPresenter(this);

    @Override
    public void initView() {
        super.initView();
        super.initView();

        tv_title.setVisibility(View.INVISIBLE);
        //拿到年份,默认是当前年份
        year = getIntent().getIntExtra(YEAR_FLAG, Calendar.getInstance().get(Calendar.YEAR));
        //拿到月份,默认是当前月份
        month = getIntent().getIntExtra(MONTH_FLAG, Calendar.getInstance().get(Calendar.MONTH) + 1);


        //创建适配器
        adapter = new CommonRecyclerViewAdapter<GameTimeDetai>(context, data) {

            @Override
            public void convert(CommonRecyclerViewHolder h, GameTimeDetai entity, int position) {
                h.setText(R.id.tv_date, month + "-" + entity.getDay());
                h.setText(R.id.tv_game, entity.getGame_name());
                String content = "        ";
                String tmp = "-h" + (entity.getCost_time() * 100 / 60 / 100f);
                content = content.substring(tmp.length() > content.length() ? content.length() : tmp.length());
                content = tmp + content;
                h.setText(R.id.tv_time_pay, content);
            }

            @Override
            public int getLayoutViewId(int viewType) {
                return R.layout.act_game_time_detail_item;
            }

        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        adapter.addHeaderView(header.init(context, month));

        rv.setAdapter(adapter);



    }

    private int year;
    private int month;

    @Override
    public void initData() {
        super.initData();

        presenter.getMasterInfoByMonth(year, month);

    }

    public void clickView(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_back: //返回按钮

                finish();

                break;
        }
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    public void onLoadMasterInfoSuccess(MasterInfo masterInfo) {
        List<GameTimeDetai> list = masterInfo.getTime_cost_list();
        Collections.reverse(list);
        AdapterNotify.notifyFreshData(data, list, adapter);
        header.disPlay(masterInfo);
    }

}
