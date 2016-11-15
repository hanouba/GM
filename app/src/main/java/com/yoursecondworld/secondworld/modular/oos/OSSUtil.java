package com.yoursecondworld.secondworld.modular.oos;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.yoursecondworld.secondworld.AppConfig;
import com.yoursecondworld.secondworld.MyApp;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.JsonRequestParameter;
import com.yoursecondworld.secondworld.common.StaticDataStore;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.UUID;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xiaojinzi.base.android.image.ImageUtil;

/**
 * Created by cxj on 2016/8/2.
 * 封装对阿里云的服务器的操作
 * 由于在本app中只有图片的上传,所以这里只对图片上传做一个封装
 */
public class OSSUtil {

    //构造函数私有化

    private Handler h = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            switch (what) {
                case successFlag:
                    successCount++;
                    if (dialog != null) {
                        dialog.setMessage("第" + (successCount) + "张图片上传成功");
                    }
                    break;
                case failureFlag:
                    failureCount++;
                    if (dialog != null) {
                        dialog.setMessage("第" + (failureCount + successCount) + "张图片上传失败");
                    }
                    break;
            }

            //表示所有的任务都完成了
            if (imageCounts <= successCount + failureCount) {
                if (failureCount > 0) {
                    ossCompletedCallback.onFailure(null, null, null);
                } else {
                    ossCompletedCallback.onSuccess(null, null);
                }
                imageCounts = 0;
                successCount = 0;
                failureCount = 0;
                ossCompletedCallback = null;
            }

        }
    };

    //批量上传的回掉
    private OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback;

    private ProgressDialog dialog;

    //总共的图片数量
    private int imageCounts = 0;

    //成功的次数
    private int successCount = 0;

    //失败的次数
    private int failureCount = 0;

    private final int successFlag = 1;
    private final int failureFlag = 2;


    /**
     * 用于和阿里云服务里打交道
     */
    private static OSS oss;

    /**
     * 在一个存储单元中,作为文件夹的名称
     * 也是为了不同的用户的图片的存储可以区分
     */
    private static String userId;

    /**
     * 动态图片存储单元的名字
     */
    //private String bucketName = "xiaojinzi";
    private String bucketName = "gm-dynamics-shot";

    /**
     * 视频的存储地址
     */
    private String videoBucketName = "gm-video-input";

    /**
     * 头像的存储地址
     */
    private String headBucketName = "gm-head-shot";

    /**
     * 初始化,每次发布的时候如果是带有图片的,那么就需要用到这个阿里云的服务
     * 每次都需要初始化,因为这里不去维护token的有效期,在发布的时候就去获取一个新的token
     * 免去了很多的麻烦,但是同时也给初始化的时候有一个初始化token的操作
     *
     * @param context 上下文
     * @param userId  用户区分不用的图片的文件夹
     */
    public static void init(final Context context, String userId, final InitListener initListener) {

        OSSUtil.userId = userId;

        RequestBody body = JsonRequestParameter.getInstance()
                .addParameter(Constant.RESULT_SESSION_ID, StaticDataStore.session_id)
                .addParameter(Constant.RESULT_OBJECT_ID, StaticDataStore.newUser.getUser_id())
                .build();

        Call<String> call = AppConfig.netWorkService.getPostDynamicsToken(body);

        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jb = new JSONObject(response.body());
                    if (jb.getInt(Constant.RESULT_STATUS) == Constant.Status.SUCCESS) {
//                        String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
                        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
                        String accessKeyId = jb.getString("AccessKeyId");
//                        String accessKeyId = "STS.GmmC3NyFHdpm2uMqiKFM21mQp";
                        String secretKeyId = jb.getString("AccessKeySecret");
//                        String secretKeyId = "6mPvVUoG4eK49vYzRgWmsZfC6ebmWCr1nfpdivJSzeBT";
                        String securityToken = jb.getString("SecurityToken");
//                        String securityToken = "CAEShAMIARKAARwnSdXjFs6uAFmzSG5gvwNA5qmp2UJXT0+3/RnOWm3A7x+8CthjrYuoQhJlLRHVTHstWzJ3mYDg0oTZyH86miyl+xtkN4NkZuSHqoXp05AFkfoqn99d2O6djyjQnbdexYqBwxwQ5dw3XpZlao7scu9OchNhXxg3oAVFop4XfayXGh1TVFMuR21tQzNOeUZIZHBtMnVNcWlLRk0yMW1RcCISMzEzNjAxODY3MjA2ODQzNjkzKglhbGljZS0wMDkwkcP+1u0qOgZSc2FNRDVCSgoBMRpFCgVBbGxvdxIbCgxBY3Rpb25FcXVhbHMSBkFjdGlvbhoDCgEqEh8KDlJlc291cmNlRXF1YWxzEghSZXNvdXJjZRoDCgEqShAxMzI2MjY2MjE1OTEwMDE1UgUyNjg0MloPQXNzdW1lZFJvbGVVc2VyYABqEjMxMzYwMTg2NzIwNjg0MzY5M3IbYWxpeXVub3NzdG9rZW5nZW5lcmF0b3Jyb2xleP+UtdW2x60C";

                        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(
                                accessKeyId,
                                secretKeyId,
                                securityToken);

                        //配置一些参数
                        ClientConfiguration conf = new ClientConfiguration();
                        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
                        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                        oss = new OSSClient(context, endpoint, credentialProvider, conf);

                        initListener.onInitSuccess();
                    } else {
                        initListener.onInitFailure();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    initListener.onInitFailure();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                initListener.onInitFailure();
            }

        });
    }

    /**
     * 声明一个自己
     */
    private static OSSUtil ossUtil = null;

    /**
     * 获取这个上传图片的工具类
     *
     * @return
     */
    public synchronized static OSSUtil getInstance() {
        if (ossUtil == null) {
            ossUtil = new OSSUtil();
        }
        return ossUtil;
    }

    /**
     * 构造函数私有化
     */
    private OSSUtil() {
    }

    /**
     * 上传视频
     *
     * @param localVideo
     * @param ossCompletedCallback
     * @return
     */
    public String uploadVideo(File localVideo, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {

        if (localVideo == null) {
            ossCompletedCallback.onFailure(null, new ClientException("the file of image is null"), null);
        }

        //存储在服务器的key,返回给用户,在上传成功之后可以保存在自己的app服务器上
        String imageKey = userId + "/" + UUID.randomUUID() + "." + xiaojinzi.base.java.common.StringUtil.getLastContent(localVideo.getPath(), ".");

        // 构造上传请求,上传key以时间戳为文件名的一部分,不会重复
        PutObjectRequest put = new PutObjectRequest(videoBucketName, imageKey, localVideo.getPath());

        OSSAsyncTask task = oss.asyncPutObject(put, ossCompletedCallback);

//        return "http://xiaojinzi.img-cn-shanghai.aliyuncs.com/" + imageKey;
        return "http://gm-video-input.oss-cn-hangzhou.aliyuncs.com/" + imageKey;
    }

    /**
     * @param localImagePath
     * @param ossCompletedCallback
     */
    public String uploadImage(String localImagePath, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {
        return uploadImage(new File(localImagePath), ossCompletedCallback);
    }

    /**
     * 上传一张图片
     *
     * @param localImagePath
     * @param ossCompletedCallback
     */
    public String uploadImage(File localImagePath, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {

        if (localImagePath == null) {
            ossCompletedCallback.onFailure(null, new ClientException("the file of image is null"), null);
        }

        //存储在服务器的key,返回给用户,在上传成功之后可以保存在自己的app服务器上
        String imageKey = userId + "/" + UUID.randomUUID() + "." + xiaojinzi.base.java.common.StringUtil.getLastContent(localImagePath.getPath(), ".");

        // 构造上传请求,上传key以时间戳为文件名的一部分,不会重复
        PutObjectRequest put = new PutObjectRequest(bucketName, imageKey, localImagePath.getPath());

        OSSAsyncTask task = oss.asyncPutObject(put, ossCompletedCallback);

//        return "http://xiaojinzi.img-cn-shanghai.aliyuncs.com/" + imageKey;
        return "http://gm-dynamics-shot.img-cn-hangzhou.aliyuncs.com/" + imageKey;

    }

    public String uploadHeadImage(byte[] bytes, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {

        if (bytes == null) {
            ossCompletedCallback.onFailure(null, new ClientException("the bytes of image is null"), null);
        }

        //存储在服务器的key,返回给用户,在上传成功之后可以保存在自己的app服务器上
        String imageKey = userId + "/" + UUID.randomUUID() + ".png";

        // 构造上传请求,上传key以时间戳为文件名的一部分,不会重复
        PutObjectRequest put = new PutObjectRequest(headBucketName, imageKey, bytes);

        OSSAsyncTask task = oss.asyncPutObject(put, ossCompletedCallback);

//        return "http://xiaojinzi.img-cn-shanghai.aliyuncs.com/" + imageKey;
        return "http://gm-head-shot.img-cn-hangzhou.aliyuncs.com/" + imageKey;

    }

    /**
     * 上传头像
     *
     * @param localImagePath
     * @param ossCompletedCallback
     * @return
     */
    public String uploadHeadImage(File localImagePath, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {

        if (localImagePath == null) {
            ossCompletedCallback.onFailure(null, new ClientException("the file of image is null"), null);
        }

        //拿到压缩图片
        Bitmap bitmap = ImageUtil.decodeLocalImage(localImagePath.getPath(), 500, 500);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        return uploadHeadImage(bytes, ossCompletedCallback);

//        存储在服务器的key,返回给用户,在上传成功之后可以保存在自己的app服务器上
//        String imageKey = userId + "/" + UUID.randomUUID() + "." + xiaojinzi.base.java.common.StringUtil.getLastContent(localImagePath.getPath(), ".");
//
        // 构造上传请求,上传key以时间戳为文件名的一部分,不会重复
//        PutObjectRequest put = new PutObjectRequest(headBucketName, imageKey, localImagePath.getPath());

//        OSSAsyncTask task = oss.asyncPutObject(put, ossCompletedCallback);


//        return "http://xiaojinzi.img-cn-shanghai.aliyuncs.com/" + imageKey;
//        return "http://gm-head-shot.img-cn-hangzhou.aliyuncs.com/" + imageKey;

    }

    /**
     * 这个测试一下是不是可以上传一个远程的图片地址
     * 现在对这个头像上传进行改造,读取图片的缩略图进行上传
     *
     * @param magePath
     * @param ossCompletedCallback
     * @return
     */
    public String uploadHeadImage(String magePath, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {

        if (magePath == null) {
            ossCompletedCallback.onFailure(null, new ClientException("the file of image is null"), null);
        }

        //拿到压缩图片
        Bitmap bitmap = ImageUtil.decodeLocalImage(magePath, 200, 200);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();

        return uploadHeadImage(bytes, ossCompletedCallback);

//        //存储在服务器的key,返回给用户,在上传成功之后可以保存在自己的app服务器上
//        String imageKey = userId + "/" + UUID.randomUUID() + "." + xiaojinzi.base.java.common.StringUtil.getLastContent(magePath, ".");
//
//        // 构造上传请求,上传key以时间戳为文件名的一部分,不会重复
//        PutObjectRequest put = new PutObjectRequest(headBucketName, imageKey, magePath);
//
//        OSSAsyncTask task = oss.asyncPutObject(put, ossCompletedCallback);
//
////        return "http://xiaojinzi.img-cn-shanghai.aliyuncs.com/" + imageKey;
//        return "http://gm-head-shot.img-cn-hangzhou.aliyuncs.com/" + imageKey;

    }

    /**
     * 批量上传图片到阿里云服务器
     * 对于一张图片都没有的,将不能回掉接口
     *
     * @param localImagePaths
     * @param dialog
     * @param ossCompletedCallback
     * @return
     */
    public String[] uploadImage(List<File> localImagePaths, ProgressDialog dialog, OSSCompletedCallback<PutObjectRequest, PutObjectResult> ossCompletedCallback) {

        imageCounts = localImagePaths.size();
        String[] images = new String[imageCounts];
        this.ossCompletedCallback = ossCompletedCallback;
        this.dialog = dialog;

        for (int i = 0; i < imageCounts; i++) {

            File file = localImagePaths.get(i);

            String image = uploadImage(file, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

                @Override
                public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                    h.sendEmptyMessage(successFlag);
                }

                @Override
                public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                    h.sendEmptyMessage(failureFlag);
                }

            });

            images[i] = image;

        }

        return images;

    }


    /**
     * 释放,防止空
     */
    public static void relaese() {
        oss = null;
        userId = null;
    }

    public interface InitListener {
        void onInitSuccess();

        void onInitFailure();
    }

}
