package com.yoursecondworld.secondworld.modular.kryptonGoldDetail.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.commonBean.MasterInfo;
import com.yoursecondworld.secondworld.modular.kryptonGoldDetail.bean.KryptonGoldDetai;
import com.yoursecondworld.secondworld.modular.kryptonGoldDetail.presenter.KryptonGoldDetailPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * 克金详情的界面
 */
@Injection(R.layout.act_krypton_gold_detail)
public class KryptonGoldDetailAct extends BaseAct implements IKryptonGoldDetailView {


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
    private List<KryptonGoldDetai> data = new ArrayList<KryptonGoldDetai>();

    private CommonRecyclerViewAdapter adapter;

    private KryptonGoldDetailHeader header = new KryptonGoldDetailHeader();

    private KryptonGoldDetailPresenter presenter = new KryptonGoldDetailPresenter(this);

    @Override
    public void initView() {
        super.initView();

        tv_title.setVisibility(View.INVISIBLE);
        //拿到年份,默认是当前年份
        year = getIntent().getIntExtra(YEAR_FLAG, Calendar.getInstance().get(Calendar.YEAR));
        //拿到月份,默认是当前月份
        month = getIntent().getIntExtra(MONTH_FLAG, Calendar.getInstance().get(Calendar.MONTH) + 1);

        //创建适配器
        adapter = new CommonRecyclerViewAdapter<KryptonGoldDetai>(context, data) {

            @Override
            public void convert(CommonRecyclerViewHolder h, KryptonGoldDetai entity, int position) {
                h.setText(R.id.tv_date, month + "-" + entity.getDay());
                h.setText(R.id.tv_game, entity.getGame_name());
                h.setText(R.id.tv_money_pay, "-¥" + entity.getCost_money());
            }

            @Override
            public int getLayoutViewId(int viewType) {
                return R.layout.act_krypton_gold_detail_item;
            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        adapter.addHeaderView(header.init(context, month));

        rv.setAdapter(adapter);


    }

    public void clickView(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_back: // 返回按钮

                finish();

                break;
        }
    }

    private int year;
    private int month;

    @Override
    public void initData() {
        super.initData();

        //获取当月master的信息
        presenter.getMasterInfoByMonth(year, month);

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    public void onLoadMasterInfoSuccess(MasterInfo masterInfo) {
        List<KryptonGoldDetai> list = masterInfo.getMoney_cost_list();
        Collections.reverse(list);
        AdapterNotify.notifyFreshData(data,list , adapter);
        header.disPlay(masterInfo);
    }


}
