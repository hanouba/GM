package com.yoursecondworld.secondworld.modular.share;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by cxj on 2016/7/27.
 * 分享的工具类
 */
public class ShareUtil {

    /**
     * 登陆到微信
     *
     * @param listener
     */
    public static void loginWeiXin(final OnLoginListener listener) {
        //拿到qq的平台
        final Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        //这句话是删除现有数据的意思
//        weixin.removeAccount();
        if (weixin.isAuthValid()) {

            PlatformDb db = weixin.getDb();
            listener.onSuccess(db.getToken(),
                    db.getUserId(), db.getUserName(),
                    db.getUserIcon());
        } else {
            weixin.setPlatformActionListener(new MyPlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    PlatformDb db = weixin.getDb();
                    listener.onSuccess(db.getToken(),
                            db.getUserId(), db.getUserName(),
                            db.getUserIcon());
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    listener.onError();
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    listener.onCancel();
                }
            });
            weixin.authorize();
        }
    }

    /**
     * 分享到微信
     */
    public static void shareToWeiXin(ShareBean sb) {

        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性

        sp.setTitle(sb.title);
        sp.setText(sb.content);
        sp.setImageUrl(sb.imageUrl);
        sp.setUrl(sb.linkUrl);

        final Platform platform = ShareSDK.getPlatform(Wechat.NAME);

        platform.share(sp);

    }

    /**
     * 分享到微博
     */
    public static void shareToWeiBo(ShareBean sb,PlatformActionListener listener) {
        //获取微博的平台
        final SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setTitle(sb.title);
        sp.setText("做游戏的掌控者 @GM游戏社区,快来下载吧" + sb.linkUrl);
//        sp.setImageUrl(sb.imageUrl);
        sp.setUrl(sb.linkUrl);
        final Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        platform.setPlatformActionListener(listener);
        platform.share(sp);
    }

    /**
     * 登陆到微博
     *
     * @param listener
     */
    public static void loginWeiBo(final OnLoginListener listener) {
        //拿到微博的平台
        final Platform weiBo = ShareSDK.getPlatform(SinaWeibo.NAME);
        //weiBo.removeAccount();
        //这句代码的意思是不使用oss授权登陆
        //weiBo.SSOSetting(true);

        //如果已经授权了
        if (weiBo.isAuthValid()) {
            listener.onSuccess(weiBo.getDb().getToken(),
                    weiBo.getDb().getUserId(), weiBo.getDb().getUserName(),
                    weiBo.getDb().getUserIcon());
        } else {
            weiBo.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    listener.onSuccess(weiBo.getDb().getToken(),
                            weiBo.getDb().getUserId(), weiBo.getDb().getUserName(),
                            weiBo.getDb().getUserIcon());
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    listener.onError();
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    listener.onCancel();
                }
            });
            weiBo.authorize();
        }
    }

    /**
     * 分享到qq平台
     */
    public static void shareToQQ(ShareBean sb) {
        //获取qq的平台
        final QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(sb.title);
        sp.setText(sb.content);
        sp.setImageUrl(sb.imageUrl);
        sp.setUrl(sb.linkUrl);
        sp.setSiteUrl(sb.linkUrl);
        sp.setTitleUrl(sb.linkUrl);
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        // 执行图文分享
        platform.share(sp);
    }

    /**
     * 登陆qq
     *
     * @param listener
     */
    public static void loginQQ(final OnLoginListener listener) {
        //拿到qq的平台
        final Platform qq = ShareSDK.getPlatform(QQ.NAME);
        //这句话是删除现有数据的意思
        //qq.removeAccount();
        if (qq.isAuthValid()) {
            PlatformDb db = qq.getDb();
            listener.onSuccess(db.getToken(),
                    db.getUserId(), db.getUserName(),
                    db.getUserIcon());
        } else {
            qq.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    PlatformDb db = qq.getDb();
                    listener.onSuccess(db.getToken(),
                            db.getUserId(), db.getUserName(),
                            db.getUserIcon());
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    listener.onError();
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    listener.onCancel();
                }
            });
            qq.authorize();
        }
    }

    /**
     * 平台监听的适适配器
     */
    public static class MyPlatformActionListener implements PlatformActionListener {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
        }

        @Override
        public void onCancel(Platform platform, int i) {
        }
    }

    public static class ShareBean {
        private String title;
        private String content;
        private String imageUrl;

        /**
         * 点击之后跳转到的分享界面
         */
        private String linkUrl;

        public ShareBean(String title, String content, String imageUrl, String linkUrl) {
            this.title = title;
            this.content = content;
            this.imageUrl = imageUrl;
            this.linkUrl = linkUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }

    /**
     * 登陆的监听
     */
    public interface OnLoginListener {
        /**
         * 成功返回用户的基本信息和token
         *
         * @param token
         * @param userId
         * @param userName
         * @param userAvatar
         */
        void onSuccess(String token, String userId, String userName, String userAvatar);


        /**
         * 取消
         */
        void onCancel();

        /**
         * 错误
         */
        void onError();
    }

}
