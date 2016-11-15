package com.yoursecondworld.secondworld.common.timePicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.yoursecondworld.secondworld.R;

import java.util.Calendar;
import java.util.Date;

/**
 * 作为一个弹窗采集时间用
 * 返回采集的时间戳
 */
public class DatePickerAct extends Activity implements View.OnClickListener {

    /**
     * 返回的Intent里面的数据中的年份的key
     */
    public static String YEAR_FLAG = "year_flag";

    /**
     * 返回的Intent里面的数据中的月份的key
     */
    public static String MONTH_FLAG = "month_flag";

    /**
     * 返回的Intent里面的数据中的每个月中的第几天的key
     */
    public static String DAY_FLAG = "day_flag";

    /**
     * 确认按钮
     */
    private Button bt_confirm = null;

    /**
     * Android自带的时钟拾取器
     */
    private DatePicker datePicker = null;

    /**
     * 年份
     */
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_date_picker);
        //获取控件
        datePicker = (DatePicker) findViewById(R.id.dp_date);
        bt_confirm = (Button) findViewById(R.id.bt_act_time_picker_confirm);

        datePicker.init(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        year = datePicker.getYear();
                        month = datePicker.getMonth();
                        day = datePicker.getDayOfMonth();
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
        bundle.putInt(YEAR_FLAG, year);
        bundle.putInt(MONTH_FLAG, month);
        bundle.putInt(DAY_FLAG, day);
        result.putExtras(bundle);
        setResult(RESULT_OK, result);
        finish();
    }


}
