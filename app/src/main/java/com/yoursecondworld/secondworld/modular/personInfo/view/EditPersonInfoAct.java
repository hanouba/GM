package com.yoursecondworld.secondworld.modular.personInfo.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiaojinzi.ximagelib.config.XImgSelConfig;
import com.xiaojinzi.ximagelib.imageView.XSelectAct;
import com.xiaojinzi.ximagelib.utils.XImageLoader;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.ColorUtil;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.common.timePicker.DatePickerAct;
import com.yoursecondworld.secondworld.modular.personInfo.presenter.EditPersonInfoPresenter;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.NewUser;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;
import com.yoursecondworld.secondworld.modular.userDetail.presenter.UserDetailPresenter;
import com.yoursecondworld.secondworld.modular.userDetail.view.IUserDetailView;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.os.T;

/**
 * 这个界面是在用户详情中点击个人资料跳出来的
 */
@Injection(R.layout.act_edit_person_info)
public class EditPersonInfoAct extends BaseAct implements IEditPersonInfoView, IUserDetailView, AdapterView.OnItemSelectedListener {

    /**
     * 请求选择图片的请求码
     */
    public static final int REQUEST_SELECT_IMAGE_CODE = 111 * 6;

    /**
     * 请求编辑昵称的请求码
     */
    public static final int REQUEST_EDIT_NICKNAME_CODE = 111 * 7;

    /**
     * 请求编辑描述的请求码
     */
    public static final int REQUEST_EDIT_DESC_CODE = 111 * 8;

    //==========================================================================

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

    @Injection(R.id.sp_sex)
    private Spinner sp_sex;

    @Injection(R.id.tv_age)
    private TextView tv_age = null;

    /**
     * 标题栏右边的菜单文本
     */
    @Injection(value = R.id.tv_menu, click = "clickView")
    private TextView tv_menu = null;

    //=============================================================================


    /**
     * 头像,圆形的头像
     */
    @Injection(value = R.id.sdv_icon, click = "clickView")
    private SimpleDraweeView sdv_icon = null;

    @Injection(value = R.id.rl_nickname, click = "clickView")
    private RelativeLayout rl_nickname;

    @Injection(value = R.id.rl_age, click = "clickView")
    private RelativeLayout rl_age;

    @Injection(value = R.id.rl_desc, click = "clickView")
    private RelativeLayout rl_desc;

    @Injection(value = R.id.tv_nickname)
    private TextView tv_name = null;

    @Injection(value = R.id.tv_desc)
    private TextView tv_desc = null;

    private EditPersonInfoPresenter presenter = new EditPersonInfoPresenter(this);

    private UserDetailPresenter userDetailPresenter = new UserDetailPresenter(this);


    @Override
    public void initView() {
        super.initView();

        //初始化基本的信息
        initBase();

    }

    /**
     * 初始化基本的信息
     */
    private void initBase() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        tv_titleName.setText("请设置您的个人资料");
//        tv_titleName.setVisibility(View.GONE);
        tv_menu.setText("保存");
        tv_menu.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData() {
        super.initData();

        //获取用户信息
        userDetailPresenter.getUserInfo();

    }

    /**
     * 显示用户信息
     *
     * @param newUser
     */
    private void disPlayUserInfo(NewUser newUser) {

        //设置头像的地址
//        Glide.with(context).load(newUser.getUser_head_image() + Constant.HEADER_SMALL_IMAGE).into(sdv_icon);
        sdv_icon.setImageURI(Uri.parse(newUser.getUser_head_image() + Constant.HEADER_SMALL_IMAGE));

        //设置昵称
        tv_name.setText(newUser.getUser_nickname());

        //设置描述
        tv_desc.setText(newUser.getUser_introduction());

        //拿到性别
        String user_sex = newUser.getUser_sex();
        if ("male".equals(user_sex)) { //男的
            sp_sex.setSelection(0);
        } else { //女的
            sp_sex.setSelection(1);
        }

        sp_sex.setOnItemSelectedListener(this);

        //拿到年龄的信息
        String user_birthday = newUser.getUser_birthday();

        tv_age.setText(user_birthday);

    }

    /**
     * 头像的地址,如果为null表示没有选择过图片
     * 反之就是选择了新的图片
     */
    private String imagePath;

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        //获取控件的id
        int id = v.getId();

        AlertDialog.Builder builder = null;

        AlertDialog dialog = null;

        View view = null;

        //对id的筛选然后做处理
        switch (id) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.sdv_icon: //点击头像

                XImageLoader imageLoader = new XImageLoader() {
                    @Override
                    public void load(Context context, String localPath, ImageView iv) {
                        Glide.with(context).load(localPath).into(iv);
                    }
                };

                XSelectAct.open(this, new XImgSelConfig.Builder(imageLoader)
                        .btnConfirmText("完成")
                        .title("图片")
                        .isPreview(true)
                        .statusBarColor(ColorUtil.getColor(this, R.color.common_app_color))
                        .titlebarBgColor(ColorUtil.getColor(this, R.color.common_app_color))
                        .maxNum(1)
                        .isPreview(false)
                        .cropSize(1, 1, 800, 800)
                        .isCamera(true)
                        .isCrop(true)
                        .build(), REQUEST_SELECT_IMAGE_CODE);

                break;
            case R.id.rl_nickname: //昵称

                //请求更改昵称
                Intent nickNameIntent = new Intent(context, EditNickNameAct.class);
                startActivityForResult(nickNameIntent, REQUEST_EDIT_NICKNAME_CODE);

                break;

            case R.id.rl_age: //年龄的

                popupAgeSelecter();

                break;
            case R.id.rl_desc: //描述

                //请求更改描述
                Intent descIntent = new Intent(context, EditDescAct.class);
                startActivityForResult(descIntent, REQUEST_EDIT_DESC_CODE);

                break;
        }
    }

    //生日
    private String birth = "1995-01-01";

    /**
     * 弹出选择年龄
     */
    private void popupAgeSelecter() {

        //请求日期的采集
        Intent intent = new Intent(context, DatePickerAct.class);
        startActivityForResult(intent, 222);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGE_CODE && resultCode == RESULT_OK && data != null) { //如果是图片选择器的返回

            ArrayList<String> images = data.getStringArrayListExtra("data");

            if (images.size() == 1) {
                imagePath = images.get(0);
                disPlayImageToIcon();
                presenter.set_head_image(imagePath);
            } else {
                T.showShort(context, "更换头像失败");
            }

        } else if (requestCode == REQUEST_EDIT_NICKNAME_CODE && resultCode == RESULT_OK && data != null) { //更改昵称的
            String nickName = data.getStringExtra("data");
            tv_name.setText(nickName);
        } else if (requestCode == REQUEST_EDIT_DESC_CODE && resultCode == RESULT_OK && data != null) { //更改描述的
            String desc = data.getStringExtra("data");
            tv_desc.setText(desc);
        } else if (requestCode == 222 && resultCode == RESULT_OK && data != null) { //如果是采集日期的
            this.birth = "" + data.getIntExtra(DatePickerAct.YEAR_FLAG, 1995) + "-" +
                    (data.getIntExtra(DatePickerAct.MONTH_FLAG, 1) + 1) + "-" +
                    data.getIntExtra(DatePickerAct.DAY_FLAG, 1);
            tv_age.setText(this.birth);
            presenter.updateAge();
        }
    }

    /**
     * 显示本地的图片到头像上
     */
    private void disPlayImageToIcon() {
        sdv_icon.setImageURI(Uri.fromFile(new File(imagePath)));
    }

    @Override
    public String getImagepath() {
        return imagePath;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public String getUserId() {
        return StaticDataStore.newUser.getUser_id();
    }

    @Override
    public String getNickName() {
        return tv_name.getText().toString();
    }

    @Override
    public String getDesc() {
        return tv_desc.getText().toString();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void onUpdateNickNameSuccess() {
    }

    @Override
    public void onUpdateDescSuccess() {
    }

    @Override
    public String getSex() {
        return sp_sex.getSelectedItemPosition() == 0 ? "male" : "female";
    }

    @Override
    public void onUpdateSexSuccess() {
    }

    @Override
    public void onUpdateBirthSuccess() {
    }

    @Override
    public String getBirth() {
        return birth;
    }

    @Override
    public void closeDialog() {
        super.closeDialog();
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

    @Override
    public String getTargetUserId() {
        return StaticDataStore.newUser.getUser_id();
    }

    @Override
    public void onLoadUserInfoSuccess(UserResult entity) {
        disPlayUserInfo(entity.getUser());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        presenter.updateSex();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
