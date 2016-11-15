package com.yoursecondworld.secondworld.commonBean;

import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.gameTimeDetail.bean.GameTimeDetai;
import com.yoursecondworld.secondworld.modular.kryptonGoldDetail.bean.KryptonGoldDetai;

import java.util.List;

/**
 * Created by cxj on 2016/11/8.
 */
public class MasterInfo extends BaseEntity {

    /**
     * time : 100
     * time_update : 0
     * money_left : 1000
     * money_update : 0
     * cost_money : 0
     * money : 1000
     * status : 0
     * money_cost_list : {}
     * time_left : 100
     * time_cost_list : {}
     * cost_time : 0
     */

    private int time;
    private int time_update;
    private int money_left;
    private int money_update;
    private int cost_money;
    private int money;
    private int time_left;
    private int cost_time;

    private List<KryptonGoldDetai> money_cost_list;

    private List<GameTimeDetai> time_cost_list;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime_update() {
        return time_update;
    }

    public void setTime_update(int time_update) {
        this.time_update = time_update;
    }

    public int getMoney_left() {
        return money_left;
    }

    public void setMoney_left(int money_left) {
        this.money_left = money_left;
    }

    public int getMoney_update() {
        return money_update;
    }

    public void setMoney_update(int money_update) {
        this.money_update = money_update;
    }

    public int getCost_money() {
        return cost_money;
    }

    public void setCost_money(int cost_money) {
        this.cost_money = cost_money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getTime_left() {
        return time_left;
    }

    public void setTime_left(int time_left) {
        this.time_left = time_left;
    }

    public int getCost_time() {
        return cost_time;
    }

    public void setCost_time(int cost_time) {
        this.cost_time = cost_time;
    }

    public List<KryptonGoldDetai> getMoney_cost_list() {
        return money_cost_list;
    }

    public void setMoney_cost_list(List<KryptonGoldDetai> money_cost_list) {
        this.money_cost_list = money_cost_list;
    }

    public List<GameTimeDetai> getTime_cost_list() {
        return time_cost_list;
    }

    public void setTime_cost_list(List<GameTimeDetai> time_cost_list) {
        this.time_cost_list = time_cost_list;
    }
}
