package com.yoursecondworld.secondworld.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.modular.allRelease.view.AllReleaseAct;
import com.yoursecondworld.secondworld.modular.collection.view.CollectionAct;
import com.yoursecondworld.secondworld.modular.drafts.view.DraftsAct;
import com.yoursecondworld.secondworld.modular.editGameArticle.ui.EditGameArticleAct;
import com.yoursecondworld.secondworld.modular.prepareModule.guide.ui.GuideAct;
import com.yoursecondworld.secondworld.modular.im.selectContacts.view.SelectContactsAct;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.view.DynamicsDetailAct;
import com.yoursecondworld.secondworld.modular.main.ui.MainAct;
import com.yoursecondworld.secondworld.modular.myAttention.view.MyAttentionAct;
import com.yoursecondworld.secondworld.modular.myFans.view.MyFansAct;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.view.ImprovePersonInfoAct;
import com.yoursecondworld.secondworld.modular.personInfo.view.EditPersonInfoAct;
import com.yoursecondworld.secondworld.modular.search.ui.SearchAct;
import com.yoursecondworld.secondworld.modular.prepareModule.selectGame.view.SelectGameAct;
import com.yoursecondworld.secondworld.modular.prepareModule.selectPlayer.ui.SelectPlayerAct;
import com.yoursecondworld.secondworld.modular.setting.ui.SettingAct;
import com.yoursecondworld.secondworld.modular.statusNotification.ui.StatusNotificationAct;
import com.yoursecondworld.secondworld.modular.system.accountBind.ui.AccountBindAct;
import com.yoursecondworld.secondworld.modular.system.accountManager.ui.AccountManagerAct;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import xiaojinzi.base.android.activity.ActivityUtil;
import xiaojinzi.base.android.adapter.listView.CommonAdapter;
import xiaojinzi.base.android.adapter.listView.CommonViewHolder;

/**
 * 无用了
 */
public class FunctionAct extends Activity {

    private ListView lv = null;

    Context context;

    private List<String> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_function);
        lv = (ListView) findViewById(R.id.lv);

        Platform[] platforms = ShareSDK.getPlatformList();
        for (Platform p : platforms) {
            System.out.println("platformName:" + p.getName());
        }

        context = this;

        data.add("引导页");
        data.add("主页");
        data.add("选择游戏标签页");
        data.add("选择玩家页面");
        data.add("设置界面");
        data.add("个人资料");
        data.add("动态详情");
        data.add("我的粉丝");
        data.add("我的关注");
        data.add("账号管理");
        data.add("账号绑定");
        data.add("选择聊天联系人");
        data.add("我的收藏");
        data.add("状态通知");
        data.add("所有发布");
        data.add("编辑游戏文章");
        data.add("搜索");
        data.add("设置个人信息two");
        data.add("测试界面");
        data.add("草稿箱");

        lv.setAdapter(new CommonAdapter<String>(this, data, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(CommonViewHolder h, String item, int position) {
                h.setText(android.R.id.text1, item);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ActivityUtil.startActivity(context, GuideAct.class);
                        break;
                    case 1:
                        ActivityUtil.startActivity(context, MainAct.class);
                        break;
                    case 2:
                        ActivityUtil.startActivity(context, SelectGameAct.class);
                        break;
                    case 3:
                        ActivityUtil.startActivity(context, SelectPlayerAct.class);
                        break;
                    case 4:
                        ActivityUtil.startActivity(context, SettingAct.class);
                        break;
                    case 5:
                        ActivityUtil.startActivity(context, ImprovePersonInfoAct.class);
                        break;
                    case 6:
                        ActivityUtil.startActivity(context, DynamicsDetailAct.class);
                        break;
                    case 7:
                        ActivityUtil.startActivity(context, MyFansAct.class);
                        break;
                    case 8:
                        ActivityUtil.startActivity(context, MyAttentionAct.class);
                        break;
                    case 9:
                        ActivityUtil.startActivity(context, AccountManagerAct.class);
                        break;
                    case 10:
                        ActivityUtil.startActivity(context, AccountBindAct.class);
                        break;
                    case 11:
                        ActivityUtil.startActivity(context, SelectContactsAct.class);
                        break;
                    case 12:
                        ActivityUtil.startActivity(context, CollectionAct.class);
                        break;
                    case 13:
                        ActivityUtil.startActivity(context, StatusNotificationAct.class);
                        break;
                    case 14:
                        ActivityUtil.startActivity(context, AllReleaseAct.class);
                        break;
                    case 15:
                        ActivityUtil.startActivity(context, EditGameArticleAct.class);
                        break;
                    case 16:
                        ActivityUtil.startActivity(context, SearchAct.class);
                        break;
                    case 17:
                        ActivityUtil.startActivity(context, EditPersonInfoAct.class);
                        break;
                    case 18:
                        ActivityUtil.startActivity(context, TestActivity.class);
                        break;
                    case 19:
                        ActivityUtil.startActivity(context, DraftsAct.class);
                        break;
                }
                finish();
            }
        });

    }

}
