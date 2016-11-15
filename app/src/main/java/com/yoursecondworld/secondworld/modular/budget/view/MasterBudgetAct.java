package com.yoursecondworld.secondworld.modular.budget.view;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.commonBean.MasterInfo;
import com.yoursecondworld.secondworld.modular.budget.presenter.MasterBudgetPresenter;

import java.util.Calendar;

import xiaojinzi.annotation.Injection;

/**
 * 预算的界面
 */
@Injection(R.layout.act_budget)
public class MasterBudgetAct extends BaseAct implements IMasterBudgetView {

    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back;

    @Injection(R.id.tv_title_name)
    private TextView tv_title;

    @Injection(value = R.id.tv_money_complete, click = "clickView")
    private TextView tv_money_complete;

    @Injection(value = R.id.tv_time_complete, click = "clickView")
    private TextView tv_time_complete;

    @Injection(R.id.et_money)
    private EditText et_money;

    @Injection(R.id.et_time)
    private EditText et_time;

    private MasterBudgetPresenter presenter = new MasterBudgetPresenter(this);

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("预算设置");
    }

    private int year;
    private int month;

    @Override
    public void initData() {
        super.initData();

        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;

        presenter.getMasterInfoByMonth(year, month);

    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_money_complete:

                //设置当月的Master的金钱
                presenter.setMasterMoney(year, month);

                break;

            case R.id.tv_time_complete:

                //设置当月的Master的时间
                presenter.setMasterTime(year, month);

                break;
        }
    }


    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    public int getMoney() {
        int value = 0;
        try {
            value = Integer.parseInt(et_money.getText().toString());
        } catch (Exception e) {
        }
        return value;
    }

    @Override
    public int getTime() {
        int value = 0;
        try {
            value = Integer.parseInt(et_time.getText().toString());
        } catch (Exception e) {
        }
        return value * 60;
    }


    @Override
    public void onLoadMasterInfoSuccess(MasterInfo masterInfo) {
        int money = masterInfo.getMoney();
        et_money.setText(money + "");

        //这里返回的是分钟
        int time = masterInfo.getTime();
        et_time.setText((time / 60) + "");
    }
}
