package com.yoursecondworld.secondworld.modular.personInfo.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.modular.personInfo.presenter.EditPersonInfoPresenter;

import xiaojinzi.annotation.Injection;

@Injection(R.layout.act_edit_nick_name)
public class EditNickNameAct extends BaseAct implements IEditPersonInfoView {

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

    //================================================================

    @Injection(R.id.et_nickname)
    private EditText et_nickname;

    @Injection(value = R.id.bt_complete, click = "complete")
    private Button bt_complete;

    private EditPersonInfoPresenter presenter = new EditPersonInfoPresenter(this);

    @Override
    public void initView() {
        super.initView();
        tv_titleName.setText("");
    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {
        finish();
    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    /**
     * 完成按钮所调用的
     *
     * @param view
     */
    public void complete(View view) {
        presenter.updateNickName();
    }

    @Override
    public String getImagepath() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getNickName() {
        return et_nickname.getText().toString().trim();
    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public void finishActivity() {

    }

    @Override
    public void onUpdateNickNameSuccess() {
        Intent intent = new Intent();
        intent.putExtra("data", getNickName());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onUpdateDescSuccess() {

    }

    @Override
    public String getSex() {
        return null;
    }

    @Override
    public void onUpdateSexSuccess() {

    }

    @Override
    public void onUpdateBirthSuccess() {

    }

    @Override
    public String getBirth() {
        return null;
    }

}
