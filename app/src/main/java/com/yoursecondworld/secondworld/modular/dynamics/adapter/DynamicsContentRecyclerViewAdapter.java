package com.yoursecondworld.secondworld.modular.dynamics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.DateFormat;
import com.yoursecondworld.secondworld.common.EmojiReplaceUtil;
import com.yoursecondworld.secondworld.common.FrescoImageResizeUtil;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewHolder;
import xiaojinzi.base.java.util.DateUtil;
import xiaojinzi.base.java.util.StringUtil;
import xiaojinzi.view.CommonNineView;

/**
 * Created by cxj on 2016/7/9.
 * 动态内容展示的适配器
 * 下面的内容现在被停止使用了,难度和逻辑太深,以后有机会再使用
 * 里面封装了自动播放视频的逻辑
 * 在滚动的时候停止正在播放的视频
 */
public class DynamicsContentRecyclerViewAdapter extends CommonRecyclerViewAdapter<NewDynamics> {

    //2016-08-25 12:52:15.691000
    public static DateUtil dateUtil = new DateUtil("yyyy-MM-dd HH:mm:ss.SSS");

    private int hSpace;
    private int vSpace;

    /**
     * 适配器挂载的recyclerView
     */
    private RecyclerView rv;

    /**
     * 布局管理器
     */
    private LinearLayoutManager layoutManager = null;

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param data    显示的数据
     */
    public DynamicsContentRecyclerViewAdapter(Context context, List<NewDynamics> data) {
        this(context, data, null);
    }

    /**
     * 三个参数的构造函数
     *
     * @param context
     * @param data
     * @param rv
     */
    public DynamicsContentRecyclerViewAdapter(Context context, List<NewDynamics> data, RecyclerView rv) {
        super(context, data);
        this.rv = rv;
        hSpace = AutoUtils.
                getPercentWidthSize(context.getResources().getDimensionPixelSize(R.dimen.dynamics_recyclerview_item_image_horizontal_space_space));
        vSpace = AutoUtils.
                getPercentHeightSize(context.getResources().getDimensionPixelSize(R.dimen.dynamics_recyclerview_item_image_vertical_space_space));
    }


    /**
     * 对数据和控件进行绑定
     *
     * @param h        RecycleView的ViewHolder
     * @param item
     * @param position 当前的下标,不包含头部
     */
    @Override
    public void convert(CommonRecyclerViewHolder h, final NewDynamics item, final int position) {

        if (layoutManager == null) {
            layoutManager = (LinearLayoutManager) rv.getLayoutManager();
        }

        //加载头像
        SimpleDraweeView icon = h.getView(R.id.iv_dynamics_content_item_user_icon);
        icon.setImageURI(Uri.parse(item.getUser().getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        final TextView tv_allTextTip = h.getView(R.id.tv_dynamics_content_item_all_text_tip);

        //显示用户名
        h.setText(R.id.tv_dynamics_content_item_user_name, item.getUser().getUser_nickname());

        //如果是男性的标识
        if ("male".equals(item.getUser().getUser_sex())) {
            h.setImage(R.id.iv_dynamics_content_item_user_sex, R.mipmap.sex_man_one);
        } else {
            h.setImage(R.id.iv_dynamics_content_item_user_sex, R.mipmap.sex_women_one);
        }

        //显示游戏标签的名称
        h.setText(R.id.tv_dynamics_content_item_game_label_name, item.getGame_tag());
        //显示动态的话题
        h.setText(R.id.tv_dynamics_content_item_topic_label_name, item.getType_tag());

        //获取文章的内容
        String content = item.getContent().trim();

        FrameLayout fl = h.getView(R.id.fl_dynamics_content_item_content_container);
        fl.removeAllViewsInLayout();

        //拿到内容的文本控件
        final TextView tv_content = new TextView(context);
        tv_content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //TextView tv_xml_content = h.getView(R.id.tv_dynamics_content_item_content);

        tv_content.setEllipsize(TextUtils.TruncateAt.END);
        tv_content.setMaxLines(3);
        tv_content.setTextColor(Color.BLACK);
        tv_content.setLineSpacing(0, 1.2f);

        fl.addView(tv_content);

//        SpannableString spanString = EmojiReplaceUtil.converse(context, content);
//        tv_content.setText(spanString);

        tv_allTextTip.setVisibility(View.GONE);

        //如果是剁手时刻
        if ("剁手时刻".equals(item.getType_tag()) && !TextUtils.isEmpty(content)) {

            SpannableString spannableString = null;

            String moneyStr = "[氪金：" + item.getMoney() + "R] ";
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

        //这里是解决了判断内容是否显示了省略号的判断
        ViewTreeObserver vto = tv_content.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Layout l = tv_content.getLayout();
                if (l != null) {
                    int lines = l.getLineCount();
                    if (lines > 0) {
                        if (l.getEllipsisCount(lines - 1) > 0) {
                            tv_allTextTip.setVisibility(View.VISIBLE);
                        } else {
                            tv_allTextTip.setVisibility(View.GONE);
                        }
                    }
                }
                return true;
            }
        });


        //时间
        TextView tv_date = h.getView(R.id.tv_dynamics_content_item_date);

        //拿到返回的时间
        String time = item.getTime();
        try {
            //解析出日期时间
            Date date = dateUtil.parse(time);
            //设置上去,按照一定的格式
            tv_date.setText(DateFormat.format(date.getTime()));
        } catch (ParseException e) {
            tv_date.setText("--:--");
        }

        //设置点赞次数
        TextView tv_zanCount = h.getView(R.id.tv_dynamics_content_item_foot_zan_count);
        tv_zanCount.setText(item.getLiked_number() + "");


        //设置评论数
        TextView tv_commentCount = h.getView(R.id.tv_dynamics_content_item_foot_comment_count);
        tv_commentCount.setText(StringUtil.commentNumberFormat(item.getComment_number()) + "");

        //设置是否收藏了
        if (item.is_collected()) { //说明已经收藏了
            h.setImage(R.id.iv_dynamics_content_item_foot_collect, R.mipmap.collected1);
        } else {
            h.setImage(R.id.iv_dynamics_content_item_foot_collect, R.mipmap.uncollected1);
        }

        //设置是否点赞
        if (item.is_liked()) {
            h.setImage(R.id.iv_dynamics_content_item_foot_zan, R.mipmap.zaned1);
        } else {
            h.setImage(R.id.iv_dynamics_content_item_foot_zan, R.mipmap.zan1);
        }

        //九宫格控件显示图片用
        CommonNineView cnv = h.getView(R.id.cnv_dynamics_content_item_images);
        //移除所有的孩子
        cnv.removeAllViewsInLayout();
        //设置九宫格每一个view之间的间距
        cnv.setHorizontalIntervalDistance(hSpace);
        cnv.setVerticalIntervalDistance(vSpace);

        cnv.setOneImageHeight(null);
        cnv.setOneImageWidth(null);

        RelativeLayout rl_video = h.getView(R.id.rl_video);

        if ((item.getPicture_list() == null || item.getPicture_list().size() == 0) &&
                (item.getVideo_list() == null || item.getVideo_list().size() == 0)) {
            cnv.setVisibility(View.GONE);
            rl_video.setVisibility(View.GONE);
        }


        //获取实体中的图片集合
        if (item.getPicture_list() != null && item.getPicture_list().size() > 0) { //如果是展示图片的

            cnv.setVisibility(View.VISIBLE);
            rl_video.setVisibility(View.GONE);

            List<String> picture_list = item.getPicture_list();

            String imagePathSubfix = Constant.DYNAMICS_IMAGEPATH_ONE_MORE_SUBFIX;

            //如果只有一张图片
            if (picture_list.size() == 1) { //如果只有一张图片
                try {
                    String extraInfo = item.getInfor_type();
                    int index = extraInfo.indexOf('*');
                    if (index > -1) {
                        Integer width = Integer.parseInt(extraInfo.substring(0, index));
//                        L.s("DynamicsAdapter", "width = " + width);
                        Integer height = Integer.parseInt(extraInfo.substring(index + 1));
//                        L.s("DynamicsAdapter", "height = " + height);
                        cnv.setOneImageWidth(width);
                        cnv.setOneImageHeight(height);

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
//                SimpleDraweeView sdv = new SimpleDraweeView(context);
                ViewGroup.LayoutParams lp = sdv.getLayoutParams();
                if (lp == null) {
                    sdv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                } else {
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                }
//                Uri uri = Uri.parse(imagesArr[i] + "@!400400");
                Uri uri = Uri.parse(picture_list.get(i) + imagePathSubfix);
//                Uri uri = Uri.parse(picture_list.get(i));
//                sdv.setImageURI(uri);
                FrescoImageResizeUtil.setResizeControl(sdv, uri);
                cnv.addClildView(sdv);
            }

            return;
        }


        //如果是播放视频的
        if (!TextUtils.isEmpty(item.getVideo_url())) {

            cnv.setVisibility(View.GONE);

            rl_video.setVisibility(View.VISIBLE);

            //拿到显示首针图的控件
            SimpleDraweeView sdv = h.getView(R.id.sdv_video_prepare_image);

            //首真图
            sdv.setImageURI(Uri.parse(item.getVideo_cover_picture()));


        } else {
            rl_video.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutViewId(int viewType) {
        return R.layout.dynamics_content_item;
    }


    /**
     * RecyclerView滚动的时候的监听接口
     */
    public class MyScrollListener extends OnScrollListener {

        /**
         * 动态滚动的时候的方向,
         * -1表示向上,也就是手指往下滑动
         * 1表示向下,也就是手指往上滑动
         */
        private int scrollDirection = 0;

        /**
         * 是否滚动
         */
        private boolean isScroll = false;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                isScroll = false;
            } else {
                isScroll = true;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //记录用户滑动的时候的方向
            scrollDirection = dy > 0 ? 1 : -1;
        }

    }
}
