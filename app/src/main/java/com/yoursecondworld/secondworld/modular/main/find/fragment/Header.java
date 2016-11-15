package com.yoursecondworld.secondworld.modular.main.find.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import xiaojinzi.autolayout.utils.AutoUtils;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.AdapterNotify;
import com.yoursecondworld.secondworld.common.ViewPagerViewAdapter;
import com.yoursecondworld.secondworld.common.view.XCircleIndicator;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.DynamicsDetailAct;
import com.yoursecondworld.secondworld.modular.main.find.adapter.PopularStarAdapter;
import com.yoursecondworld.secondworld.modular.main.find.entity.Adv;

import com.yoursecondworld.secondworld.modular.main.util.FindPopularStarSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;

import com.yoursecondworld.secondworld.modular.userDetail.view.UserDetailAct;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/26.
 * 列表中头View的逻辑
 */
public class Header {

    /**
     * 头对应的布局
     */
    private View headerView = null;

    /**
     * 上下文
     */
    private Context context;

    /**
     * 上边的大图
     */
    private ViewPager vp;

    /**
     * 指示器
     */
    private XCircleIndicator xCircleIndicator;

    /**
     * 轮播图的图片
     */
    private ArrayList<View> views = new ArrayList<View>();
    ;

    /**
     * 展示热门明星用的
     */
    private RecyclerView rcv_popularStar;

    /**
     * 人气明星的数据
     */
    private List<NewUser> popularStars = new ArrayList<NewUser>();

    /**
     * 人气明星的适配器
     */
    private PopularStarAdapter popularStar_adapter = null;

    /**
     * 广告栏木的适配器
     */
    private ViewPagerViewAdapter advAdapter = null;

    private TextView tv_title;

    /**
     * 构造函数
     *
     * @param context
     */
    public Header(Context context) {
        this.context = context;
        headerView = View.inflate(context, R.layout.frag_find_for_main_recyclerview_header, null);

        initView(headerView);

        advAdapter = new ViewPagerViewAdapter(views);

        vp.setAdapter(advAdapter);

        //创建适配器
        popularStar_adapter = new PopularStarAdapter(context, popularStars);

        //设置人气明星的布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_popularStar.setLayoutManager(layoutManager);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.frag_find_popular_star_item_horizontal_space);
        dimensionPixelSize = AutoUtils.getPercentWidthSize(dimensionPixelSize);
        FindPopularStarSpaceItemDecoration itemDecoration = new FindPopularStarSpaceItemDecoration(context, dimensionPixelSize, popularStars);
        rcv_popularStar.addItemDecoration(itemDecoration);
        //设置适配器
        rcv_popularStar.setAdapter(popularStar_adapter);

        setOnListener();

    }

    /**
     * 设置监听
     */
    private void setOnListener() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                xCircleIndicator.setCurrentPage(position);
                tv_title.setText(advs.get(position).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //监听人气明星的点击
        popularStar_adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                NewUser user = popularStars.get(position);
                Intent i = new Intent(context, UserDetailAct.class);
                i.putExtra(UserDetailAct.TARGET_USER_ID_FLAG, user.getUser_id());
                context.startActivity(i);
            }
        });
    }

    /**
     * 初始化试图
     *
     * @param headerView
     */
    private void initView(View headerView) {
        vp = (ViewPager) headerView.findViewById(R.id.vp_frag_find_big_image);
        rcv_popularStar = (RecyclerView) headerView.findViewById(R.id.rcv_frag_find_part_one_popular_star);
        xCircleIndicator = (XCircleIndicator) headerView.findViewById(R.id.view_indicator);
        tv_title = (TextView) headerView.findViewById(R.id.tv_title);
    }


    public View getHeaderView() {
        return headerView;
    }

    private List<Adv> advs;

    /**
     * 显示轮播图
     *
     * @param advs
     */
    public void disPlayAdv(List<Adv> advs) {
        this.advs = advs;
        views.clear();
        int size = advs.size();
        for (int i = 0; i < size; i++) {
            final Adv adv = advs.get(i);
            SimpleDraweeView sdv = new SimpleDraweeView(context);
            sdv.setImageURI(Uri.parse(adv.getImage()));
            views.add(sdv);
            if (i == 0) {
                tv_title.setText(adv.getTitle());
            }

            sdv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //http://8.yun.haodai.com/Tuiguang/credit/?ref=hd_11015803
                    if (!TextUtils.isEmpty(adv.getArticle_id())) {
                        Intent intent = new Intent(context, DynamicsDetailAct.class);
                        intent.putExtra(DynamicsDetailAct.DYNAMICS_ID_FLAG, adv.getArticle_id());
                        context.startActivity(intent);
                    } else if (!TextUtils.isEmpty(adv.getHyper_text())) {
                        Uri uri = Uri.parse(adv.getHyper_text());
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(it);
                    }
                }
            });
        }

        xCircleIndicator.initData(size, 0);

        advAdapter.notifyDataSetChanged();
    }

    /**
     * 显示人气明星
     *
     * @param popupLarStars
     */
    public void disPlayPopupLarStar(List<NewUser> popupLarStars) {
//        this.popularStars.addAll(popupLarStars);
//        popularStar_adapter.notifyItemRangeInserted(popupLarStars.size(), popupLarStars.size());
        AdapterNotify.notifyFreshData(this.popularStars, popupLarStars, popularStar_adapter);
    }

}
