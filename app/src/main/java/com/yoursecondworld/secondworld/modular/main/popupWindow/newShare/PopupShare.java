package com.yoursecondworld.secondworld.modular.main.popupWindow.newShare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ShareEventEntity;
import com.yoursecondworld.secondworld.modular.main.eventEntity.SharePlatformEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.share.ShareAdapter;
import com.yoursecondworld.secondworld.modular.main.popupWindow.share.ShareEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.share.SharePopupWindowSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.share.ShareUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.wechat.friends.Wechat;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/24.
 * 弹出分享的界面,这个用android自带的轻量级组件弹出
 */
public class PopupShare extends BottomSheetDialog {

    /**
     * 上下文
     */
    private Context context;

    private RecyclerView rv = null;

    private List<ShareEntity> data = new ArrayList<ShareEntity>();

    private CommonRecyclerViewAdapter adapter = null;

    private TextView tv_cancel = null;

    private ShareUtil.ShareBean sb = new ShareUtil.ShareBean("来自GM的分享", "游戏的掌控者", "http://avatar.csdn.net/4/2/9/1_u011692041.jpg", "http://blog.csdn.net/u011692041");

    /**
     * 从哪里点击的分享
     * {@link SharePlatformEntity#FROM_HOME}
     * {@link SharePlatformEntity#FROM_FIND}
     * {@link SharePlatformEntity#FROM_COLLECT}
     * {@link SharePlatformEntity#FROM_USERDETAIL}
     */
    public String from;

    /**
     * 弹出的视图
     */
    private View contentView;

    /**
     * 创建弹出的界面
     *
     * @param context
     */
    public PopupShare(@NonNull Context context) {
        super(context);
        this.context = context;
        contentView = View.inflate(context, R.layout.act_main_popup_for_share, null);

        data.add(new ShareEntity(R.mipmap.logo_of_weixin1, "微信分享"));
        data.add(new ShareEntity(R.mipmap.logo_of_weibo1, "微博分享"));
        data.add(new ShareEntity(R.mipmap.logo_of_qq1, "QQ分享"));
//        data.add(new ShareEntity(R.mipmap.logo_of_qq, "复制链接"));

        initView(contentView);

        setContentView(contentView);

        setOnListener();
    }

    /**
     * 初始化控件
     *
     * @param contentView
     */
    private void initView(View contentView) {

        tv_cancel = (TextView) contentView.findViewById(R.id.tv_act_main_popup_for_share_cancel);

        rv = (RecyclerView) contentView.findViewById(R.id.rv_act_main_popup_for_share);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);

        adapter = new ShareAdapter(context, data);

        SharePopupWindowSpaceItemDecoration itemDecoration = new SharePopupWindowSpaceItemDecoration();
        rv.addItemDecoration(itemDecoration);

        rv.setAdapter(adapter);

        //设置分享平台的选择的监听
        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                switch (position) {
                    case 0:
                        ShareUtil.shareToWeiXin(sb);
                        break;
                    case 1:

                        ShareUtil.shareToWeiBo(sb, new PlatformActionListener() {
                            @Override
                            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                                T.showShort(context, "分享成功");
                            }

                            @Override
                            public void onError(Platform platform, int i, Throwable throwable) {
                                T.showShort(context, "分享失败");
                            }

                            @Override
                            public void onCancel(Platform platform, int i) {
                                T.showShort(context, "取消分享");
                            }
                        });
                        break;
                    case 2:

                        ShareUtil.shareToQQ(sb);
                        break;
                }
                dismiss();
            }
        });

    }

    /**
     * 设置条目的监听
     */
    private void setOnListener() {

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    /**
     * 获取视图
     *
     * @return
     */
    public View getContentView() {
        return contentView;
    }

    public ShareUtil.ShareBean getSb() {
        return sb;
    }
}
