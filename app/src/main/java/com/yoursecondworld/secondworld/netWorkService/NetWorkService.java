package com.yoursecondworld.secondworld.netWorkService;


import com.yoursecondworld.secondworld.common.baseResult.BaseEntity;
import com.yoursecondworld.secondworld.common.ImUtil;
import com.yoursecondworld.secondworld.commonBean.MasterInfo;
import com.yoursecondworld.secondworld.modular.blackList.entity.BlockUserResult;
import com.yoursecondworld.secondworld.modular.dynamics.entity.DynamicsResult;
import com.yoursecondworld.secondworld.modular.dynamics.entity.ZanResult;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.entity.DynamicsCommentResult;
import com.yoursecondworld.secondworld.modular.dynamicsDetail.entity.DynamicsCommentZanResult;
import com.yoursecondworld.secondworld.modular.main.find.entity.AdvResult;
import com.yoursecondworld.secondworld.modular.messageNotify.fragment.entity.MessageEntity;
import com.yoursecondworld.secondworld.modular.prepareModule.improvePersonInfo.entity.NickNameIsExistResult;
import com.yoursecondworld.secondworld.modular.prepareModule.login.entity.LoginResult;
import com.yoursecondworld.secondworld.modular.selectPostGame.entity.GamesResult;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.CommentsListResult;
import com.yoursecondworld.secondworld.modular.statusNotification.enity.ZanListResult;
import com.yoursecondworld.secondworld.modular.system.accountBind.entity.AccountBindInfoResult;
import com.yoursecondworld.secondworld.modular.systemInfo.entity.UserResult;

import okhttp3.RequestBody;
import retrofit2.Call;

import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by cxj on 2016/6/10.
 * 所有的网络请求
 */
public interface NetWorkService {

    /**
     * 获取sessionId
     *
     * @return
     */
    @GET("aquire_session_id")
    Call<String> aquire_session_id();

    /**
     * 发送注册短信
     *
     * @param body
     * @return
     */
    @POST("send_resginer_message")
    Call<String> send_resginer_message(@Body RequestBody body);

    /**
     * 获取荣晕的token
     *
     * @param body
     * @return
     */
    @POST("get_rc_token")
    Call<ImUtil.TokenResult> get_rc_token(@Body RequestBody body);


    /**
     * 注册
     *
     * @param body
     * @return
     */
    @POST("auth_resginer_message")
    Call<LoginResult> register(@Body RequestBody body);

    /**
     * 登陆
     *
     * @param body
     * @return
     */
    @POST("login")
    Call<LoginResult> login(@Body RequestBody body);

    /**
     * 微信登陆
     *
     * @param body
     * @return
     */
    @POST("wechat_login")
    Call<LoginResult> wechat_login(@Body RequestBody body);

    /**
     * 微博登录
     *
     * @param body
     * @return
     */
    @POST("weibo_login")
    Call<LoginResult> weibo_login(@Body RequestBody body);

    /**
     * qq登陆
     *
     * @param body
     * @return
     */
    @POST("qq_login")
    Call<LoginResult> qq_login(@Body RequestBody body);

    /**
     * 判断昵称是不是一样了
     *
     * @param body
     * @return
     */
    @POST("nickname_exist")
    Call<NickNameIsExistResult> nickname_exist(@Body RequestBody body);

    /**
     * 设置头像地址
     *
     * @param body
     * @return
     */
    @POST("set_head_image")
    Call<BaseEntity> set_head_image(@Body RequestBody body);

    /**
     * 设置出生日期
     *
     * @param body
     * @return
     */
    @POST("set_birthday")
    Call<BaseEntity> set_birthday(@Body RequestBody body);

    /**
     * 设置昵称
     *
     * @param body
     * @return
     */
    @POST("set_nickname")
    Call<BaseEntity> set_nickname(@Body RequestBody body);

    /**
     * 设置简介
     *
     * @param body
     * @return
     */
    @POST("set_introduction")
    Call<BaseEntity> set_introduction(@Body RequestBody body);

    /**
     * 设置性别
     *
     * @param body
     * @return
     */
    @POST("set_sex")
    Call<BaseEntity> set_sex(@Body RequestBody body);

    /**
     * 设置头像,昵称,性别
     *
     * @param body
     * @return
     */
    @POST("combine_set_information")
    Call<BaseEntity> combine_set_information(@Body RequestBody body);

    /**
     * 设置游戏标签
     *
     * @param body
     * @return
     */
    @POST("update_followed_game")
    Call<BaseEntity> update_followed_game(@Body RequestBody body);

    /**
     * 发布动态
     *
     * @param body
     * @return
     */
    @POST("post_article")
    Call<BaseEntity> postDynamics(@Body RequestBody body);

    @POST("get_homepage_hot_article_list")
    Call<DynamicsResult> loadDynamics(@Body RequestBody body);

    /**
     * 获取游戏标签的数据
     *
     * @param body
     * @return
     */
    @POST("get_game_hot_article_list")
    Call<DynamicsResult> get_game_hot_article_list(@Body RequestBody body);

    /**
     * 获取我关注的文章
     *
     * @param body
     * @return
     */
    @POST("get_mylist")
    Call<DynamicsResult> get_mylist(@Body RequestBody body);

    /**
     * 根据动态id查询具体的动态
     *
     * @param body
     * @return
     */
    @POST("get_article_by_id")
    Call<DynamicsResult> getDynamicsById(@Body RequestBody body);


    /**
     * 分页获取评论数量,每次20条
     *
     * @param body
     * @return
     */
    @POST("get_article_comment_list")
    Call<DynamicsCommentResult> getDynamicsComment(@Body RequestBody body);

    /**
     * 给评论点赞
     *
     * @param body
     * @return
     */
    @POST("like_comments")
    Call<DynamicsCommentZanResult> like_comments(@Body RequestBody body);

    @POST("unlike_comments")
    Call<DynamicsCommentZanResult> unlike_comments(@Body RequestBody body);

    /**
     * 评论动态
     *
     * @param body
     * @return
     */
    @POST("post_article_comment")
    Call<BaseEntity> postDynamicsComment(@Body RequestBody body);

    /**
     * 获取发布的时候需要在阿里云使用的token
     * 这个请求也是需要登录以后,否则不予响应
     *
     * @param body
     * @return
     */
    @POST("get_upload_file_key")
    Call<String> getPostDynamicsToken(@Body RequestBody body);

    /**
     * 获取收藏的文章
     *
     * @param body
     * @return
     */
    @POST("get_collected_article_list")
    Call<DynamicsResult> getCollectedArticle(@Body RequestBody body);

    /**
     * 获取用户的信息
     *
     * @return
     */
    @POST("get_user_information")
    Call<UserResult> getUserInfo(@Body RequestBody body);

    /**
     * 获取用户的动态
     *
     * @param body
     * @return
     */
    @POST("get_user_article")
    Call<DynamicsResult> getUserDynamics(@Body RequestBody body);

    /**
     * 搜索动态
     *
     * @param body
     * @return
     */
    @POST("search_article")
    Call<DynamicsResult> searchDynamics(@Body RequestBody body);

    /**
     * 搜索用户
     *
     * @param body
     * @return
     */
    @POST("search_user")
    Call<UserResult> searchUser(@Body RequestBody body);

    /**
     * 发送忘记密码的短信
     *
     * @param body
     * @return
     */
    @POST("send_find_back_password_message")
    Call<BaseEntity> sendForgetPasswordSms(@Body RequestBody body);

    /**
     * 更改密码
     *
     * @param body
     * @return
     */
    @POST("auth_message_and_change_password")
    Call<String> changePassword(@Body RequestBody body);

    /**
     * 删除动态
     *
     * @param body
     * @return
     */
    @POST("delete_article")
    Call<BaseEntity> deleteDynamics(@Body RequestBody body);

    /**
     * 获取发现页面的广告
     *
     * @param body
     * @return
     */
    @POST("get_banner_list")
    Call<AdvResult> getAllAdv(@Body RequestBody body);

    /**
     * 获取人气明星
     *
     * @param body
     * @return
     */
    @POST("get_hot_user_list")
    Call<UserResult> getAllPopupLarStar(@Body RequestBody body);

    /**
     * 返回推荐关注的人
     *
     * @param body
     * @return
     */
    @POST("get_recommend_user_list")
    Call<UserResult> get_recommend_user_list(@Body RequestBody body);

    /**
     * 获取互相关注的列表
     *
     * @param body
     * @return
     */
    @POST("get_user_friends_list")
    Call<UserResult> getFollowEachOther(@Body RequestBody body);

    /**
     * 获取全部粉丝
     *
     * @param body
     * @return
     */
    @POST("get_user_fans_list")
    Call<UserResult> loadUnFollowFans(@Body RequestBody body);

    /**
     * 获取我的关注
     *
     * @param body
     * @return
     */
    @POST("get_user_follows_list")
    Call<UserResult> getUserFollows(@Body RequestBody body);

    /**
     * 搜索我的关注
     *
     * @param body
     * @return
     */
    @POST("search_followed_user")
    Call<UserResult> search_followed_user(@Body RequestBody body);

    /**
     * 拉黑用户
     *
     * @param body
     * @return
     */
    @POST("block_user")
    Call<BaseEntity> blockUser(@Body RequestBody body);

    /**
     * 添加关注
     *
     * @param body
     * @return
     */
    @POST("follow_user")
    Call<BaseEntity> followUser(@Body RequestBody body);

    /**
     * 取消关注
     *
     * @param body
     * @return
     */
    @POST("unfollow_user")
    Call<BaseEntity> unFollowUser(@Body RequestBody body);

    /**
     * 获取热门文章列表
     *
     * @param body
     * @return
     */
    @POST("get_hot_article_list")
    Call<DynamicsResult> get_hot_article_list(@Body RequestBody body);

    /**
     * 对动态进行点赞
     *
     * @return
     */
    @POST("like_article")
    Call<ZanResult> zan(@Body RequestBody body);

    /**
     * 取消点赞
     *
     * @return
     */
    @POST("unlike_article")
    Call<ZanResult> cancelZan(@Body RequestBody body);

    /**
     * 收藏文章
     *
     * @param body
     * @return
     */
    @POST("collect_article")
    Call<BaseEntity> collect_article(@Body RequestBody body);

    /**
     * 取消收藏文章
     *
     * @param body
     * @return
     */
    @POST("uncollect_article")
    Call<BaseEntity> uncollect_article(@Body RequestBody body);

    /**
     * 举报文章
     *
     * @param body
     * @return
     */
    @POST("report_article")
    Call<BaseEntity> report_article(@Body RequestBody body);


    /**
     * 发送反馈的接口
     *
     * @param body
     * @return
     */
    @POST("post_feedback")
    Call<BaseEntity> post_feedback(@Body RequestBody body);

    /**
     * 第三方的绑定情况
     *
     * @param body
     * @return
     */
    @POST("is_third_party_account_binded")
    Call<AccountBindInfoResult> is_third_party_account_binded(@Body RequestBody body);

    /**
     * 绑定qq的接口
     *
     * @param body
     * @return
     */
    @POST("bind_qq")
    Call<BaseEntity> bind_qq(@Body RequestBody body);

    /**
     * 绑定微信的接口
     *
     * @param body
     * @return
     */
    @POST("bind_wechat")
    Call<BaseEntity> bind_wechat(@Body RequestBody body);

    /**
     * 绑定微博的接口
     *
     * @param body
     * @return
     */
    @POST("bind_weibo")
    Call<BaseEntity> bind_weibo(@Body RequestBody body);

    /**
     * 搜索用户文章
     *
     * @param body
     * @return
     */
    @POST("search_user_article")
    Call<DynamicsResult> search_user_article(@Body RequestBody body);

    /**
     * 获取黑名单的接口
     *
     * @param body
     * @return
     */
    @POST("get_user_blocked_list")
    Call<BlockUserResult> get_user_blocked_list(@Body RequestBody body);

    /**
     * 取消拉黑用户
     *
     * @param body
     * @return
     */
    @POST("unblock_user")
    Call<BaseEntity> unblock_user(@Body RequestBody body);

    /**
     * 获取评论的列表
     *
     * @param body
     * @return
     */
    @POST("mes_get_comments_list")
    Call<CommentsListResult> mes_get_comments_list(@Body RequestBody body);

    /**
     * 获取点赞列表
     *
     * @param body
     * @return
     */
    @POST("mes_get_likes_list")
    Call<ZanListResult> mes_get_likes_list(@Body RequestBody body);

    /**
     * @param body
     * @return
     */
    @POST("mes_new_message_exist")
    Call<MessageEntity> mes_new_message_exist(@Body RequestBody body);

    /**
     * 发送绑定电话号码的短信
     *
     * @param body
     * @return
     */
    @POST("send_bind_phone_number_message")
    Call<MessageEntity> send_bind_phone_number_message(@Body RequestBody body);

    /**
     * 绑定手机号
     *
     * @param body
     * @return
     */
    @POST("auth_bind_phone_number_message")
    Call<MessageEntity> auth_bind_phone_number_message(@Body RequestBody body);

    @POST("search_game")
    Call<GamesResult> search_game(@Body RequestBody body);

    /**
     * 获取热门游戏
     *
     * @param body
     * @return
     */
    @POST("get_hot_games")
    Call<GamesResult> get_hot_games(@Body RequestBody body);

    /**
     * 举报用户
     *
     * @param body
     * @return
     */
    @POST("report_user")
    Call<BaseEntity> report_user(@Body RequestBody body);

    /**
     * 是指每个月能花费金额
     *
     * @param body
     * @return
     */
    @POST("master_set_money")
    Call<BaseEntity> master_set_money(@Body RequestBody body);

    /**
     * 设置每个月能花费的时间
     *
     * @param body
     * @return
     */
    @POST("master_set_time")
    Call<BaseEntity> master_set_time(@Body RequestBody body);

    /**
     * 获取Masterd的信息
     *
     * @param body
     * @return
     */
    @POST("master_get_infor_by_month")
    Call<MasterInfo> master_get_infor_by_month(@Body RequestBody body);

    /**
     * 添加氪金
     *
     * @param body
     * @return
     */
    @POST("master_cost_money")
    Call<BaseEntity> master_cost_money(@Body RequestBody body);

    /**
     * 添加花费时间
     *
     * @param body
     * @return
     */
    @POST("master_cost_time")
    Call<BaseEntity> master_cost_time(@Body RequestBody body);

}
