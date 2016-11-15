package com.yoursecondworld.secondworld.modular.im.selectContacts.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.PinyinUtil;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.commonFunction.fansAndFollow.presenter.FansAndFollowPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.fansAndFollow.view.IFansAndFollowView;
import com.yoursecondworld.secondworld.modular.im.selectContacts.adapter.SelectContactsActAdapter;
import com.yoursecondworld.secondworld.modular.im.selectContacts.presenter.SelectContactsPresenter;
import com.yoursecondworld.secondworld.modular.im.selectContacts.util.ContactsSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.User;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;
import xiaojinzi.view.IndexableListView;

/**
 * 选择联系人的界面
 */
@Injection(R.layout.act_select_contacts)
public class SelectContactsAct extends BaseAct implements ISelectContactsView, IFansAndFollowView {

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            contactsEntities.addAll(users);

            isSelectedContacts.clear();
            int size = contactsEntities.size();
            for (int i = 0; i < size; i++) {
                isSelectedContacts.add(false);
            }

            adapter.notifyItemRangeInserted(0, users.size());

            users = null;

        }
    };

    /**
     * 标题栏的容器
     */
    @Injection(R.id.rl_act_titlebar_container)
    private RelativeLayout rl_titlebarContainer = null;

    /**
     * 标题栏
     */
    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

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

    @Injection(value = R.id.tv_menu)
    private TextView tv_menu = null;

    //=============================================================================

    @Injection(R.id.rv_act_select_contacts_content)
    private RecyclerView rv = null;

    @Injection(R.id.letter)
    private IndexableListView indexableListView;

    @Injection(value = R.id.bt_chat, click = "clickView")
    private Button bt_chat;

    /**
     * 要展示的数据
     */
    private ArrayList<NewUser> contactsEntities = new ArrayList<NewUser>();

    /**
     * 记录每个条目是否被选中,选中是一个效果,没有选中时另一种效果
     * 在进行搜索的时候应该这里面存的是所有item的选中情况,所以过
     * 滤掉一些数据之后,还是能够支持的,不需要调整大小和过滤后的
     * 集合一样的长度,唯一需要注意的就是过滤之前先把原先选中的
     * 还原
     */
    private ArrayList<Boolean> isSelectedContacts = new ArrayList<Boolean>();

    /**
     * 展示数据的适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    /**
     * 布局管理器
     */
    private LinearLayoutManager layoutManager;

    private SelectContactsPresenter presenter = new SelectContactsPresenter(this);

    private FansAndFollowPresenter fansAndFollowPresenter = new FansAndFollowPresenter(this);

    @Override
    public void initView() {
        super.initView();

        initBase();

        initContacts();

    }

    @Override
    public void initData() {
        super.initData();

        //获取互相关注的用户,也就是好友
        fansAndFollowPresenter.getFollowEachOther();

    }

    /**
     * 记录用户的选择的联系人下标
     */
    private int oldSelectPosition = -1;

    @Override
    public void setOnlistener() {
        super.setOnlistener();
        //设置联系人条目的监听
        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position == oldSelectPosition) {
                    return;
                }
                //曲线原先选中的
                cancelSelect(false);

                isSelectedContacts.set(position, true);
                oldSelectPosition = position;

//                adapter.notifyItemChanged(position);
                adapter.notifyDataSetChanged();

            }
        }, 2);

        //设置字母列表的监听
        indexableListView.setOnLetterChange(new IndexableListView.OnLetterChange() {
            @Override
            public void letterChange(String c) {

                //占到对应的字母,然后滚动列表到那个字母的地方
                for (int i = 0; i < contactsEntities.size(); i++) {
                    NewUser user = contactsEntities.get(i);
                    if (user.getUser_id() == null) {
                        if (user.getUser_nickname().equals(c)) {
                            layoutManager.scrollToPositionWithOffset(i, 0);
                            return;
                        }
                    }
                }

            }
        });

    }

    /**
     * 取消用户的选中情况
     *
     * @param isNotify 是否通知适配器
     */
    private void cancelSelect(boolean isNotify) {
        if (oldSelectPosition != -1) {
            isSelectedContacts.set(oldSelectPosition, false);
            adapter.notifyItemChanged(oldSelectPosition);
            oldSelectPosition = -1;
        }
    }

    /**
     * 初始化联系人
     */
    private void initContacts() {

        //创建布局管理器
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);

        //创建适配器
        adapter = new SelectContactsActAdapter(context, contactsEntities, isSelectedContacts);

        //设置适配器
        rv.setAdapter(adapter);

    }

    /**
     * 初始化基本的信息
     */
    private void initBase() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("我的好友");
        tv_menu.setText("聊天");

        bt_chat.setAlpha(0.8f);
    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        //获取控件的id
        int id = v.getId();

        //对id的筛选然后做处理
        switch (id) {
            case R.id.iv_back: //如果是返回按钮
                finish();
                break;
            case R.id.bt_chat: //如果是开始聊天的按钮
                if (oldSelectPosition > -1 && oldSelectPosition < contactsEntities.size()) {
                    NewUser user = contactsEntities.get(oldSelectPosition);
                    if (user.getUser_id() != null) {
                        RongIM rongIM = RongIM.getInstance();
                        RongIMClient.ConnectionStatusListener.ConnectionStatus currentConnectionStatus = rongIM.getCurrentConnectionStatus();
                        if (currentConnectionStatus == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) { //如果是已经连接的
                            if (user != null) {
                                rongIM.startPrivateChat(context, user.getUser_id() + "", user.getUser_nickname());
                            }
                        } else {
                            T.showShort(context, "未连接");
                        }
                    }
                } else {
                    T.showShort(context, "您还未选择联系人");
                }
                break;
        }
    }


    @Override
    public void onLoadSuccess(List<User> users) {

    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onLoadFollowEachOtherSuccess(List<NewUser> newUsers) {
        users = newUsers;
        tv_titleName.setText("我的好友(" + newUsers.size() + ")");
        sortData();
    }

    @Override
    public void onLoadUnFollowFansSuccess(List<NewUser> newUsers) {
    }

    @Override
    public void onLoadMoreUnFollowFansSuccess(List<NewUser> users) {
    }

    @Override
    public void savePass(String pass) {
    }

    @Override
    public String getPass() {
        return null;
    }

    /**
     * 待整理,整理好后才调用ISelectContactsView中的显示方法
     */
    private List<NewUser> users;

    /**
     * 整理数据
     */
    private void sortData() {

        new Thread() {
            @Override
            public void run() {

                PinyinUtil pinyinUtil = PinyinUtil.getInstance();

                //表示#-A-Z的字母在
                ArrayList<NewUser>[] ziMuIndex = new ArrayList[27];

                int size = users.size();

                int index = -1;

                //这个循环结束了之后,用户已经按照名字分别存放到对用的集合中了
                for (int i = 0; i < size; i++) {
                    NewUser user = users.get(i);
                    //拿到用户名
                    String name = user.getUser_nickname();
                    char zimu = '#';
                    if (!TextUtils.isEmpty(name)) {
                        //获取用户名的首字母,可能是"#",可能是A-Z
                        zimu = pinyinUtil.getCharInitial(name.charAt(0));
                    }

                    if (zimu == '#') { //如果解析不出来,返回了#号,那么放到列表的最后
                        index = 0;
                    } else {
                        index = zimu - 65 + 1;
                    }
                    if (ziMuIndex[index] == null) {
                        ziMuIndex[index] = new ArrayList<NewUser>();
                    }
                    ziMuIndex[index].add(user);
                }

                users.clear();

                //存放进去整理好的数据
                for (int i = 0; i < ziMuIndex.length; i++) {
                    ArrayList<NewUser> userArrayList = ziMuIndex[i];
                    if (userArrayList != null) { //比如0号位置集合不为null,说明有数据,那么先添加头
                        NewUser u = new NewUser();
                        if (i == 0) { //如果是'#'
                            u.setUser_nickname("#");
                        } else {
                            u.setUser_nickname(((char) (i - 1 + 65)) + "");
                        }
                        users.add(u);
                        users.addAll(userArrayList);
                    }
                    //释放资源
                    userArrayList = null;
                }

                ziMuIndex = null;

                h.sendEmptyMessage(0);
            }
        }.start();

    }

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    /**
     * 当sessionId失效了
     */
    @Subscribe
    public void onSessionIdInvalid(SessionInvalidEvent event) {
        finish();
    }

}
