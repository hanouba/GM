package com.yoursecondworld.secondworld.modular.messageNotify.fragment.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.im.ConversationAct;
import com.yoursecondworld.secondworld.modular.im.selectContacts.view.SelectContactsAct;
import com.yoursecondworld.secondworld.modular.messageNotify.fragment.entity.MessageEntity;
import com.yoursecondworld.secondworld.modular.messageNotify.fragment.presenter.MessageFragmentPresenter;
import com.yoursecondworld.secondworld.modular.statusNotification.ui.StatusNotificationAct;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;
import xiaojinzi.activity.fragment.BaseFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.activity.ActivityUtil;

/**
 * Created by cxj on 2016/7/16.
 */
public class MessageFragment extends BaseFragment implements IMessageFragmentView {

    @Injection(value = R.id.iv_back, click = "clickView")
    private ImageView iv_back = null;

    @Injection(value = R.id.tv_frag_message_list, click = "clickView")
    private TextView tv_messageList = null;

    @Injection(value = R.id.rl_notifi_message, click = "clickView")
    private RelativeLayout rl_notifi_message;

    @Injection(R.id.tv_tip)
    private TextView tv_tip = null;

    @Injection(R.id.tv_new_number)
    private TextView tv_number = null;

    @Override
    public int getLayoutId() {
        return R.layout.frag_message_for_main;
    }

    private MessageFragmentPresenter presenter = new MessageFragmentPresenter(this);

    @Override
    protected void initView() {
        super.initView();

        int a = AutoUtils.getPercentHeightSize(80);

        GradientDrawable d = (GradientDrawable) getResources().getDrawable(R.drawable.common_bt_bg);
        d.setCornerRadius(a);

        tv_number.setBackground(d);
        tv_number.setText("" + 0);
        tv_number.setVisibility(View.INVISIBLE);

        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.frag__frag_message_conversationlist);

        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                .build();
        fragment.setUri(uri);

    }

    /**
     * 点击事件的集中处理
     *
     * @param view
     */
    public void clickView(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.tv_frag_message_list:
                ActivityUtil.startActivity(context, SelectContactsAct.class);
                break;

            case R.id.rl_notifi_message:
                ActivityUtil.startActivity(context, StatusNotificationAct.class);
                break;
        }

    }

    @Override
    protected void initData() {
        super.initData();
//        ImUtil.connect(context);
    }

    @Override
    protected void setOnListener() {
        super.setOnListener();

        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
                ConversationAct.targetUserId = uiConversation.getConversationTargetId();
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.mes_new_message_exist();
    }

    @Override
    public void onloadNewMessageSuccess(MessageEntity messageEntity) {
        //tv_number
        if (messageEntity != null) {
            if (messageEntity.getLast_event() != null) {
                if (messageEntity.getLast_event().getEvent_type() == 2) {
                    tv_number.setVisibility(View.INVISIBLE);
                } else {
                    if (messageEntity.getNumber() > 0) {
                        tv_number.setVisibility(View.VISIBLE);
                        if (messageEntity.getNumber() > 99) {
                            tv_number.setText("99+");
                        } else {
                            tv_number.setText("" + messageEntity.getNumber());
                        }
                    } else {
                        tv_number.setVisibility(View.INVISIBLE);
                    }
                    if (messageEntity.getLast_event().getEvent_type() == 1) {
                        tv_tip.setText(messageEntity.getLast_event().getUser_nickname() + "赞了你的动态");
                    } else {
                        tv_tip.setText(messageEntity.getLast_event().getContent());
                    }
                }
            }
        }
    }

    @Override
    public void onSessionInvalid() {

    }
}
