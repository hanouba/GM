package com.yoursecondworld.secondworld.modular.main.master.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.view.WaveLoadingView;
import com.yoursecondworld.secondworld.commonBean.MasterInfo;
import com.yoursecondworld.secondworld.modular.budget.view.MasterBudgetAct;
import com.yoursecondworld.secondworld.modular.gameTimeDetail.view.GameTimeDetailAct;
import com.yoursecondworld.secondworld.modular.kryptonGoldDetail.view.KryptonGoldDetailAct;
import com.yoursecondworld.secondworld.modular.main.master.presenter.MasterPresenter;
import com.yoursecondworld.secondworld.modular.main.popupWindow.inputKryton.InputKrytonPopupWindow;
import com.yoursecondworld.secondworld.modular.main.popupWindow.inputTime.InputTimePopupWindow;
import com.yoursecondworld.secondworld.modular.selectPostGame.view.SelectPostGameAct;

import java.util.Calendar;

import xiaojinzi.activity.fragment.BaseFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.activity.ActivityUtil;

/**
 * Created by cxj on 2016/10/31.
 * 主界面挂载的Master功能的fragment
 */
public class MasterFragment extends BaseFragment implements IMasterFragmentView {

    public static final int REQUEST_GAME_NAME_CODE = 122;

    @Injection(R.id.tv_date)
    private TextView tv_date;

    @Injection(value = R.id.iv_add_money, click = "clickView")
    private ImageView iv_add_money;

    @Injection(value = R.id.iv_add_time, click = "clickView")
    private ImageView iv_add_time;

    @Injection(value = R.id.tv_budget_setting, click = "clickView")
    private TextView tv_budget_setting;

    @Injection(value = R.id.view_left, click = "clickView")
    private View view_left;

    @Injection(value = R.id.view_right, click = "clickView")
    private View view_right;

    @Injection(value = R.id.tv_krypton_detail, click = "clickView")
    private TextView tv_krypton_detail;

    @Injection(value = R.id.tv_time_detail, click = "clickView")
    private TextView tv_time_detail;

    @Injection(R.id.wldv_money)
    private WaveLoadingView wldv_money;

    @Injection(R.id.wldv_time)
    private WaveLoadingView wldv_time;

    @Injection(R.id.tv_money)
    private TextView tv_money;

    @Injection(R.id.tv_money_left)
    private TextView tv_money_left;

    @Injection(R.id.tv_time)
    private TextView tv_time;

    @Injection(R.id.tv_time_left)
    private TextView tv_time_left;

    @Injection(R.id.sfl)
    private SwipeRefreshLayout sfl;

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            masterPresenter.getMasterInfoByMonth(year, month);
        }
    };

    private MasterPresenter masterPresenter = new MasterPresenter(this);

    /**
     * 当前的年份
     */
    private int year;

    /**
     * 当前的月份
     */
    private int month;

    @Override
    public int getLayoutId() {
        return R.layout.frag_master_for_main;
    }

    @Override
    protected void initView() {
        super.initView();

        //初始化年份和月份
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        //设置显示
        tv_date.setText(year + "-" + month);

    }

    @Override
    protected void setOnListener() {
        super.setOnListener();
        sfl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                h.sendEmptyMessageDelayed(0, 400);
            }
        });
    }

    private InputKrytonPopupWindow inputKrytonPopupWindow;
    private InputTimePopupWindow inputTimePopupWindow;

    /**
     * 1表示inputKrytonPopupWindow
     * 2表示inputTimePopupWindow
     */
    private int isPopupMoneyOrTime;

    private boolean isToGetGameName = false;

    /**
     * 点击事件集中处理
     *
     * @param v
     */
    public void clickView(View v) {
        int id = v.getId();

        Intent intent = null;

        switch (id) {

            case R.id.view_left:

                month--;

                if (month < 1) { //边缘判断
                    month = 12;
                    year--;
                }

                tv_date.setText(year + "-" + month);

                loadData();

                break;

            case R.id.view_right:

                month++;

                if (month > 12) { //边缘判断
                    month = 1;
                    year++;
                }

                int y = Calendar.getInstance().get(Calendar.YEAR);
                int m = Calendar.getInstance().get(Calendar.MONTH) + 1;

                if (year == y && month > m) {
                    month--;
                    tip("下个月还没到呢");
                    return;
                }

                tv_date.setText(year + "-" + month);

                loadData();

                break;

            case R.id.iv_add_money:

                inputKrytonPopupWindow = new InputKrytonPopupWindow(context);
//                inputKrytonPopupWindow.setHeight(ScreenUtils.getScreenHeight(context));
                inputKrytonPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

                inputKrytonPopupWindow.setListenerGameNameClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, SelectPostGameAct.class);
                        intent.putExtra(SelectPostGameAct.IS_RETURN_FLAG, true);
                        getActivity().startActivityForResult(intent, REQUEST_GAME_NAME_CODE);
                        isToGetGameName = true;
                    }
                });

                isPopupMoneyOrTime = 1;

                break;

            case R.id.iv_add_time:

                inputTimePopupWindow = new InputTimePopupWindow(context);

                inputTimePopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

                inputTimePopupWindow.setListenerGameNameClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, SelectPostGameAct.class);
                        intent.putExtra(SelectPostGameAct.IS_RETURN_FLAG, true);
                        getActivity().startActivityForResult(intent, REQUEST_GAME_NAME_CODE);
                        isToGetGameName = true;
                    }
                });

                inputTimePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        loadData();
                    }
                });

                isPopupMoneyOrTime = 2;

                break;
            case R.id.tv_krypton_detail:

                intent = new Intent(context, KryptonGoldDetailAct.class);
                intent.putExtra(KryptonGoldDetailAct.YEAR_FLAG, year);
                intent.putExtra(KryptonGoldDetailAct.MONTH_FLAG, month);

                startActivity(intent);

                break;

            case R.id.tv_time_detail:

                intent = new Intent(context, GameTimeDetailAct.class);
                intent.putExtra(GameTimeDetailAct.YEAR_FLAG, year);
                intent.putExtra(GameTimeDetailAct.MONTH_FLAG, month);

                startActivity(intent);

                break;

            case R.id.tv_budget_setting:

                ActivityUtil.startActivity(context, MasterBudgetAct.class);

                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isToGetGameName) {
            isToGetGameName = false;
            return;
        }
        loadData();
    }

    private void loadData() {
        if (sfl.isRefreshing()) {
            return;
        }
        showDialog("请稍后");
        h.sendEmptyMessageDelayed(0, 400);
    }

    @Override
    public void showDialog(String content) {
        sfl.setRefreshing(true);
    }

    @Override
    public void closeDialog() {
        sfl.setRefreshing(false);
    }

    /**
     * 设置游戏名称
     *
     * @param gameName 游戏名称
     */
    public void setGameName(String gameName) {
        if (isPopupMoneyOrTime == 1) {
            inputKrytonPopupWindow.setGameName(gameName);
        } else if (isPopupMoneyOrTime == 2) {
            inputTimePopupWindow.setGameName(gameName);
        }
    }

    @Override
    public void onLoadMasterInfoSuccess(MasterInfo masterInfo) {

        tv_money.setText(masterInfo.getCost_money() + "");
        tv_money_left.setText(masterInfo.getMoney_left() + "");
        tv_time.setText(masterInfo.getCost_time() * 100 / 60 / 100f + "");
        tv_time_left.setText(masterInfo.getTime_left() * 100 / 60 / 100f + "");

        int moneyPercent = (int) (((Number) masterInfo.getMoney_left()).floatValue() * 100 / masterInfo.getMoney());
        int timePercent = (int) (((Number) masterInfo.getTime_left()).floatValue() * 100 / masterInfo.getTime());

        //显示百分比到球上面
        if (moneyPercent <= 0) {
            moneyPercent = 0;
        }

        if (moneyPercent >= 100) {
            moneyPercent = 100;
        }

//        wldv_money.setmWaterLevelRatio(0);
        wldv_money.setProgressValue(moneyPercent);

//        wldv_money.setCenterTitle(moneyPercent + "%");
        if (timePercent <= 0) {
            timePercent = 0;
        }
        if (timePercent >= 100) {
            timePercent = 100;
        }

//        wldv_time.setmWaterLevelRatio(0);
        wldv_time.setProgressValue(timePercent);


    }

    @Override
    public void onSessionInvalid() {
    }

}
