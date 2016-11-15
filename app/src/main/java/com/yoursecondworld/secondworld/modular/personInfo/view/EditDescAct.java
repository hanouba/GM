package com.yoursecondworld.secondworld.modular.personInfo.view;


import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.modular.personInfo.presenter.EditPersonInfoPresenter;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.view.TextWatcherAdapter;

@Injection(R.layout.act_edit_desc)
public class EditDescAct extends BaseAct implements IEditPersonInfoView {

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

    @Injection(R.id.et_desc)
    private EditText et_desc;

    @Injection(R.id.tv_desc_length_tip)
    private TextView tv_desc_length_tip;

    @Injection(value = R.id.bt_complete, click = "complete")
    private Button bt_complete;

    private EditPersonInfoPresenter presenter = new EditPersonInfoPresenter(this);

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {
        finish();
    }

    @Override
    public void initView() {
        super.initView();
        tv_titleName.setText("");
    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        //添加输入描述的监听
        et_desc.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                int length = s.toString().trim().length();
                if (length > 38) {
                    et_desc.setText(s.toString().trim().substring(0, 38));
                    et_desc.setSelection(38);
                    tv_desc_length_tip.setText("38/38");
                } else {
                    tv_desc_length_tip.setText(length + "/38");
                }
            }
        });

    }

    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    @Override
    public String getImagepath() {
        return null;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getNickName() {
        return null;
    }

    @Override
    public String getDesc() {
        return et_desc.getText().toString().trim();
    }

    @Override
    public void finishActivity() {
    }

    @Override
    public void onUpdateNickNameSuccess() {
    }

    @Override
    public void onUpdateDescSuccess() {
        Intent intent = new Intent();
        intent.putExtra("data", getDesc());
        setResult(RESULT_OK, intent);
        finish();
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

    /**
     * 完成按钮所调用的
     *
     * @param view
     */
    public void complete(View view) {
        presenter.updateDesc();
    }

}
