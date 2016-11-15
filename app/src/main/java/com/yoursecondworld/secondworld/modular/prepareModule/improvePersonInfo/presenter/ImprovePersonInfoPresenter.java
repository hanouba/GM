package com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.presenter;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.common.CallbackAdapter;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.DownLoadImageUtil;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;
import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.modular.oos.OSSUtil;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.entity.NickNameIsExistResult;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.model.IImprovePersonInfoModel;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.model.ImprovePersonInfoModel;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.view.IImprovePersonInfoView;

import java.io.File;

import retrofit2.Call;

/**
 * Created by cxj on 2016/9/6.
 */
public class ImprovePersonInfoPresenter implements OSSUtil.InitListener, OSSCompletedCallback<PutObjectRequest, PutObjectResult> {


    private IImprovePersonInfoView view;

    private IImprovePersonInfoModel model = new ImprovePersonInfoModel();

    public ImprovePersonInfoPresenter(IImprovePersonInfoView view) {
        this.view = view;
    }

    private String tipcontent;

    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            view.tip(tipcontent);
        }
    };

    /**
     * 调用这个方法
     * 开始更新用户昵称
     * 性别
     * 头像
     *
     * @param isCheckNickName 是因为如果现在昵称是自己的话就不必检测了
     */
    public void startImproveUserInfo(boolean isCheckNickName) {

        //获取到昵称
        String nickName = view.getNickName();
        //判断昵称是不是为空
        if (TextUtils.isEmpty(nickName)) {
            view.tip("昵称不可以为空");
            return;
        }

        view.showDialog("正在完善信息");

        if (isCheckNickName) {
            checkNickNameIsExist();
        } else {
            uploadImage();
        }

    }

    //检查昵称是不是重复
    private void checkNickNameIsExist() {

        Call<NickNameIsExistResult> call = AppConfig.netWorkService.nickname_exist(JsonRequestParameter.build(
                new String[]{"nickname"},
                new Object[]{view.getNickName()}));

        call.enqueue(new CallbackAdapter<NickNameIsExistResult>(view) {
            @Override
            public void onResponse(NickNameIsExistResult entity) {
                if (entity.isNickname_exist()) {
                    view.closeDialog();
                    view.tip("昵称已存在");
                } else {
                    //上传头像
                    uploadImage();
                }
            }

            @Override
            public void onFailure(Call<NickNameIsExistResult> call, Throwable t) {
                super.onFailure(call, t);
                view.tip("网络似乎有点问题");
            }
        });

    }

    private File f;

    private void downLoadImage() {
        String defultImageUrl = view.getDefultImageUrl();
        DownLoadImageUtil d = new DownLoadImageUtil();
        File folfer = new File(Environment.getExternalStorageDirectory(), Constant.SDCARD_IMAGE_FOLDER);
        if (!folfer.exists()) {
            folfer.mkdirs();
        }
        boolean isExists = folfer.exists();
        System.out.println("isExists = " + isExists);
        f = new File(folfer, System.currentTimeMillis() + ".png");
        d.downLoadImage(defultImageUrl, new DownLoadImageUtil.OnDownLoadListener() {
            @Override
            public void onSuccess(byte[] bytes) {
                images = bytes;
                uploadImage();
            }

            @Override
            public void onFail() {
                tipcontent = "完善信息失败";
                h.sendEmptyMessage(0);
                view.closeDialog();
            }
        });
    }

    private File imageFile;

    private byte[] images;

    /**
     * 开始上传图片的工作
     */
    private void uploadImage() {
        //拿到本地的图片地址
        String imageLocalPath = view.getLocalImagePath();
        //如果无需上传,那么这里分两种情况,一种是第三方登录有头像的,需要先下载头像,然后上传
        //第二种就是手机号注册的
        if (TextUtils.isEmpty(imageLocalPath)) {
            String defultImageUrl = view.getDefultImageUrl();
            if (Constant.USER_DEFULT_ICONURL.equals(defultImageUrl)) { //如果是默认的头像
                improveUserInfo();
            } else { //这里是第三方的头像的下载
                //检查是否下载好了
                if (images == null) {
                    downLoadImage();
                } else {
                    OSSUtil.relaese();
                    OSSUtil.init(view.getContext(), view.getUserId(), this);
                }
            }
        } else {
            File f = new File(imageLocalPath);
            if (f.exists() && f.isFile()) {
                imageFile = f;
                OSSUtil.relaese();
                OSSUtil.init(view.getContext(), view.getUserId(), this);
            } else {
                improveUserInfo();
            }
        }
    }

    /**
     * 最终的完善用户的信息的接口
     */
    private void improveUserInfo() {

        //判断是否有上传好的头像地址,没有的话就是用默认的头像
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = view.getDefultImageUrl();
        }

        Call<BaseEntity> call = AppConfig.netWorkService.combine_set_information(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "nickname", "sex", "head_image"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getNickName(), view.getSex(), imageUrl}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                improveBirthInfo();
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                tipcontent = "完善信息失败";
                h.sendEmptyMessage(0);
                view.closeDialog();
            }
        });

    }

    /**
     * 完善出生日期
     */
    public void improveBirthInfo() {

        Call<BaseEntity> call = AppConfig.netWorkService.set_birthday(JsonRequestParameter.build(
                new String[]{Constant.RESULT_SESSION_ID, Constant.RESULT_OBJECT_ID, "birthday"},
                new Object[]{StaticDataStore.session_id, StaticDataStore.newUser.getUser_id(), view.getBirth()}));

        call.enqueue(new CallbackAdapter<BaseEntity>(view) {
            @Override
            public void onResponse(BaseEntity entity) {
                tipcontent = "完善信息成功";
                h.sendEmptyMessage(0);
                view.onProveSuccess();
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {
                super.onFailure(call, t);
                tipcontent = "完善信息失败";
                h.sendEmptyMessage(0);
                view.closeDialog();
            }
        });

    }

    private String imageUrl;

    @Override
    public void onInitSuccess() {
        if (imageFile != null && imageFile.exists() && imageFile.isFile()) {
            //上传成功的话这个地址就是图片你的网络地址
            imageUrl = OSSUtil.getInstance().uploadHeadImage(imageFile, this);
        } else if (images != null) {
            imageUrl = OSSUtil.getInstance().uploadHeadImage(images, this);
        } else {
            tipcontent = "上传图片失败";
            h.sendEmptyMessage(0);
        }
    }

    @Override
    public void onInitFailure() {
        tipcontent = "上传图片失败";
        h.sendEmptyMessage(0);
        view.closeDialog();
    }

    @Override
    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
        tipcontent = "上传图片成功";
        h.sendEmptyMessage(0);
        view.closeDialog();
        improveUserInfo();
    }

    @Override
    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
        tipcontent = "上传图片失败";
        h.sendEmptyMessage(0);
        view.closeDialog();
    }
}
