package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/17
 */
public class MovingBean {

    /**
     * id : 56
     * title : 测试
     * click : 0
     * like : 0
     * comment : 0
     * favorites : 1
     * addtime : 8小时前
     * avatar :
     * user_nickname :
     * is_flie_type : 1
     * is_likes : 0
     * is_comment : 1
     */

    private int id;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private int uid;
    private String title;
    private String content;
    private int click;
    private int like;
    private int comment;
    private int favorites;
    private String addtime;
    private String avatar;
    private String user_nickname;
    private int is_flie_type;
    private int is_likes;
    private int is_comment;
    private int is_attention;
    private List<String> img;
    private List<VideoBean> video;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
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

    public int getIs_flie_type() {
        return is_flie_type;
    }

    public void setIs_flie_type(int is_flie_type) {
        this.is_flie_type = is_flie_type;
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

    public int getIs_attention() {
        return is_attention;
    }

    public void setIs_attention(int is_attention) {
        this.is_attention = is_attention;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public List<VideoBean> getVideo() {
        return video;
    }

    public void setVideo(List<VideoBean> video) {
        this.video = video;
    }
}
