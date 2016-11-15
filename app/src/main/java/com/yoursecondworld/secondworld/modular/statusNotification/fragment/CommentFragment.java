package com.yoursecondworld.secondworld.modular.statusNotification.fragment;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.DynamicsDetailAct;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.CommentsListEntity;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.ZanListEntity;
import com.yoursecondworld.secondworld.modular.statusNotification.fragment.adapter.CommentFragmentAdapter;
import com.yoursecondworld.secondworld.modular.statusNotification.fragment.itemDecoration.ZanItemDecoration;
import com.yoursecondworld.secondworld.modular.statusNotification.presenter.StatusNotificationPresenter;
import com.yoursecondworld.secondworld.modular.statusNotification.sheetDialog.PopupReply;
import com.yoursecondworld.secondworld.modular.statusNotification.ui.IStatusNotificationView;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.activity.fragment.BaseFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;

/**
 * Created by cxj on 2016/7/23.
 * 显示赞的列表的fragment
 */
public class CommentFragment extends BaseFragment implements IStatusNotificationView {

    /**
     * 显示咱的内容的列表控件
     */
    @Injection(R.id.rv)
    private RecyclerView rv = null;

    /**
     * 显示赞的内容的适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    /**
     * 显示的数据
     */
    private List<CommentsListEntity> data = new ArrayList<CommentsListEntity>();

    private StatusNotificationPresenter statusNotificationPresenter = new StatusNotificationPresenter(this);

    @Override
    public int getLayoutId() {
        return R.layout.frag_comment_for_status_notifi;
    }

    @Override
    protected void initView() {
        super.initView();

        //创建适配器
        adapter = new CommentFragmentAdapter(context, data);

        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        ZanItemDecoration itemDecoration = new ZanItemDecoration();
        rv.addItemDecoration(itemDecoration);

        rv.setAdapter(adapter);

    }

    @Override
    protected void initData() {
        super.initData();
        statusNotificationPresenter.getCommentsList();
    }

    private boolean isNoTAnyMore;

    @Override
    protected void setOnListener() {
        super.setOnListener();

        //加载更多的监听
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //如果暂停了

                    boolean b = !ViewCompat.canScrollVertically(rv, 1);
                    //如果已经不可以向下滑动了,就去加载更多
                    if (b) {
                        if (isNoTAnyMore) {
                            return;
                        }
                        statusNotificationPresenter.getMoreCommentsList();
                    }
                }
            }
        });

        adapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
            @Override
            public void onViewInItemClick(View v, int position) {
                int id = v.getId();
                switch (id) {
                    case R.id.tv_reply:
                        CommentsListEntity entity = data.get(position);
                        PopupReply p = new PopupReply(context, entity.getUser_id(), entity.getUser_nickname(), entity.getSource_article_id());
                        p.show();
                        break;
                    case R.id.tv_frag_zan_for_status_notifi_item_text_content:
                        //跳转到动态详情去
                        Intent i = new Intent(context, DynamicsDetailAct.class);

                        //携带被点击的item的动态的id过去
                        i.putExtra(DynamicsDetailAct.DYNAMICS_ID_FLAG, data.get(position).getSource_article_id());

                        context.startActivity(i);
                        break;
                }
            }
        }, R.id.tv_reply, R.id.tv_frag_zan_for_status_notifi_item_text_content);

    }

    @Override
    public void onLoadCommentsListSuccess(List<CommentsListEntity> commentsListEntities) {
        isNoTAnyMore = false;
        AdapterNotify.notifyFreshData(data, commentsListEntities, adapter);
    }

    @Override
    public void onLoadMoreCommentsListSuccess(List<CommentsListEntity> commentsListEntities) {
        if (commentsListEntities.size() == 0) {
            isNoTAnyMore = true;
        }
        AdapterNotify.notifyAppendData(data, commentsListEntities, adapter);
    }

    private String pass;

    @Override
    public void savePass(String pass) {
        this.pass = pass;
    }

    @Override
    public String getPass() {
        return pass;
    }

    @Override
    public void onLoadZanListSuccess(List<ZanListEntity> zanListEntities) {

    }

    @Override
    public void onLoadMoreZanListSuccess(List<ZanListEntity> zanListEntities) {

    }

    @Override
    public void onSessionInvalid() {

    }
}
