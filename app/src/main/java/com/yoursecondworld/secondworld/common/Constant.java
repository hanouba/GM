package com.yoursecondworld.secondworld.common;

import okhttp3.MediaType;

/**
 * Created by cxj on 2016/7/7.
 * 常量类
 */
public class Constant {

    /**
     * fresco加载本地图片你的时候的前缀,后面跟上资源的地址,比如如下:
     * "res://com.xxx.yy/"+R.raw.web_loading
     */
    public static final String FRESCO_LOCALIMAGE_PREFIX = "res://com.xxx.yy/";


    /**
     * 动态分享的前缀
     */
//    public static final String DYNAMICS_SHARE_URL_PREFIX = "http://114.55.110.208:9000/shared/";
    public static final String DYNAMICS_SHARE_URL_PREFIX = "https://gm.yoursecondworld.com/shared/";


    /**
     * 缓存图片的文件夹
     */
    public static final String SDCARD_IMAGE_FOLDER = "gameMaster/image";

    /**
     * 请求的时候的类型
     */
    public static final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    /**
     * 返回的json中的是否成功的标识符
     */
    public static final String RESULT_STATUS = "status";


    public static final String RESULT_PASS = "pass";

    public static final String RESULT_SESSION_ID = "session_id";

    public static final String RESULT_OBJECT_ID = "object_id";

    /**
     * 动态中图片的路径的分隔符
     */
    public static final char DYNAMICS_IMAGEPATH_SPLIT_CHAR = ';';

    /**
     * 动态内容在不展开的情况下就显示一百个字最多
     */
    public static final int DYNAMICS_CONTENT_MAX_LENGTH = 100;

    /**
     * 动态的item的左右两边的外边距
     * 这个值需要是配过的哦
     */
    public static final int DYNAMICS_ITEM_LEFT_AND_RIGHT_MARGIN = 24;

    /**
     * 小头像
     */
    public static final String HEADER_SMALL_IMAGE = "@!200_200";

    /**
     * 大头像
     */
    public static final String HEADER_BIG_IMAGE = "@!400400";

    /**
     * 动态中的图片的后缀,多张图片的时候使用
     */
    public static final String DYNAMICS_IMAGEPATH_ONE_MORE_SUBFIX = "@!300_300";

    /**
     * 一张图片的时候使用,宽大于高的情况,比例大于1.5时候
     */
    public static final String DYNAMICS_IMAGEPATH_ONE_H_SUBFIX = "@!600_400";

    /**
     * 一张图片的时候使用,高大于宽的情况,比例大于1.5时候
     */
    public static final String DYNAMICS_IMAGEPATH_ONE_V_SUBFIX = "@!400_600";

    /**
     * 一张图片的时候使用,高和宽差不多的情况,比例小于1.5时候
     */
    public static final String DYNAMICS_IMAGEPATH_ONE_SUBFIX = "@!400_400";

    /**
     * 图片模糊的效果
     */
    public static final String USER_ICON_BLUR = "@!BlurImage";


    public static final String ORIGIN_IMAGE = "@!origin";

    public static final String MORIGIN_IMAGE = "@!mOrigin";

    /**
     * 用户默认的头像地址
     */
    public static final String USER_DEFULT_ICONURL = "http://head-shot.img-cn-shanghai.aliyuncs.com/user_defult_icon.png";


    public class Status {

        public static final int SUCCESS = 0;

    }

    /**
     * 有关轻量级存储的
     */
    public class SP {

        /**
         * 关于即时通信的
         */
        public class im {

            /**
             * IM用户的标识
             */
            public static final String TOKEN = "im_token";

        }

        /**
         * 关于登陆界面的
         */
        public class loginAct {

            /**
             * 用户电话,也就是账户信息
             */
            public static final String USERACCOUNT = "userAccount";

            /**
             * 用户的密码
             */
            public static final String USERPASSWORD = "userPassword";

        }

        /**
         * 有关设置的
         */
        public class mySetting {

            /**
             * 视频播放的模式
             */
            public static final String VIDEOPLAYMODE_FLAG = "videoplaymode";

            /**
             * 默认的视频加载模式
             */
            public static final int DEFAULTMODE = 0;

            /**
             * 仅3G/4G/Wi-Fi下自动播放
             */
            public static final int THREEG_FOURG_WIFI_MODE = 1;

            /**
             * 不自动播放的模式i
             */
            public static final int NOTAUTOPLAY_MODE = 2;

            /**
             * 勿扰是不是打开的标识
             */
            public static final String NOTDISTURBISOPEN_FLAG = "notdisturbisopen";

            /**
             * 勿打扰的起始时间
             */
            public static final String NOTDISTURBSTARTTIME_FLAG = "notdisturbstarttime";

            /**
             * 勿打扰的结束时间
             */
            public static final String NOTDISTURBENDTIME_FLAG = "notdisturbendtime";

            /**
             * 新消息是否通知
             */
            public static final String NEW_MESSAGE_NOTIFY_FLAG = "newMessageNotify";


            /**
             * 是否移动网络下省流量
             */
            public static final String MOBILE_NETWORK_TRAFFIC_FLAG = "mobileNetworkTraffic";

        }
    }

}
