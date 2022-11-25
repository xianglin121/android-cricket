package com.longya.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/24
 */
public class CommunityBean {

    /**
     * id : 56
     * title : 测试
     * click : 1
     * likes : 0
     * like : 0
     * comment : 0
     * favorites : 1
     * addtime : 3天前
     * avatar :
     * user_nickname :
     * classification_name : 足球圈
     * classification_icon : null
     * classification_id : 1
     * is_flie_type : 1
     * is_likes : 0
     * is_comment : 1
     * is_favorites : 0
     */

    private int id;
    private int circle_id;
    private int uid;
    private String title;
    private String content;
    private int click;
    private int likes;
    private int like;
    private int comment;
    private int favorites;
    private String addtime;
    private String addtimeString;
    private String avatar;
    private String user_nickname;
    private String classification_name;
    private String classification_icon;
    private int classification_id;
    private int is_flie_type;
    private int is_likes;
    private int is_comment;
    private int is_attention;
    private int is_favorites;
    private List<String> img;
    private List<VideoBean> video;
    private int comment_likes;
    private int is_comment_likes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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

    public String getAddtimeString() {
        return addtimeString;
    }

    public void setAddtimeString(String addtimeString) {
        this.addtimeString = addtimeString;
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

    public String getClassification_name() {
        return classification_name;
    }

    public void setClassification_name(String classification_name) {
        this.classification_name = classification_name;
    }

    public String getClassification_icon() {
        return classification_icon;
    }

    public void setClassification_icon(String classification_icon) {
        this.classification_icon = classification_icon;
    }

    public int getClassification_id() {
        return classification_id;
    }

    public void setClassification_id(int classification_id) {
        this.classification_id = classification_id;
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

    public int getIs_favorites() {
        return is_favorites;
    }

    public void setIs_favorites(int is_favorites) {
        this.is_favorites = is_favorites;
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

    public int getComment_likes() {
        return comment_likes;
    }

    public void setComment_likes(int comment_likes) {
        this.comment_likes = comment_likes;
    }

    public int getIs_comment_likes() {
        return is_comment_likes;
    }

    public void setIs_comment_likes(int is_comment_likes) {
        this.is_comment_likes = is_comment_likes;
    }
}
