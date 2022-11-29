package com.longya.live.retrofit;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by WuXiaolong on 2016/3/24.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public interface ApiStores {
    //baseUrl
    String API_SERVER_URL = "https://api.onecric.tv/";//正式

    //获取验证码
    @POST("api/user/getCode")
    Observable<JsonObject> getCode(@Body RequestBody body);

    //注册
    @POST("api/user/registered")
    Observable<JsonObject> register(@Body RequestBody body);

    //验证码登录
    @POST("plus/user/verificationCodeLogin")
    Observable<JsonObject> loginByCode(@Body RequestBody body);

    //密码登录
    @POST("api/user/login")
    Observable<JsonObject> loginByPwd(@Body RequestBody body);

    //退出登录
    @POST("api/Member/SignOut")
    Observable<JsonObject> signOut(@Header("token") String token);

    //注销用户
    @POST("api/member/logout")
    Observable<JsonObject> logOut(@Header("token") String token,
                                  @Query("code") String code);

    //更新极光推送id
    @POST("api/member/jiguang")
    Observable<JsonObject> updateJgId(@Header("token") String token,
                                      @Query("pushid") String pushid);

    //忘记密码
    @POST("api/user/forgotPassword")
    Observable<JsonObject> changePwd(@Body RequestBody body);

    //获取用户信息
    @GET("api/Member/info")
    Observable<JsonObject> getUserInfo(@Header("token") String token);

    //获取指定用户信息
    @GET("api/User/getUserInfo")
    Observable<JsonObject> getUserInfo(@Header("token") String token,
                                       @Query("id") int uid);

    //关注用户
    @GET("api/Member/attention")
    Observable<JsonObject> doFollow(@Header("token") String token,
                                    @Query("id") int id);

    //获取游客userSig
    @GET("api/Universal/imTouristAccount")
    Observable<JsonObject> getVisitorUserSig();

    //邀请二维码
    @GET("api/member/InvitationCode")
    Observable<JsonObject> getInviteQrCode(@Header("token") String token);

    //获取默认配置
    @GET("api/universal/getHot")
    Observable<JsonObject> getDefaultConfiguration();

    //获取首页足球列表
    @GET("api/Football/football_match")
    Observable<JsonObject> getFootballList(@Header("token") String token,
                                           @Query("type") int type,
                                           @Query("time") String time,
                                           @Query("page") int page,
                                           @Query("competition_id") String competition_id);

    //获取足球比赛详情
    @GET("api/Football/football_match_detail")
    Observable<JsonObject> getFootballDetail(@Header("token") String token,
                                             @Query("id") int id);

    //获取足球比赛阵容
    @GET("api/Football/football_match_lineup")
    Observable<JsonObject> getFootballMatchLineup(@Query("id") int id);

    //获取足球比赛指数详情
    @GET("api/Football/football_odds_history")
    Observable<JsonObject> getFootballIndexDetail(@Query("id") int id);

    //获取足球比赛指数列表
    @GET("api/Football/football_odds_history_detail")
    Observable<JsonObject> getFootballIndexList(@Query("id") int id,
                                                @Query("type") String type);

    //获取首页篮球列表
    @GET("api/Basketball/getList")
    Observable<JsonObject> getBasketballList(@Header("token") String token,
                                             @Query("type") int type,
                                             @Query("time") String time,
                                             @Query("page") int page,
                                             @Query("competition_id") String id);

    //获取篮球比赛详情
    @GET("api/Basketball/getInfo")
    Observable<JsonObject> getBasketballDetail(@Header("token") String token,
                                               @Query("id") int id);

    //获取篮球比赛指数详情
    @GET("api/Basketball/exponent")
    Observable<JsonObject> getBasketballIndexDetail(@Header("token") String token,
                                                    @Query("id") int id);

    //获取篮球比赛指数列表
    @GET("api/Basketball/getExponentList")
    Observable<JsonObject> getBasketballIndexList(@Header("token") String token,
                                                  @Query("id") int id,
                                                  @Query("type") String type);

    //获取同联赛详情列表
    @GET("api/Basketball/compensation")
    Observable<JsonObject> getBasketballHistoryIndexDetail(@Header("token") String token,
                                                           @Query("id") int id,
                                                           @Query("company_id") int company_id);

    //获取同联赛详情列表
    @GET("api/Football/compensation_detail")
    Observable<JsonObject> getFootballHistoryIndexDetail(@Query("id") int id,
                                                         @Query("company_id") int company_id);

    //获取正在直播列表
    @GET("api/live_streaming/getLiveList")
    Observable<JsonObject> getLivingList(@Header("token") String token,
                                         @Query("page") int page,
                                         @Query("type") int type,
                                         @Query("isweb") int isweb);

    //获取正在直播列表
    @GET("api/live_streaming/getLiveListinfo")
    Observable<JsonObject> getAllLivingList(@Header("token") String token);

    //获取直播详情
    @GET("api/live_streaming/getRoomInfo")
    Observable<JsonObject> getLiveDetail(@Header("token") String token,
                                         @Query("uid") int uid);

    //获取礼物列表
    @GET("api/Gift/getList")
    Observable<JsonObject> getGiftList(@Header("token") String token);

    //获取背包礼物列表
    @GET("api/Gift/getUserGiftList")
    Observable<JsonObject> getBackpackGiftList(@Header("token") String token);

    //赠送礼物
    @GET("api/member/handselGift")
    Observable<JsonObject> sendGift(@Header("token") String token,
                                    @Query("gift_id") int gift_id,
                                    @Query("anchor_id") int anchor_id,
                                    @Query("type") int type);

    //获取贵族列表
    @GET("api/Guard/getList")
    Observable<JsonObject> getNobelList(@Header("token") String token);

    //首页全部赛程
    @POST("api/hot_match/all_match")
    Observable<JsonObject> getAllMatch();

    //获取充值列表
    @POST("api/chargerules/getList")
    Observable<JsonObject> getCoinList(@Header("token") String token);

    //获取支付链接
    @GET("api/chargerules/payUrl")
    Observable<JsonObject> getPayUrl(@Header("token") String token,
                                     @Query("id") int id,
                                     @Query("type") int type);

    //获取七牛token
    @POST("api/Member/getQiniuToken")
    Observable<JsonObject> getQiniuToken(@Header("token") String token);

    //修改用户信息
    @POST("api/member/updateInfo")
    Observable<JsonObject> updateUserInfo(@Header("token") String token,
                                          @Body RequestBody body);

    //获取关注列表
    @GET("api/User/attentionList")
    Observable<JsonObject> getAttentionList(@Header("token") String token,
                                            @Query("page") int page,
                                            @Query("type") int type);

    //获取我的粉丝列表
    @GET("api/User/watchlistList")
    Observable<JsonObject> getFansList(@Header("token") String token,
                                       @Query("page") int page,
                                       @Query("uid") int uid);

    //获取炫彩气泡列表
    @GET("api/Barrage/getList")
    Observable<JsonObject> getBackgroundDanmuList(@Header("token") String token);

    //购买贵族
    @GET("api/guard_user/buy")
    Observable<JsonObject> buyNoble(@Header("token") String token,
                                    @Query("id") int id,
                                    @Query("anchor_id") int anchor_id);

    //发送炫彩弹幕
    @GET("api/Barrage/send")
    Observable<JsonObject> sendBgDanmu(@Header("token") String token,
                                       @Query("gift_id") int gift_id,
                                       @Query("uid") int uid,
                                       @Query("info") String info);

    //发送喇叭消息
    @GET("api/Member/sneHorn")
    Observable<JsonObject> sendBroadcast(@Header("token") String token,
                                         @Query("content") String content);

    //用户预约比赛(直播间中使用)
    @POST("api/live/reserveMatch")
    Observable<JsonObject> reserveMatchOne(@Header("token") String token,
                                           @Body RequestBody body);

    //用户预约比赛(首页的直播tab页使用)
    @POST("api/live/reserveMatch2")
    Observable<JsonObject> reserveMatchTwo(@Header("token") String token,
                                           @Body RequestBody body);

    //获取我的预约列表
    @GET("api/Live_streaming/getUserReserveLiveList")
    Observable<JsonObject> getReserveList(@Header("token") String token,
                                          @Query("page") int page);

    //修改支付密码
    @POST("api/Member/paymentPassword")
    Observable<JsonObject> setPayPwd(@Header("token") String token,
                                     @Body RequestBody body);

    //获取当前用户银行卡详情
    @GET("api/Bank_card/getInfo")
    Observable<JsonObject> getBankInfo(@Header("token") String token);

    //绑定银行卡
    @GET("api/Bank_card/bind")
    Observable<JsonObject> bindAccount(@Header("token") String token,
                                       @Query("bank_name") String bankName,
                                       @Query("card_number") String cardNumber,
                                       @Query("name") String name,
                                       @Query("id_card") String idCard,
                                       @Query("code") String code);

    //获取当前主播动态
    @GET("api/anchor_dynamics/userPostList")
    Observable<JsonObject> getAnchorMoving(@Header("token") String token,
                                           @Query("page") int page,
                                           @Query("uid") int uid);

    //获取动态详情
    @GET("api/anchor_dynamics/info")
    Observable<JsonObject> getMovingInfo(@Header("token") String token,
                                         @Query("page") int page,
                                         @Query("id") int id);

    //获取直播预告
    @GET("api/live_streaming/getReserveLiveList")
    Observable<JsonObject> getReserveLiveList(@Header("token") String token,
                                              @Query("page") int page,
                                              @Query("uid") int uid);

    //主播动态评论
    @POST("api/anchor_dynamics_user/comment")
    Observable<JsonObject> doMovingComment(@Header("token") String token,
                                           @Body RequestBody body);

    //获取动态评论二级列表
    @POST("api/anchor_dynamics/commentList")
    Observable<JsonObject> getMovingReplyList(@Header("token") String token,
                                              @Body RequestBody body);

    //动态点赞
    @POST("api/anchor_dynamics_user/likes")
    Observable<JsonObject> doMovingLike(@Header("token") String token,
                                        @Body RequestBody body);

    //动态评论点赞
    @POST("api/anchor_dynamics_user/commentLikes")
    Observable<JsonObject> doMovingCommentLike(@Header("token") String token,
                                               @Body RequestBody body);

    //获取喇叭历史
    @GET("api/Member/hornList")
    Observable<JsonObject> getSpeakerHistoryList(@Header("token") String token,
                                                 @Query("page") int page);

    //获取直播间排行榜
    @GET("api/live_streaming/roomRanking")
    Observable<JsonObject> getLiveRankingList(@Header("token") String token,
                                              @Query("uid") int uid,
                                              @Query("type") int type);

    //获取头条分类
    @GET("api/Headlines/classification")
    Observable<JsonObject> getHeadlineClassify(@Header("token") String token);

    //获取头条列表
    @GET("api/Headlines/getList")
    Observable<JsonObject> getHeadlineList(@Header("token") String token,
                                           @Query("page") int page,
                                           @Query("cid") int cid);

    //获取头条详情
    @GET("api/Headlines/getInfo")
    Observable<JsonObject> getHeadlineInfo(@Header("token") String token,
                                           @Query("page") int page,
                                           @Query("order") int order,
                                           @Query("id") int id);

    //头条评论点赞
    @POST("api/Headlines_user/commentLikes")
    Observable<JsonObject> doHeadlineCommentLike(@Header("token") String token,
                                                 @Body RequestBody body);

    //头条动态评论
    @POST("api/Headlines_user/comment")
    Observable<JsonObject> doHeadlineComment(@Header("token") String token,
                                             @Body RequestBody body);

    //获取动态评论二级列表
    @POST("api/Headlines/commentList")
    Observable<JsonObject> getHeadlineReplyList(@Header("token") String token,
                                                @Body RequestBody body);

    //头条点赞
    @POST("api/Headlines_user/likes")
    Observable<JsonObject> doHeadlineLike(@Header("token") String token,
                                          @Body RequestBody body);

    //头条收藏
    @POST("api/Headlines_user/favorites")
    Observable<JsonObject> doHeadlineCollect(@Header("token") String token,
                                             @Body RequestBody body);

    //获取头条收藏列表
    @GET("api/Headlines_user/logList")
    Observable<JsonObject> getHeadlineCollectionList(@Header("token") String token,
                                                     @Query("page") int page,
                                                     @Query("uid") int uid,
                                                     @Query("type") int type);

    //获取首页的热门社区
    @GET("api/circle/index")
    Observable<JsonObject> getHotCommunity(@Header("token") String token,
                                           @Query("page") int page);

    //获取首页的社区关注
    @GET("api/Circle/attentionList")
    Observable<JsonObject> getFollowCommunity(@Header("token") String token,
                                              @Query("page") int page);

    //获取圈子分类列表
    @GET("api/circle/classification")
    Observable<JsonObject> getCommunityClassify(@Header("token") String token);

    //社区点赞
    @POST("api/Circleuser/likes")
    Observable<JsonObject> doCommunityLike(@Header("token") String token,
                                           @Body RequestBody body);

    //获取社区详情
    @GET("api/circle/info")
    Observable<JsonObject> getCommunityInfo(@Header("token") String token,
                                            @Query("page") int page,
                                            @Query("id") int id);

    //社区收藏
    @POST("api/Circleuser/favorites")
    Observable<JsonObject> doCommunityCollect(@Header("token") String token,
                                              @Body RequestBody body);

    //获取社区评论二级列表
    @POST("api/Circle/commentList")
    Observable<JsonObject> getCommunityReplyList(@Header("token") String token,
                                                 @Body RequestBody body);

    //社区评论点赞
    @POST("api/Circleuser/commentLikes")
    Observable<JsonObject> doCommunityCommentLike(@Header("token") String token,
                                                  @Body RequestBody body);

    //社区评论
    @POST("api/Circleuser/comment")
    Observable<JsonObject> doCommunityComment(@Header("token") String token,
                                              @Body RequestBody body);

    //获取指定分类的圈子列表
    @GET("api/Circle/designatedCategory")
    Observable<JsonObject> getCommunityDetail(@Header("token") String token,
                                              @Query("page") int page,
                                              @Query("id") int id,
                                              @Query("is_refining") int is_refining,
                                              @Query("is_reply") int is_reply);

    //社区发布帖子
    @POST("api/Circleuser/insert")
    Observable<JsonObject> publishCommunity(@Header("token") String token,
                                            @Body RequestBody body);

    //获取社区收藏列表
    @GET("api/Circleuser/logList")
    Observable<JsonObject> getCommunityCollectionList(@Header("token") String token,
                                                      @Query("page") int page,
                                                      @Query("uid") int uid,
                                                      @Query("type") int type);

    //获取人气排行榜
    @GET("api/Leaderboard/anchor")
    Observable<JsonObject> getPopularRanking(@Header("token") String token,
                                             @Query("type") int type);

    //获取壕气排行榜
    @GET("api/Leaderboard/user")
    Observable<JsonObject> getRichRanking(@Header("token") String token,
                                          @Query("type") int type);

    //获取热门比赛分类
    @GET("api/hot_match/initial_list")
    Observable<JsonObject> getHotMatchClassify();

    //获取首页热门列表
    @GET("api/hot_match/all_hot_match")
    Observable<JsonObject> getHotMatchList(@Header("token") String token,
                                           @Query("type") int type,
                                           @Query("football") String football,
                                           @Query("basketball") String basketball,
                                           @Query("page") int page);

    //搜索比赛
    @GET("api/hot_match/search_match")
    Observable<JsonObject> searchMatch(@Query("search") String content);

    //获取微视频列表
    @GET("api/Video/getList")
    Observable<JsonObject> getVideoList(@Header("token") String token,
                                        @Query("page") int page);

    //获取比赛筛选列表
    @GET("api/Football/match_filter")
    Observable<JsonObject> getMatchFilterList(@Query("type") int match_filter,
                                              @Query("ball") int ball);

    //发布视频
    @POST("api/Video_user/insert")
    Observable<JsonObject> publishVideo(@Header("token") String token,
                                        @Body RequestBody body);

    //观看视频
    @GET("api/Video/addView")
    Observable<JsonObject> doWatch(@Header("token") String token,
                                   @Query("id") int id);

    //视频点赞
    @POST("api/Video_user/likes")
    Observable<JsonObject> doVideoLike(@Header("token") String token,
                                       @Body RequestBody body);

    //获取视频一级评论列表
    @GET("api/Video/commentOneList")
    Observable<JsonObject> getVideoCommentList(@Header("token") String token,
                                               @Query("page") int page,
                                               @Query("id") int id);

    //获取视频评论二级列表
    @POST("api/Video/commentList")
    Observable<JsonObject> getVideoReplyList(@Header("token") String token,
                                             @Body RequestBody body);

    //视频评论点赞
    @POST("api/Video_user/commentLikes")
    Observable<JsonObject> doVideoCommentLike(@Header("token") String token,
                                              @Body RequestBody body);

    //视频评论
    @POST("api/Video_user/comment")
    Observable<JsonObject> doVideoComment(@Header("token") String token,
                                          @Body RequestBody body);

    //视频收藏
    @POST("api/Video_user/favorites")
    Observable<JsonObject> doVideoCollect(@Header("token") String token,
                                          @Body RequestBody body);

    //获取这场比赛正在直播的主播列表
    @GET("api/Live_streaming/getMatchLiveList")
    Observable<JsonObject> getMatchAnchorList(@Header("token") String token,
                                              @Query("id") int id);

    //获取常见问题列表
    @GET("api/Portal_post/getList")
    Observable<JsonObject> getCommonProblemList(@Header("token") String token,
                                                @Query("id") int id);

    //获取首页直播的分类列表
    @GET("api/hot_match/schedule")
    Observable<JsonObject> getLiveMatchList(@Header("token") String token,
                                            @Query("page") int page,
                                            @Query("date") String date,
                                            @Query("match_type") int match_type);

    //收藏赛事
    @POST("api/Basketballuser/collect")
    Observable<JsonObject> doMatchCollect(@Header("token") String token,
                                          @Body RequestBody body);

    //获取消息列表
    @GET("api/Push/getList")
    Observable<JsonObject> getMessageList(@Header("token") String token,
                                          @Query("page") int page);

    //余额变动记录
    @GET("api/Order/getList")
    Observable<JsonObject> getBalanceChangeList(@Header("token") String token,
                                                @Query("type") int type,
                                                @Query("page") int page);

    //提现记录
    @GET("api/Balance/withdrawalList")
    Observable<JsonObject> getWithdrawList(@Header("token") String token,
                                           @Query("page") int page);

    //搜索-主播
    @GET("api/Search/anchor")
    Observable<JsonObject> searchAnchor(@Header("token") String token,
                                        @Query("name") String name);

    //搜索-直播
    @GET("api/Search/live")
    Observable<JsonObject> searchLive(@Header("token") String token,
                                      @Query("name") String name);

    //搜索-头条
    @GET("api/Search/headlines")
    Observable<JsonObject> searchHeadline(@Header("token") String token,
                                          @Query("name") String name);

    //搜索-社区
    @GET("api/Search/circle")
    Observable<JsonObject> searchCommunity(@Header("token") String token,
                                           @Query("name") String name);

    //搜索-综合
    @GET("api/Search/index")
    Observable<JsonObject> searchAll(@Header("token") String token,
                                     @Query("name") String name);

    //获取头条发布列表
    @GET("api/Headlines/userPostList")
    Observable<JsonObject> getHeadlinePublishList(@Header("token") String token,
                                                  @Query("page") int page,
                                                  @Query("uid") int uid,
                                                  @Query("type") int type);

    //删除头条
    @GET("api/Headlines_user/delete")
    Observable<JsonObject> deleteHeadline(@Header("token") String token,
                                          @Query("id") int id);

    //获取社区发帖列表
    @GET("api/Circleuser/userPostList")
    Observable<JsonObject> getCommunityPublishList(@Header("token") String token,
                                                   @Query("page") int page,
                                                   @Query("uid") int uid);

    //获取视频发布列表
    @GET("api/Video/userPostList")
    Observable<JsonObject> getVideoPublishList(@Header("token") String token,
                                               @Query("page") int page,
                                               @Query("uid") int uid);

    //获取视频收藏列表
    @GET("api/Video_user/logList")
    Observable<JsonObject> getVideoCollectList(@Header("token") String token,
                                               @Query("page") int page,
                                               @Query("uid") int uid,
                                               @Query("type") int type);

    //获取任务列表
    @GET("api/Activity_Config/user_task_list")
    Observable<JsonObject> getTaskList(@Header("token") String token,
                                       @Query("type") int type);

    //获取官方活动列表
    @GET("api/Activity_Config/getActivityUrl")
    Observable<JsonObject> getActivityList(@Header("token") String token,
                                           @Query("page") int page);

    //完成红包任务
    @GET("api/Activity/task_handle")
    Observable<JsonObject> doTask(@Header("token") String token,
                                  @Query("id") int id,
                                  @Query("uid") int uid);

    //获取宝箱列表
    @GET("api/Activity_Config/index")
    Observable<JsonObject> getBoxList(@Header("token") String token);

    //宝箱计时结束处理
    @GET("api/Activity/box_time_over")
    Observable<JsonObject> doBoxTimeOver(@Header("token") String token,
                                         @Query("id") int id);

    //开启宝箱
    @GET("api/Activity/open_box")
    Observable<JsonObject> openBox(@Header("token") String token,
                                   @Query("id") int id);

    //获取举报列表
    @GET("api/Report/classifyList")
    Observable<JsonObject> getReportList(@Header("token") String token);

    //添加举报
    @GET("api/Reportuser/insert")
    Observable<JsonObject> doReport(@Header("token") String token,
                                    @Query("classify_id") int classify_id,
                                    @Query("video_id") int video_id,
                                    @Query("type") int type,
                                    @Query("type2") int type2);

    //获取用户可提现余额
    @GET("api/Member/getWithdrawalBalance")
    Observable<JsonObject> getWithdrawalBalance(@Header("token") String token);

    //申请提现
    @GET("api/Balance/applyForWithdrawal")
    Observable<JsonObject> applyWithdrawal(@Header("token") String token,
                                           @Query("amount") int amount,
                                           @Query("code") String code,
                                           @Query("type") String type,
                                           @Query("pass") String pass);

    //获取红包列表
    @GET("api/Red_envelope/getList")
    Observable<JsonObject> getRedEnvelopeList(@Header("token") String token,
                                              @Query("id") int id);

    //领取红包
    @GET("api/Red_envelope/receive")
    Observable<JsonObject> receiveRedEnvelope(@Header("token") String token,
                                              @Query("id") int id);

    //领取红包
    @POST("api/Red_envelope/insert")
    Observable<JsonObject> addRedEnvelope(@Header("token") String token,
                                          @Body RequestBody body);

    //获取默认头像列表
    @GET("api/user/avatarList")
    Observable<JsonObject> getDefaultAvatarList(@Header("token") String token);

    //获取数据库默认关注列表
    @GET("api/football_database/popular")
    Observable<JsonObject> getDataDefaultFollowList();

    //获取赛事分类
    @GET("api/Basketball_database/getCategory")
    Observable<JsonObject> getMatchDataClassify();

    //获取国家
    @GET("api/Basketball_database/getCountry")
    Observable<JsonObject> getMatchDataCountry(@Query("id") int id);

    //获取篮球赛事列表
    @GET("api/Basketball_database/tournamentList")
    Observable<JsonObject> getBasketBallMatchDataList(@Query("country_id") int country_id,
                                            @Query("category_id") int category_id);

    //获取篮球赛事列表
    @GET("api/Football_database/tournamentList")
    Observable<JsonObject> getFootBallMatchDataList(@Query("country_id") int country_id,
                                            @Query("category_id") int category_id);

    //获取篮球赛事详情
    @GET("api/Basketball_database/competitionInfo")
    Observable<JsonObject> getBasketballMatchDataDetail(@Query("id") int id,
                                                        @Query("page") int page,
                                                        @Query("season_id") int season_id,
                                                        @Query("type") int type);

    //获取篮球赛事排名
    @GET("api/Basketball_database/pointsRanking")
    Observable<JsonObject> getBasketballMatchDataRanking(@Query("id") int id);

    //获取篮球球队列表
    @GET("api/Basketball_database/TeamData")
    Observable<JsonObject> getBasketballMatchDataBestTeam(@Query("id") int id,
                                                          @Query("type") int type);

    //获取篮球球员列表
    @GET("api/Basketball_database/teamMember")
    Observable<JsonObject> getBasketballMatchDataBestMember(@Query("id") int id,
                                                          @Query("type") int type);

    //获取足球赛事详情
    @GET("api/football_database/competitionInfo")
    Observable<JsonObject> getFootballMatchDataDetail(@Query("id") int id,
                                                      @Query("season_id") int season_id,
                                                      @Query("stage_id") int stage_id,
                                                      @Query("son_id") int son_id);

    //获取足球赛事阶段列表
    @GET("api/Football_database/stage")
    Observable<JsonObject> getFootballMatchDataStage(@Query("id") int id);

    //获取足球赛程积分
    @GET("api/Football_database/integral")
    Observable<JsonObject> getFootballMatchDataRanking(@Query("season_id") int season_id,
                                                     @Query("integral_type") int integral_type);

    //获取足球球队列表
    @GET("api/Football_database/TeamData")
    Observable<JsonObject> getFootballMatchDataBestTeam(@Query("id") int id,
                                                          @Query("type") int type);

    //获取足球球员列表
    @GET("api/Football_database/teamMember")
    Observable<JsonObject> getFootballMatchDataBestMember(@Query("id") int id,
                                                            @Query("type") int type);

    //获取轮播图
    @GET("api/banner/getBannerList")
    Observable<JsonObject> getBannerList(@Query("banner_type") int type);

    //获取公开赛列表
    @POST("api/Cricket/get_cricket_tournament")
    Observable<JsonObject> getTournamentList();

    //获取赛事列表
    @POST("api/Cricket/Cricket_match")
    Observable<JsonObject> getCricketMatchList(@Body RequestBody body);

    //获取公开赛赛事列表
    @POST("api/Cricket/get_cricket_tournament_match")
    Observable<JsonObject> getCricketTournamentMatchList(@Body RequestBody body);

    //获取团队球员列表
    @POST("api/Cricket/get_tournament_team_list")
    Observable<JsonObject> getTournamentTeamPlayerList(@Body RequestBody body);

    //获取公开赛队伍列表
    @POST("api/Cricket/get_tournament_team")
    Observable<JsonObject> getTournamentTeamList(@Body RequestBody body);

    //获取公开赛积分表
    @POST("api/Cricket/cricket_tournament_standings")
    Observable<JsonObject> getTournamentPointsList(@Body RequestBody body);

    //获取公开赛统计数据
    @POST("api/Cricket/get_tournament_stats")
    Observable<JsonObject> getTournamentStats(@Body RequestBody body);

    //获取公开赛统计数据列表
    @POST("api/Cricket/get_tournament_stats_list")
    Observable<JsonObject> getTournamentStatsList(@Body RequestBody body);

    //获取赛事详情
    @POST("api/Cricket/cricket_match_detail")
    Observable<JsonObject> getCricketDetail(@Body RequestBody body);

    //获取赛事详情-info
    @POST("api/Cricket/cricket_match_detail_info")
    Observable<JsonObject> getCricketDetailInfo(@Body RequestBody body);

    //获取赛事详情-live
    @POST("api/Cricket/cricket_match_detail_live")
    Observable<JsonObject> getCricketDetailLive(@Body RequestBody body);

    //获取赛事详情-squad
    @POST("api/Cricket/cricket_match_detail_squad")
    Observable<JsonObject> getCricketDetailSquad(@Body RequestBody body);

    //获取赛事详情-fantasy
    @POST("api/Cricket/cricket_match_detail_fantasy")
    Observable<JsonObject> getCricketDetailFantasy(@Body RequestBody body);

    //获取赛事详情-fantasyInfo
    @POST("api/Cricket/cricket_match_detail_fantasy_info")
    Observable<JsonObject> getCricketDetailFantasyInfo(@Body RequestBody body);

    //获取赛事详情-updates
    @POST("api/Cricket/cricket_match_detail_updates")
    Observable<JsonObject> getCricketDetailUpdates(@Body RequestBody body);

    //获取球员信息
    @POST("api/CricketPlayer/cricket_player_index")
    Observable<JsonObject> getPlayerProfile(@Body RequestBody body);

    //获取球员信息-bio
    @POST("api/CricketPlayer/cricket_player_bio")
    Observable<JsonObject> getPlayerProfileBio(@Body RequestBody body);

    //获取球员信息-career
    @POST("api/CricketPlayer/get_player_career")
    Observable<JsonObject> getPlayerProfileCareer(@Body RequestBody body);

    //获取赛事详情-scorecard
    @POST("api/Cricket/cricket_match_detail_scorecard")
    Observable<JsonObject> getMatchScorecard(@Body RequestBody body);
}
