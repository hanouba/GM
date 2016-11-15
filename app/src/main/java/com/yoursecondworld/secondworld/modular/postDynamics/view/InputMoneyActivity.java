package com.yoursecondworld.secondworld.modular.postDynamics.view;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;

import xiaojinzi.annotation.Injection;

@Injection(R.layout.act_input_money)
public class InputMoneyActivity extends BaseAct {

    @Injection(R.id.et_content)
    private EditText et_content;

    public static final int RESPONSECODE = 2345;

    public static final String MONEY_FLAG = "money";


    @Override
    public boolean isRegisterEvent() {
        return false;
    }

    public void confirm(View view) {
        Intent i = new Intent();
        i.putExtra(MONEY_FLAG, et_content.getText().toString());
        setResult(RESPONSECODE, i);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

}
