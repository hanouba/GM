package com.yoursecondworld.secondworld.modular.kryptonGoldDetail.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hp.hpl.sparta.Text;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.commonBean.MasterInfo;

/**
 * Created by cxj on 2016/10/31.
 */
public class KryptonGoldDetailHeader {

    private TextView tv_money;

    private TextView tv_surplus;

    private int month;

    public View init(Context mContext, int month) {

        this.month = month;

        View contentView = View.inflate(mContext, R.layout.act_krypton_gold_detail_header, null);

        ((TextView) contentView.findViewById(R.id.tv1)).setText("你" + month + "月氪金");

        tv_money = (TextView) contentView.findViewById(R.id.tv_money);
        tv_surplus = (TextView) contentView.findViewById(R.id.tv_surplus);

        return contentView;

    }

    public void disPlay(MasterInfo masterInfo) {

        tv_money.setText(masterInfo.getCost_money() + "");
        tv_surplus.setText("剩余/$：" + masterInfo.getMoney_left());

    }

}
