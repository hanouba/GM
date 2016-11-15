package com.yoursecondworld.secondworld.modular.im.selectContacts.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/7/21.
 * 显示IM联系人列表的适配器
 */
public class SelectContactsActAdapter extends CommonRecyclerViewAdapter<NewUser> {

    /**
     * 记录每个条目是否被选中,选中是一个效果,没有选中时另一种效果
     */
    private ArrayList<Boolean> isSelectedContacts;

    /**
     * 构造函数
     *
     * @param context            上下文
     * @param data               显示的数据
     * @param isSelectedContacts 记录每个条目是否被选中,选中是一个效果,没有选中时另一种效果
     */
    public SelectContactsActAdapter(Context context, List<NewUser> data, ArrayList<Boolean> isSelectedContacts) {
        super(context, data);
        this.isSelectedContacts = isSelectedContacts;
    }

    @Override
    public void convert(CommonRecyclerViewHolder h, NewUser entity, int position) {
        int itemType = getItemType(position);
        switch (itemType) {
            case 1: //如果是头布局
                h.setText(R.id.tv_act_select_contacts_tag_item_name, entity.getUser_nickname());
                break;
            case 2:
                //设置昵称
                h.setText(R.id.tv_act_select_contacts_item_name, entity.getUser_nickname());
                //拿到头像控件
                SimpleDraweeView icon = h.getView(R.id.sdv_act_select_contacts_item_icon);
                //设置头像
                icon.setImageURI(Uri.parse(entity.getUser_head_image()));
                //判断是不是选中了
                boolean isSelect = isSelectedContacts.get(position);
                if (isSelect) {
                    h.setImage(R.id.iv_act_select_contacts_item_radiobutton, R.mipmap.radiobutton_selected1);
                } else {
                    h.setImage(R.id.iv_act_select_contacts_item_radiobutton, R.mipmap.radiobutton_unselected1);
                }

                RelativeLayout rl_item = h.getView(R.id.rl_item);

                if (getItemType(position - 1) == 1 && (position == data.size() - 1 || getItemType(position + 1) == 1)) {
                    rl_item.setBackgroundResource(R.drawable.select_contact_item_top_and_bottom);
                } else if (position == data.size() - 1 || getItemType(position + 1) == 1) { //如果是一个字母下面最后一个
                    rl_item.setBackgroundResource(R.drawable.select_contact_item_bottom);
                } else if (getItemType(position - 1) == 1) { //如果是头item下面的第一个
                    rl_item.setBackgroundResource(R.drawable.select_contact_item_top);
                } else {
                    rl_item.setBackgroundResource(R.color.white);
                }

                break;
        }
    }

    @Override
    public int getLayoutViewId(int viewType) {
        switch (viewType) {
            case 1:
                return R.layout.act_select_contacts_tag_item;
            default:
                return R.layout.act_select_contacts_item;
        }
    }

    @Override
    public int getItemType(int position) {
        NewUser user = data.get(position);
        if (user.getUser_id() != null) { //表示这个是正常的item
            return 2;
        } else { //表示这个是item头
            return 1;
        }
    }
}
