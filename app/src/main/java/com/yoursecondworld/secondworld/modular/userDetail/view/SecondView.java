package com.yoursecondworld.secondworld.modular.userDetail.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;

/**
 * Created by cxj on 2016/9/5.
 * 用户详情的第二个头部试图
 */
public class SecondView {

    private View secondHeaderView;

    /**
     * 第二个头布局的昵称控件
     */
    private TextView tv_nickName = null;

    /**
     * 第二个头布局描述
     */
    private TextView tv_secondDesc = null;

    /**
     * 提示信息
     */
    private TextView tv_person_info_tip = null;

    private RelativeLayout rl_person_info;

    /**
     * 编辑的图标
     */
    private ImageView iv_edtiUserInfo = null;

    private RecyclerView rv_games = null;

    private Context context;

    /**
     * @param context  上下文
     * @param isMyInfo 是否是自己的用户详情
     */
    public SecondView(Context context, boolean isMyInfo) {

        this.context = context;

        //创建视图
        secondHeaderView = View.inflate(context, R.layout.act_user_detail_second_header, null);

        tv_person_info_tip = (TextView) secondHeaderView.findViewById(R.id.tv_person_info_tip);

        if (isMyInfo) {
            rl_person_info = (RelativeLayout) secondHeaderView.findViewById(R.id.rl_person_info);
        }

        //找到编辑的图标
        iv_edtiUserInfo = (ImageView) secondHeaderView.findViewById(R.id.iv_edit_info);

        //找到昵称控件
        tv_nickName = (TextView) secondHeaderView.findViewById(R.id.tv_act_user_detail_viewpager_view_info_item_nickname_name);
        //找到描述的控件
        tv_secondDesc = (TextView) secondHeaderView.findViewById(R.id.tv_act_user_detail_viewpager_view_info_item_dsec_content);

        rv_games = (RecyclerView) secondHeaderView.findViewById(R.id.rv_games);

        if (isMyInfo) {
            iv_edtiUserInfo.setVisibility(View.VISIBLE);
            tv_person_info_tip.setText("你的个人资料");
        } else {
            iv_edtiUserInfo.setVisibility(View.GONE);
            tv_person_info_tip.setText("他的个人资料");
        }

    }

    /**
     * 显示用户信息
     *
     * @param user
     */
    public void disPlayUserInfo(NewUser user) {
        tv_nickName.setText(user.getUser_nickname());
        //设置描述信息
        String description = user.getUser_introduction();
        description = TextUtils.isEmpty(description) ? "主人很懒,啥都没有留下~~~" : description;
        tv_secondDesc.setText(description);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rv_games.setLayoutManager(layoutManager);

        rv_games.addItemDecoration(new RecyclerView.ItemDecoration() {

            private int hSpace = AutoUtils.getPercentWidthSize(24);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = hSpace;
            }
        });

        rv_games.setAdapter(new CommonRecyclerViewAdapter<String>(context,user.getGames()){
            @Override
            public void convert(CommonRecyclerViewHolder h, String entity, int position) {
                h.setText(R.id.tv_labelname, entity);
            }

            @Override
            public int getLayoutViewId(int viewType) {
                return R.layout.act_user_detail_info_label_item;
            }
        });

    }

    /**
     * 返回试图
     *
     * @return
     */
    public View getSecondHeaderView() {
        return secondHeaderView;
    }

    /**
     * 设置编辑界面的点击事件
     *
     * @param listener
     */
    public void setOnEditIconListener(View.OnClickListener listener) {
        if (rl_person_info != null) {
            rl_person_info.setOnClickListener(listener);
        }
    }


}
