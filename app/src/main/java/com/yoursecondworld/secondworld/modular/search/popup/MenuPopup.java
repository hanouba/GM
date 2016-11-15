package com.yoursecondworld.secondworld.modular.search.popup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;

/**
 * Created by cxj on 2016/10/15.
 */
public class MenuPopup extends PopupWindow {

    private View contentView;

    private TextView tv_tab_text1;
    private TextView tv_tab_text2;

    public MenuPopup(Context context) {
        super(context);
        contentView = View.inflate(context, R.layout.act_search_menu, null);
        tv_tab_text1 = (TextView) contentView.findViewById(R.id.tv_tab_text1);
        tv_tab_text2 = (TextView) contentView.findViewById(R.id.tv_tab_text2);
        setContentView(contentView);
        initPopupWindow();
    }

    /**
     * 初始化popupWindow本身
     */
    private void initPopupWindow() {
        setContentView(contentView);
        setFocusable(true);
        setBackgroundDrawable(null);
    }

    public void setOnItemClickListener(View.OnClickListener clickListener) {
        tv_tab_text1.setOnClickListener(clickListener);
        tv_tab_text2.setOnClickListener(clickListener);
    }

}
