package com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.EmojiReplaceUtil;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji.adapter.SelectEmojiFragmentAdapter;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji.event.SelectEmojiEvent;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji.itemSpaceDecoration.EmojiSpaceItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.activity.fragment.BaseFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;

/**
 * Created by cxj on 2016/7/29.
 * 这个fragment是显示表情的
 */
public class SelectEmojiFragment extends BaseFragment {

    @Injection(R.id.rv)
    private RecyclerView rv = null;

    /**
     * 显示表情的适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    /**
     * 管理表情的布局管理器
     */
    private StaggeredGridLayoutManager layoutManager = null;

    /**
     * 显示的数据
     */
    private List<Integer> data = new ArrayList<Integer>();

    @Override
    public int getLayoutId() {
        return R.layout.frag_select_emoji_for_post_dynamics;
    }

    @Override
    protected void initView() {
        super.initView();

        addAllEmogi();

        adapter = new SelectEmojiFragmentAdapter(context, data);

        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);

        EmojiSpaceItemDecoration itemDecoration = new EmojiSpaceItemDecoration();
        rv.addItemDecoration(itemDecoration);

        rv.setAdapter(adapter);

    }

    @Override
    protected void setOnListener() {
        super.setOnListener();

        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String emoji = EmojiReplaceUtil.emojiToTextMapper.get(data.get(position));
                if (emoji == null) {
                    emoji = "";
                }
                EventBus.getDefault().post(new SelectEmojiEvent(emoji));
            }
        });

    }

    /**
     * 加入所有表情
     */
    private void addAllEmogi() {
//        data.add(R.mipmap.emoji_0);
        data.add(R.mipmap.emoji_1);
        data.add(R.mipmap.emoji_2);
        data.add(R.mipmap.emoji_3);
        data.add(R.mipmap.emoji_4);
        data.add(R.mipmap.emoji_5);
        data.add(R.mipmap.emoji_6);
        data.add(R.mipmap.emoji_7);
        data.add(R.mipmap.emoji_8);
        data.add(R.mipmap.emoji_9);
        data.add(R.mipmap.emoji_10);
        data.add(R.mipmap.emoji_11);
        data.add(R.mipmap.emoji_12);
        data.add(R.mipmap.emoji_13);
        data.add(R.mipmap.emoji_14);
        data.add(R.mipmap.emoji_15);
        data.add(R.mipmap.emoji_16);
        data.add(R.mipmap.emoji_17);
        data.add(R.mipmap.emoji_18);
        data.add(R.mipmap.emoji_19);
        data.add(R.mipmap.emoji_20);
        data.add(R.mipmap.emoji_21);
        data.add(R.mipmap.emoji_22);
        data.add(R.mipmap.emoji_23);
        data.add(R.mipmap.emoji_24);
        data.add(R.mipmap.emoji_25);
        data.add(R.mipmap.emoji_26);
        data.add(R.mipmap.emoji_27);
        data.add(R.mipmap.emoji_28);
        data.add(R.mipmap.emoji_29);
        data.add(R.mipmap.emoji_30);
        data.add(R.mipmap.emoji_31);
        data.add(R.mipmap.emoji_32);
        data.add(R.mipmap.emoji_33);
        data.add(R.mipmap.emoji_34);
        data.add(R.mipmap.emoji_35);
        data.add(R.mipmap.emoji_36);
        data.add(R.mipmap.emoji_37);
        data.add(R.mipmap.emoji_38);
        data.add(R.mipmap.emoji_39);
        data.add(R.mipmap.emoji_40);
        data.add(R.mipmap.emoji_41);
        data.add(R.mipmap.emoji_42);
        data.add(R.mipmap.emoji_43);
//        data.add(R.mipmap.emoji_44);
        data.add(R.mipmap.emoji_45);
        data.add(R.mipmap.emoji_46);
//        data.add(R.mipmap.emoji_47);
//        data.add(R.mipmap.emoji_48);
//        data.add(R.mipmap.emoji_49);
//        data.add(R.mipmap.emoji_50);
//        data.add(R.mipmap.emoji_51);
//        data.add(R.mipmap.emoji_52);
//        data.add(R.mipmap.emoji_53);
//        data.add(R.mipmap.emoji_54);
//        data.add(R.mipmap.emoji_55);
//        data.add(R.mipmap.emoji_56);
//        data.add(R.mipmap.emoji_57);
//        data.add(R.mipmap.emoji_58);
//        data.add(R.mipmap.emoji_59);
//        data.add(R.mipmap.emoji_60);
//        data.add(R.mipmap.emoji_61);
//        data.add(R.mipmap.emoji_62);
//        data.add(R.mipmap.emoji_63);
//        data.add(R.mipmap.emoji_64);
//        data.add(R.mipmap.emoji_65);
//        data.add(R.mipmap.emoji_66);
//        data.add(R.mipmap.emoji_67);
//        data.add(R.mipmap.emoji_68);
//        data.add(R.mipmap.emoji_69);
//        data.add(R.mipmap.emoji_70);
//        data.add(R.mipmap.emoji_71);
//        data.add(R.mipmap.emoji_72);
//        data.add(R.mipmap.emoji_73);
//        data.add(R.mipmap.emoji_74);
//        data.add(R.mipmap.emoji_75);
//        data.add(R.mipmap.emoji_76);
//        data.add(R.mipmap.emoji_77);
//        data.add(R.mipmap.emoji_78);
//        data.add(R.mipmap.emoji_79);
//        data.add(R.mipmap.emoji_80);
//        data.add(R.mipmap.emoji_81);
//        data.add(R.mipmap.emoji_82);
//        data.add(R.mipmap.emoji_83);
//        data.add(R.mipmap.emoji_84);
//        data.add(R.mipmap.emoji_85);
//        data.add(R.mipmap.emoji_86);
//        data.add(R.mipmap.emoji_87);
//        data.add(R.mipmap.emoji_88);
//        data.add(R.mipmap.emoji_89);
//        data.add(R.mipmap.emoji_90);
//        data.add(R.mipmap.emoji_91);
//        data.add(R.mipmap.emoji_92);
//        data.add(R.mipmap.emoji_93);
//        data.add(R.mipmap.emoji_94);
//        data.add(R.mipmap.emoji_95);
//        data.add(R.mipmap.emoji_96);
//        data.add(R.mipmap.emoji_97);
//        data.add(R.mipmap.emoji_98);
//        data.add(R.mipmap.emoji_99);
//        data.add(R.mipmap.emoji_100);
//        data.add(R.mipmap.emoji_101);
//        data.add(R.mipmap.emoji_102);
//        data.add(R.mipmap.emoji_103);
//        data.add(R.mipmap.emoji_104);
//        data.add(R.mipmap.emoji_105);
//        data.add(R.mipmap.emoji_106);
//        data.add(R.mipmap.emoji_107);
//        data.add(R.mipmap.emoji_108);
//        data.add(R.mipmap.emoji_109);
//        data.add(R.mipmap.emoji_110);
//        data.add(R.mipmap.emoji_111);
//        data.add(R.mipmap.emoji_112);
//        data.add(R.mipmap.emoji_113);
//        data.add(R.mipmap.emoji_114);
//        data.add(R.mipmap.emoji_115);
//        data.add(R.mipmap.emoji_116);
//        data.add(R.mipmap.emoji_117);
//        data.add(R.mipmap.emoji_118);
//        data.add(R.mipmap.emoji_119);
//        data.add(R.mipmap.emoji_120);
//        data.add(R.mipmap.emoji_121);
//        data.add(R.mipmap.emoji_122);
//        data.add(R.mipmap.emoji_123);
//        data.add(R.mipmap.emoji_124);
//        data.add(R.mipmap.emoji_125);
//        data.add(R.mipmap.emoji_126);
//        data.add(R.mipmap.emoji_127);
//        data.add(R.mipmap.emoji_128);
//        data.add(R.mipmap.emoji_129);
    }

}
