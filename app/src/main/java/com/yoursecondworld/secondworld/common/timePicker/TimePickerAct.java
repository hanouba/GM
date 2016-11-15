package com.yoursecondworld.secondworld.common.timePicker;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.yoursecondworld.secondworld.R;

import xiaojinzi.base.android.log.L;

/**
 * 作为一个弹窗采集时间用
 * 返回采集的时间戳
 */
public class TimePickerAct extends Activity implements View.OnClickListener {

    /**
     * 拾取器弹窗的返回码
     */
    public static int TIMEPICKERRESULT = 666;

    /**
     * 返回的Intent里面的数据中的小时的key
     */
    public static String HOUR_FLAG = "hour_flag";

    /**
     * 返回的Intent里面的数据中的分钟的key
     */
    public static String MINUTE_FLAG = "minute_flag";

    /**
     * 确认按钮
     */
    private Button bt_confirm = null;

    /**
     * Android自带的时钟拾取器
     */
    private TimePicker timePicker = null;

    /**
     * 小时和分钟
     */
    private int hourOfDay;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_time_picker);
        //获取控件
        timePicker = (TimePicker) findViewById(R.id.tp_act_time_picker);
        bt_confirm = (Button) findViewById(R.id.bt_act_time_picker_confirm);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                TimePickerAct.this.hourOfDay = hourOfDay;
                TimePickerAct.this.minute = minute;
            }
        });

        ///设置按钮的点击事件监听
        bt_confirm.setOnClickListener(this);

    }

    /**
     * 确定的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent result = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(HOUR_FLAG, hourOfDay);
        bundle.putInt(MINUTE_FLAG, minute);
        result.putExtras(bundle);
        setResult(TIMEPICKERRESULT, result);
        finish();
    }


}
