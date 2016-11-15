package com.yoursecondworld.secondworld.modular.dynamicsDetail.view;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.BaseFragmentAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.DateFormat;
import com.yoursecondworld.secondworld.common.EmojiReplaceUtil;
import com.yoursecondworld.secondworld.common.FrescoImageResizeUtil;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.common.imagePreview.ImagePreviewAct;
import com.yoursecondworld.secondworld.modular.commonFunction.postComment.presenter.PostCommentPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.postComment.view.IpostCommentView;
import com.yoursecondworld.secondworld.modular.commonFunction.user.presenter.UserPresenter;
import com.yoursecondworld.secondworld.modular.commonFunction.user.view.IUserView;
import com.yoursecondworld.secondworld.modular.dynamics.adapter.DynamicsContentRecyclerViewAdapter;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.dynamics.presenter.DynamicsPresenter;
import com.yoursecondworld.secondworld.modular.dynamics.view.IDynamicsView;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.adapter.DynamicsDetailActAdapter;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.entity.DynamicsComment;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.presenter.DynamicsDetailPresenter;
import com.yoursecondworld.secondworld.modular.main.eventEntity.ItemMenuEventEntity;
import com.yoursecondworld.secondworld.modular.main.popupWindow.itemMenu.ItemMenu;
import com.yoursecondworld.secondworld.modular.main.popupWindow.newShare.PopupShare;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji.SelectEmojiFragment;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji.event.SelectEmojiEvent;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.view.TextWatcherAdapter;
import xiaojinzi.base.android.os.KeyBoardUtils;
import xiaojinzi.base.android.os.ScreenUtils;
import xiaojinzi.base.android.os.T;
import xiaojinzi.view.CommonNineView;

/**
 * 动态详情的界面
 */
@Injection(R.layout.act_dynamics_detail)
public class DynamicsDetailAct extends BaseFragmentAct implements IDynamicsDetailView, IUserView, IDynamicsView, IpostCommentView {

    /**
     * 跳转到这个界面需要传递的参数
     */
    public static final String DYNAMICS_ID_FLAG = "dynamics_id";


    /**
     * 取关注的字符串
     */
    private String attention = "关注TA";

    /**
     * 取消关注的字符串
     */
    private String cancelAttention = "取消关注";

    /**
     * xml跟布局
     */
    @Injection(value = R.id.rl_root,click = "clickView")
    private RelativeLayout rl_root = null;

    /**
     * xml中的内容
     */
    @Injection(R.id.rl_content)
    private RelativeLayout rl_content = null;

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
     * 标题栏中间的字
     */
    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    /**
     * 标题栏右边的小菜单
     */
    @Injection(value = R.id.tv_menu, click = "clickView")
    private TextView tv_menu = null;

    /**
     * 屏幕高度
     */
    private int screenHeight;

    /**
     * 状态栏的高度
     */
    private int stateHeight;

    /**
     * 标题栏高度
     */
    private int titlebarHeight;

    //=======================================================================================

    /**
     * 整个页面是一个列表,头部是动态内容,主要的是为了下面的评论显示
     */
    @Injection(R.id.rv_act_dynamics_detail_content)
    private RecyclerView rv_contetn = null;

    /**
     * 评论的内容
     */
    @Injection(R.id.et_content)
    private EditText et_content = null;

    @Injection(value = R.id.iv_foot_emoji, click = "clickView")
    private ImageView iv_emoji = null;

    @Injection(R.id.fl)
    private FrameLayout fl = null;

    /**
     * 适配器,针对头部不起作用,头部也不计算在其中
     */
    private CommonRecyclerViewAdapter adapter_content = null;

    /**
     * 内容的数据集合
     */
    private List<DynamicsComment> dynamicsCommentList = new ArrayList<DynamicsComment>();

    /**
     * 由上个界面传递过来
     */
    private String dynamicsId;

    private boolean isNoTAnyMore;

    /**
     * 初始化控件
     */
    @Override
    public void initView() {

        super.initView();

        //拿到动态的id
        dynamicsId = getIntent().getStringExtra(DYNAMICS_ID_FLAG);

        if (TextUtils.isEmpty(dynamicsId)) {
            T.showShort(context, "参数dynamicsId无效");
            finish();
            return;
        }

        //初始化基本信息
        initBase();

        //获取头部试图
        View headerView = initHeader();

        //初始化内容
        initContent(headerView);

    }

    /**
     * 初始化基本信息
     */
    private void initBase() {

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_menu.setVisibility(View.VISIBLE);
        tv_menu.setText(attention);
        tv_titleName.setText("动态详情");

        screenHeight = ScreenUtils.getScreenHeightPixels(context);
        stateHeight = ScreenUtils.getStatusHeight(context);
        titlebarHeight = (int) getResources().getDimension(R.dimen.titlebar_height);
        titlebarHeight = AutoUtils.getPercentHeightSize(titlebarHeight);

        controlKeyboardLayout(rl_root, rl_content);

    }

    /**
     * 出事化内容
     *
     * @param headerView 列表的头
     */
    private void initContent(View headerView) {

        //创建适配器
        adapter_content = new DynamicsDetailActAdapter(context, dynamicsCommentList);

        //添加头部
        adapter_content.addHeaderView(headerView);

        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        rv_contetn.setLayoutManager(layoutManager);

        //设置适配器
        rv_contetn.setAdapter(adapter_content);

    }

    //@xxx:
    private String contentPrefix;

    private String commentTargetUserId;

    private String commentTargetUserName;

    private String realContent = "";

    @Override
    public void setOnlistener() {
        super.setOnlistener();

        et_content.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                //拿到内容
                String content = s.toString();
                if (contentPrefix != null) {
                    if (!content.startsWith(contentPrefix)) {
                        contentPrefix = null;
                        commentTargetUserId = null;
                        et_content.setText(realContent);
                        commentTargetUserId = null;
                    } else {
                        realContent = content.substring(contentPrefix.length());
                    }
                } else {
                    realContent = content;
                }
            }
        });

        //监听评论item的点击事件
        adapter_content.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //设置回复的人
                commentTargetUserId = dynamicsCommentList.get(position).getUser().getUser_id();
                commentTargetUserName = dynamicsCommentList.get(position).getUser().getUser_nickname();
                contentPrefix = "@" + dynamicsCommentList.get(position).getUser().getUser_nickname() + ":";
                SpannableString spanString = new SpannableString(contentPrefix + realContent);
                spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#5681AF")), 0, contentPrefix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                et_content.setText(spanString);
                et_content.setSelection(contentPrefix.length() + realContent.length());
            }
        });

        //加载更多的监听
        rv_contetn.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { //如果暂停了

                    boolean b = !ViewCompat.canScrollVertically(rv_contetn, 1);
                    //如果已经不可以向下滑动了,就去加载更多
                    if (b) {
                        if (isNoTAnyMore) {
                            return;
                        }
                        presenter.getMoreDynamicsComment();
                    }
                }
            }
        });

        //监听item内部控件的点击事件
        adapter_content.setOnViewInItemClickListener(
                new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
                    @Override
                    public void onViewInItemClick(View v, int position) {

                        int id = v.getId();
                        DynamicsComment dynamicsComment = dynamicsCommentList.get(position);
                        switch (id) {
                            case R.id.sdv_act_dynamics_detail_comment_item_user_icon: //评论的头像
                                //拿到评论的人的id
                                String targetId = dynamicsComment.getUser().getUser_id();
                                context.startActivity(new Intent(context, UserDetailAct.class).putExtra(UserDetailAct.TARGET_USER_ID_FLAG, targetId));
                                break;
                            case R.id.iv_zan: //点赞

                                ImageView iv_zan = (ImageView) v;
                                TextView tv_zan = (TextView) ((ViewGroup) v.getParent()).findViewById(R.id.tv_zan_number);

                                if (dynamicsComment.is_liked()) {
                                    presenter.unlike_comments(tv_zan, iv_zan, dynamicsComment, dynamicsComment.getComment_id());
                                } else {
                                    presenter.like_comments(tv_zan, iv_zan, dynamicsComment, dynamicsComment.getComment_id());
                                }

                                break;
                        }

                    }
                }, R.id.sdv_act_dynamics_detail_comment_item_user_icon,
                R.id.iv_zan);

    }

    /**
     * 头布局
     */
    private View headerView;

    /**
     * 播放视频的组件
     */
    private VideoView video;

    private ImageView iv_play_video;

    private RelativeLayout rl_video;

    private DynamicsDetailPresenter presenter = new DynamicsDetailPresenter(this);

    private UserPresenter userPresenter = new UserPresenter(this);

    private DynamicsPresenter dynamicsPresenter = new DynamicsPresenter(this);

    private PostCommentPresenter postCommentPresenter = new PostCommentPresenter(this);

    /**
     * 初始化头部
     */
    private View initHeader() {

        headerView = View.inflate(context, R.layout.act_dynamics_detail_header, null);

        int hSpace = AutoUtils.
                getPercentWidthSize(context.getResources().getDimensionPixelSize(R.dimen.dynamics_recyclerview_item_image_horizontal_space_space));
        int vSpace = AutoUtils.
                getPercentHeightSize(context.getResources().getDimensionPixelSize(R.dimen.dynamics_recyclerview_item_image_vertical_space_space));

        //图片显示容器
        CommonNineView images = (CommonNineView) headerView.findViewById(R.id.cnv_dynamics_content_item_images);
        images.setVisibility(View.GONE);

        TextView tv_allTextTip = (TextView) headerView.findViewById(R.id.tv_dynamics_content_item_all_text_tip);
        tv_allTextTip.setVisibility(View.GONE);

        //视频播放组件
        rl_video = (RelativeLayout) headerView.findViewById(R.id.rl_video);
        rl_video.setVisibility(View.GONE);
        video = (VideoView) headerView.findViewById(R.id.video);

        images.setHorizontalIntervalDistance(hSpace);
        images.setVerticalIntervalDistance(vSpace);

        return headerView;
    }

    @Override
    public void initData() {
        super.initData();
        presenter.getDynamicsById();
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
            case R.id.rl_root:

                KeyBoardUtils.closeKeybord(et_content,context);

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_foot_emoji:

                fl.setVisibility(View.VISIBLE);
                replaceFragment(new SelectEmojiFragment());

                break;
            case R.id.tv_menu:

                if (attention.equals(tv_menu.getText())) {
                    //关注用户
                    userPresenter.followUser(getUserId());
                } else {
                    //取消关注用户
                    userPresenter.unFollowUser(getUserId());
                }
                break;
        }

    }

    /**
     * 切换fragment,如果当前有输入法显示会先隐藏输入法
     *
     * @param f
     */
    private void replaceFragment(Fragment f) {
        //先隐藏输入法
        KeyBoardUtils.closeKeybord(et_content, context);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl, f);
//        fragmentTransaction.commit();
        fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    /**
     * 在聊天的内容比较多的时候没有问题,当时在内容比较少的时候上面的内容就会看不见,所以这个方法也是不可取的
     *
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private void controlKeyboardLayout(final View root, final View needToScrollView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                DynamicsDetailAct.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = DynamicsDetailAct.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                ViewGroup.LayoutParams layoutParams = rl_content.getLayoutParams();
                int height = screenHeight - titlebarHeight - heightDifference - stateHeight;
                if (height != layoutParams.height) {
                    layoutParams.height = height;
                    if (heightDifference != 0) {
                        fl.setVisibility(View.GONE);
                    }
                    rl_root.requestLayout();
                }
            }
        });
    }

    @Override
    public String getComentTargetUserId() {
        return commentTargetUserId;
    }

    @Override
    public String getDynamicsId() {
        return dynamicsId;
    }

    @Override
    public String getCommentContent() {
        //拿到输入的内容
        String content = et_content.getText().toString();
        //如果有@xxx的那个前缀,就去除,拿到真正的内容
        if (contentPrefix != null && content.startsWith(contentPrefix)) {
            content = content.substring(contentPrefix.length());
        }
        if (commentTargetUserId != null) {
            content = "回复 @" + commentTargetUserName + ": " + content;
        }
        return content;
    }

    @Override
    public void clearCommentContent() {
        commentTargetUserId = null;
        commentTargetUserName = "";
        contentPrefix = "";
        realContent = "";
        et_content.getText().clear();
    }

    @Override
    public String getUserId() {
        if (newDynamics != null) {
            return newDynamics.getUser().getUser_id();
        }
        return null;
    }

    @Override
    public void onLoadDataSuccess(NewDynamics newDynamics) {
        doUpdateDynamics(newDynamics);
        doSetOnViewInDynamicsListener();
        //如果已经关注就显示取消关注的字样
        if (newDynamics.getUser().isFollow()) {
            tv_menu.setText(this.cancelAttention);
        }
        presenter.getDynamicsComment();
    }

    @Override
    public void onLoadCommentDataSuccess(List<DynamicsComment> dynamicsComments) {
        if (dynamicsComments.size() == 0) {
            tip("暂时没有评论哦");
            isNoTAnyMore = true;
        } else {
            isNoTAnyMore = false;
            AdapterNotify.notifyFreshData(dynamicsCommentList, dynamicsComments, adapter_content);
        }
    }

    @Override
    public void onLoadMoreCommentDataSuccess(List<DynamicsComment> dynamicsComments) {
        if (dynamicsComments.size() == 0) {
            isNoTAnyMore = true;
        } else {
            AdapterNotify.notifyAppendData(dynamicsCommentList, dynamicsComments, adapter_content);
        }
    }

    @Override
    public void onCommentSuccess() {
        KeyBoardUtils.closeKeybord(et_content,context);
        presenter.getDynamicsComment();
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
    public void onZanCommentSuccess(TextView tv_zan, ImageView iv_zan, DynamicsComment dynamicsComment, int liked_number) {
        dynamicsComment.setIs_liked(true);
        dynamicsComment.setLiked_number(liked_number);
        tv_zan.setText("" + liked_number);
        iv_zan.setImageResource(R.mipmap.zaned1);
    }

    @Override
    public void onCancelZanCommentSuccess(TextView tv_zan, ImageView iv_zan, DynamicsComment dynamicsComment, int liked_number) {
        dynamicsComment.setIs_liked(false);
        dynamicsComment.setLiked_number(liked_number);
        tv_zan.setText("" + liked_number);
        iv_zan.setImageResource(R.mipmap.zan1);
    }

    /**
     * 保存加载的数据
     */
    private NewDynamics newDynamics;

    private SimpleDraweeView userIcon;

    private ImageView iv_arrowButtom;

    private ImageView iv_collect;
    private ImageView iv_zan;
    private ImageView iv_share;
    private TextView tv_zanNum;

    /**
     * 更新动态的显示
     *
     * @param newDynamics
     */
    private void doUpdateDynamics(final NewDynamics newDynamics) {

        this.newDynamics = newDynamics;

        //用户基本信息
        NewUser user = newDynamics.getUser();

        //加载头像
        userIcon = (SimpleDraweeView) headerView.findViewById(R.id.iv_dynamics_content_item_user_icon);
        userIcon.setImageURI(Uri.parse(user.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        //用户名
        TextView tv_name = (TextView) headerView.findViewById(R.id.tv_dynamics_content_item_user_name);
        tv_name.setText(user.getUser_nickname());

        TextView tv_label = (TextView) headerView.findViewById(R.id.tv_dynamics_content_item_game_label_name);
        tv_label.setText(newDynamics.getGame_tag());

        //时间
        TextView tv_date = (TextView) headerView.findViewById(R.id.tv_dynamics_content_item_date);
        try {
            Date date = DynamicsContentRecyclerViewAdapter.dateUtil.parse(newDynamics.getTime());
            tv_date.setText(DateFormat.format(date.getTime()));
        } catch (ParseException e) {
            tv_date.setText("--:--");
        }

        //下拉的箭头
        iv_arrowButtom = (ImageView) headerView.findViewById(R.id.iv_dynamics_content_item_arrow_bottom);


        //性别
        ImageView iv_sex = (ImageView) headerView.findViewById(R.id.iv_dynamics_content_item_user_sex);
        if ("male".equals(newDynamics.getUser().getUser_sex())) {
            iv_sex.setImageResource(R.mipmap.sex_man_one);
        } else {
            iv_sex.setImageResource(R.mipmap.sex_women_one);
        }

        //设置内容
        TextView tv_content = (TextView) headerView.findViewById(R.id.tv_dynamics_content_item_content);

        //显示的内容
        String content = newDynamics.getContent();

        //如果是剁手时刻
        if ("剁手时刻".equals(newDynamics.getType_tag()) && !TextUtils.isEmpty(content)) {

            SpannableString spannableString = null;

            String moneyStr = "[氪金：" + newDynamics.getMoney() + "R] ";
            //创建可变的字符串
            SpannableString spanString = null;

            spannableString = EmojiReplaceUtil.converse(context, content);

            spanString = new SpannableString(moneyStr);

            spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#FCBD4F")), 0, moneyStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //设置前景色金色

            tv_content.setText(spanString);

            tv_content.append(spannableString);

        } else {
            if (!TextUtils.isEmpty(content)) {
                //加载文章内容
                tv_content.setText(EmojiReplaceUtil.converse(context, content));
            }
        }

        //设置点赞次数
        tv_zanNum = (TextView) findViewById(R.id.tv_dynamics_content_item_foot_zan_count);
        tv_zanNum.setText(newDynamics.getLiked_number() + "");

        //分享的图标
        iv_share = (ImageView) findViewById(R.id.iv_dynamics_content_item_foot_share);

        //设置是否收藏了
        if (newDynamics.is_collected()) { //说明已经收藏了
            iv_collect = (ImageView) findViewById(R.id.iv_dynamics_content_item_foot_collect);
            iv_collect.setImageResource(R.mipmap.collected1);
        } else {
            iv_collect = (ImageView) findViewById(R.id.iv_dynamics_content_item_foot_collect);
            iv_collect.setImageResource(R.mipmap.uncollected1);
        }

        //设置是否点赞
        if (newDynamics.is_liked()) {
            iv_zan = (ImageView) findViewById(R.id.iv_dynamics_content_item_foot_zan);
            iv_zan.setImageResource(R.mipmap.zaned1);
        } else {
            iv_zan = (ImageView) findViewById(R.id.iv_dynamics_content_item_foot_zan);
            iv_zan.setImageResource(R.mipmap.zan1);
        }

        //图片显示容器
        CommonNineView images = (CommonNineView) headerView.findViewById(R.id.cnv_dynamics_content_item_images);
        images.removeAllViewsInLayout();

        //如果是图片类型的动态
        if (newDynamics.getPicture_list() != null && newDynamics.getPicture_list().size() > 0) { //如果是展示图片的
            images.setVisibility(View.VISIBLE);
            List<String> picture_list = newDynamics.getPicture_list();

            String imagePathSubfix = Constant.DYNAMICS_IMAGEPATH_ONE_MORE_SUBFIX;

            //如果只有一张图片
            if (picture_list.size() == 1) { //如果只有一张图片
                try {
                    String extraInfo = newDynamics.getInfor_type();
                    int index = extraInfo.indexOf('*');
                    if (index > -1) {
                        Integer width = Integer.parseInt(extraInfo.substring(0, index));
                        Integer height = Integer.parseInt(extraInfo.substring(index + 1));
                        images.setOneImageWidth(width);
                        images.setOneImageHeight(height);

                        if (width > height) {
                            if (((Number) width).floatValue() / height >= 1.5f) {
                                imagePathSubfix = Constant.DYNAMICS_IMAGEPATH_ONE_H_SUBFIX;
                            } else {
                                imagePathSubfix = Constant.DYNAMICS_IMAGEPATH_ONE_SUBFIX;
                            }
                        } else if (width <= height) {
                            if (((Number) height).floatValue() / width >= 1.5f) {
                                imagePathSubfix = Constant.DYNAMICS_IMAGEPATH_ONE_V_SUBFIX;
                            } else {
                                imagePathSubfix = Constant.DYNAMICS_IMAGEPATH_ONE_SUBFIX;
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }

            for (int i = 0; i < picture_list.size(); i++) {
                View itemView = View.inflate(context, R.layout.simple_drawee_view, null);
                SimpleDraweeView sdv = (SimpleDraweeView) itemView.findViewById(R.id.sdv);
//                Uri uri = Uri.parse(imagesArr[i] + "@!400400");
//                Uri uri = Uri.parse(picture_list.get(i) + imagePathSubfix);
                Uri uri = Uri.parse(picture_list.get(i) + imagePathSubfix);
//                sdv.setImageURI(uri);
                FrescoImageResizeUtil.setResizeControl(sdv, uri);
                images.addClildView(itemView);
            }
        }

        //视频播放组件
        video = (VideoView) headerView.findViewById(R.id.video);
        iv_play_video = (ImageView) headerView.findViewById(R.id.iv_play_video);


        //如果是视频类型的动态
        if (!TextUtils.isEmpty(newDynamics.getVideo_url())) {

            rl_video.setVisibility(View.VISIBLE);

            rv_contetn.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager ll = (LinearLayoutManager) rv_contetn.getLayoutManager();
                    int findFirstVisibleItemPosition = ll.findFirstVisibleItemPosition();
                    if (findFirstVisibleItemPosition > 0) {
                        iv_play_video.setVisibility(View.VISIBLE);
                    }
                }
            });

            iv_play_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iv_play_video.setVisibility(View.INVISIBLE);
                    video.start();
                }
            });

            video.setVideoURI(Uri.parse(newDynamics.getVideo_url()));

//            MediaController m = new MediaController(context);

//            video.setMediaController(m);

//            video.start();

            video.setKeepScreenOn(true);

        }

    }

    /**
     * 设置头像、点赞和收藏的点击事件
     */
    private void doSetOnViewInDynamicsListener() {

        //头像监听
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newDynamics == null) {
                    return;
                }
                String targetId = newDynamics.getUser().getUser_id();
                context.startActivity(new Intent(context, UserDetailAct.class).putExtra(UserDetailAct.TARGET_USER_ID_FLAG, targetId));
            }
        });

        //点赞监听
        iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newDynamics == null) {
                    return;
                }
                if (newDynamics.is_liked()) {
                    dynamicsPresenter.cancelZan(tv_zanNum, iv_zan, newDynamics, newDynamics.get_id());
                } else {
                    dynamicsPresenter.zan(tv_zanNum, iv_zan, newDynamics, newDynamics.get_id());
                }
            }
        });

        //下拉箭头
        iv_arrowButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newDynamics == null) {
                    return;
                }
                ItemMenuEventEntity eventEntity = new ItemMenuEventEntity();
                eventEntity.from = ItemMenuEventEntity.FROM_DYNAMICS_DETAIL;
                eventEntity.dynamicsId = newDynamics.get_id();
                eventEntity.userId = newDynamics.getUser().getUser_id();
                EventBus.getDefault().post(eventEntity);
            }
        });

        iv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newDynamics == null) {
                    return;
                }
                //表示现在是没有收藏的状态
                if (newDynamics.is_collected()) {
                    dynamicsPresenter.uncollect_article(iv_collect, newDynamics, newDynamics.get_id());
                } else {
                    dynamicsPresenter.collect_article(iv_collect, newDynamics, newDynamics.get_id());
                }
            }
        });

        //点击分享的时候弹出来的菜单
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupShare popopShare = new PopupShare(context);
                popopShare.show();
                popopShare.getSb().setLinkUrl(Constant.DYNAMICS_SHARE_URL_PREFIX + newDynamics.get_id());
                popopShare.getSb().setContent(newDynamics.getContent());
            }
        });

        //图片监听
        CommonNineView images = (CommonNineView) headerView.findViewById(R.id.cnv_dynamics_content_item_images);

        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> picture_list = newDynamics.getPicture_list();
                int size = picture_list.size();
                if (size > 0) {
                    CommonNineView cnv = (CommonNineView) view;
                    int clickImageIndex = cnv.getClickImageIndex();
                    if (clickImageIndex > -1 && clickImageIndex < size) {
                        //创建意图跳转到图片预览界面
                        Intent intent = new Intent(context, ImagePreviewAct.class);

                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ImagePreviewAct.IMAGES_FLAG, (ArrayList<String>) picture_list);
                        bundle.putInt(ImagePreviewAct.POSITION, clickImageIndex);
                        intent.putExtras(bundle);

                        startActivity(intent);
                    }
                }
            }
        });


    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void onSessionInvalid() {

    }

    /**
     * 发送按钮调用
     *
     * @param view
     */
    public void sendComment(View view) {
        pass = null;
        postCommentPresenter.postDynamicsComment();
    }

    @Override
    public void onFollowSuccess(Object... obs) {
        tv_menu.setText(cancelAttention);
    }

    @Override
    public void onUnFollowSuccess(Object... obs) {
        tv_menu.setText(attention);
    }

    @Override
    public void onBlockSuccess() {
    }

    @Override
    public void onUnBlockSuccess() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fl.getVisibility() == View.VISIBLE) {
                fl.setVisibility(View.GONE);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 主要负责释放资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
        } catch (Throwable e) {
        }
    }

    @Override
    public void onZanSuccess(TextView tv_zan, final ImageView iv_zan, NewDynamics dynamics, int liked_number) {
        iv_zan.setImageResource(R.mipmap.zaned1);
        dynamics.setLiked_number(liked_number);
        tv_zan.setText(dynamics.getLiked_number() + "");

        ValueAnimator objectAnimator = ObjectAnimator//
                .ofFloat(1f, 1.4f, 1f)//
                .setDuration(1000);

        //设置更新数据的监听
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                iv_zan.setScaleX(value);
                iv_zan.setScaleY(value);
            }

        });

        objectAnimator.start();

        dynamics.setIs_liked(true);

    }

    @Override
    public void onCancleZanSuccess(TextView tv_zan, final ImageView iv_zan, NewDynamics dynamics, int liked_number) {
        iv_zan.setImageResource(R.mipmap.zan1);
        dynamics.setLiked_number(liked_number);
        dynamics.setIs_liked(false);
        tv_zan.setText(liked_number + "");
    }

    @Override
    public void onCollectSuccess(final ImageView iv_collect, NewDynamics dynamics) {
        iv_collect.setImageResource(R.mipmap.collected1);

        ValueAnimator objectAnimator = ObjectAnimator//
                .ofFloat(1f, 1.4f, 1f)//
                .setDuration(1000);

        //设置更新数据的监听
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                iv_collect.setScaleX(value);
                iv_collect.setScaleY(value);
            }

        });

        objectAnimator.start();

        dynamics.setIs_collected(true);
    }

    @Override
    public void onUnCollectSuccess(ImageView iv_collect, NewDynamics dynamics) {
        iv_collect.setImageResource(R.mipmap.uncollected1);

        dynamics.setIs_collected(false);
    }

    @Override
    public void onReportDynamicsSuccess() {
    }

    @Subscribe
    public void onEventSelectedEmoji(SelectEmojiEvent selectEmojiEvent) {
        et_content.append(EmojiReplaceUtil.converse(context, selectEmojiEvent.emoji));
        et_content.setSelection(et_content.getText().length());
    }

    /**
     * 弹出点击item中的下拉小箭头之后的被调用
     *
     * @param item
     */
    @Subscribe
    public void onEventShowDynamicsItemMenu(ItemMenuEventEntity item) {
        //只针对动态详情的发出的事件才接受
        if (item.from == ItemMenuEventEntity.FROM_DYNAMICS_DETAIL) {
            ItemMenu itemMenu = new ItemMenu(context, item);
            itemMenu.show();
        }
    }

    /**
     * 当sessionId失效了
     */
    @Subscribe
    public void onSessionIdInvalid(SessionInvalidEvent event) {
        finish();
    }

    //视频暂停的时候的记录点
    private int seek = -1;

    @Override
    protected void onResume() {
        super.onResume();
        if (rl_video.getVisibility() == View.VISIBLE) { //如果是可见的
            if (seek != -1) {
                video.seekTo(seek);
                video.start();
                seek = -1;
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (rl_video.getVisibility() == View.VISIBLE) { //如果是可见的
            if (video.isPlaying()) { //如果正在播放
                video.pause();
                seek = video.getCurrentPosition();
            }
        }
    }
}
