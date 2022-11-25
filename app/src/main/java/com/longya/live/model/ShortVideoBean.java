package com.longya.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/28
 */
public class ShortVideoBean {

    /**
     * id : 1
     * uid : 2
     * title : 测试
     * click : 5
     * likes : 1
     * comment_count : 2
     * favorites : 1
     * addtime : 2021-12-22 11:35:24
     * avatar :
     * user_nickname : 手机用户0152
     * like : 1
     * is_flie_type : 1
     * is_attention : 0
     * is_likes : 0
     * is_comment : 0
     * is_favorites : 0
     */

    private int id;
    private int uid;
    private String title;
    private int click;
    private int likes;
    private int comment_count;
    private int favorites;
    private String addtime;
    private String avatar;
    private String user_nickname;
    private int like;
    private int is_flie_type;
    private int is_attention;
    private int is_likes;
    private int is_comment;
    private int is_favorites;
    private List<VideoBean> video;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getIs_flie_type() {
        return is_flie_type;
    }

    public void setIs_flie_type(int is_flie_type) {
        this.is_flie_type = is_flie_type;
    }

    public int getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }

    public int getIs_likes() {
        return is_likes;
    }

    public void setIs_likes(int is_likes) {
        this.is_likes = is_likes;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    public int getIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(int is_favorites) {
        this.is_favorites = is_favorites;
    }

    public List<VideoBean> getVideo() {
        return video;
    }

    public void setVideo(List<VideoBean> video) {
        this.video = video;
    }
}
