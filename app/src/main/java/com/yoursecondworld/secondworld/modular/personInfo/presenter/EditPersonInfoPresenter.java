package com.yoursecondworld.secondworld.modular.personInfo.presenter;

import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.oos.OSSUtil;
import com.yoursecondworld.secondworld.modular.personInfo.view.IEditPersonInfoView;

import java.io.File;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/6.
 */
public class EditPersonInfoPresenter implements OSSUtil.InitListener, OSSCompletedCallback<PutObjectRequest, PutObjectResult> {

    private IEditPersonInfoView view;

    public EditPersonInfoPresenter(IEditPersonInfoView view) {
        this.view = view;
    }

    /**
     * 更新昵称
     */
    public void updateNickName() {

        String nickName = view.getNickName();
        if (TextUtils.isEmpty(nickName)) {
            view.tip("昵称不可以为空");
            return;
        }

        if (nickName.length() > 12) {
            view.tip("昵称长度不可以超过12个字符");
            return;
        }

        view.showDialog("正在保存");

        Call<BaseEntity> call = AppConfig.netWorkService.set_nickname(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "nickname"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), nickName}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.tip("保存成功");
                view.onUpdateNickNameSuccess();
            }

            @Override
            public void onOtherStatusResponse(int status) {
                super.onOtherStatusResponse(status);
                view.tip("昵称已存在");
            }
        });

    }

    /**
     * 更新性别
     */
    public void updateSex() {
        //拿到性别
        String sex = view.getSex();

        view.showDialog("正在保存");

        Call<BaseEntity> call = AppConfig.netWorkService.set_sex(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "sex"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), sex}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                //                view.tip("保存成功");
                view.onUpdateSexSuccess();
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("保存性别失败");
            }
        });

    }

    /**
     * 更新生日
     */
    public void updateAge() {

        //拿到生日信息
        String birth = view.getBirth();

        Call<BaseEntity> call = AppConfig.netWorkService.set_birthday(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "birthday"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), birth}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.tip("保存成功");
                view.onUpdateBirthSuccess();
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("保存生日失败");
            }
        });

    }

    /**
     * 更新描述
     */
    public void updateDesc() {

        //获取到描述的信息
        String desc = view.getDesc();

        if (TextUtils.isEmpty(desc)) {
            view.tip("描述不能为空");
            return;
        }

        view.showDialog("正在保存");

        Call<BaseEntity> call = AppConfig.netWorkService.set_introduction(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "introduction"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), desc}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.tip("保存成功");
                view.onUpdateDescSuccess();
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("保存简介失败");
            }
        });

    }

    private File imageFile;

    /**
     * 开始上传图片的工作
     */
    public void set_head_image(String localImagePath) {

        view.showDialog("正在上传头像");

        //拿到要上传的图片
        String imagepath = view.getImagepath();
        if (TextUtils.isEmpty(imagepath)) {
            view.closeDialog();
            view.tip("请选择文件");
        } else {
            File f = new File(imagepath);
            if (f.exists() && f.isFile()) {
                imageFile = f;
                OSSUtil.relaese();
                OSSUtil.init(view.getContext(), view.getUserId(), this);
            } else {
                view.closeDialog();
                view.tip("文件不存在");
            }
        }

    }

    /**
     * 上传成功之后这个地址就是网络的地址
     */
    private String imageUrl;

    @Override
    public void onInitSuccess() {
        //上传成功的话这个地址就是图片你的网络地址
        imageUrl = OSSUtil.getInstance().uploadHeadImage(imageFile, this);
    }

    @Override
    public void onInitFailure() {
        view.closeDialog();
        view.tip("初始化头像上传组建失败");
    }

    /**
     * 头像上传成功之后
     *
     * @param putObjectRequest
     * @param putObjectResult
     */
    @Override
    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {

        Call<BaseEntity> call = AppConfig.netWorkService.set_head_image(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "head_image"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), imageUrl}));

        //设置头像到服务器
        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                view.tip("保存顺利");
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("设置头像失败");
            }
        });

    }

    /**
     * 头像上传失败之后
     *
     * @param putObjectRequest
     * @param e
     * @param e1
     */
    @Override
    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
        view.closeDialog();
        view.tip("头像上传失败");
    }
}
