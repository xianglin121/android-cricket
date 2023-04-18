package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/20
 */
public class LiveBean {

    /**
     * id : 2
     * uid : 2
     * islive : 2
     * type : 1
     * viewers : 0
     * live_duration : null
     * clicktime : 1636768664
     * match_id : 3596072
     * starttime : 1636707446
     * title : 测试
     * stream : 2_1636707446
     * thumb : http://baidu.com
     * pull : http://livepull.mhuan.shop/live/2_1636707446.m3u8
     * hotvotes : 0
     * ishot : 0
     * isrecommend : 0
     * is_home : 0
     * deletetime : 0
     * avatar :
     * user_nickname :
     * heat : 0
     */

    private int id;
    private int uid;
    private int islive;
    private int type;
    private int viewers;
    private Object live_duration;
    private int clicktime;
    private int match_id;
    private int starttime;
    private String title;
    private String stream;
    private String thumb;
    private String pull;
    private int hotvotes;
    private int ishot;
    private int isrecommend;
    private int is_home;
    private int deletetime;
    private String avatar;
    private String user_nickname;
    private int heat;
    private int live_id;

    public String away_logo;
    public String bottom;
    public String home_logo;

    public int getLive_id() {
        return live_id;
    }

    public void setLive_id(int live_id) {
        this.live_id = live_id;
    }

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

    public int getIslive() {
        return islive;
    }

    public void setIslive(int islive) {
        this.islive = islive;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getViewers() {
        return viewers;
    }

    public void setViewers(int viewers) {
        this.viewers = viewers;
    }

    public Object getLive_duration() {
        return live_duration;
    }

    public void setLive_duration(Object live_duration) {
        this.live_duration = live_duration;
    }

    public int getClicktime() {
        return clicktime;
    }

    public void setClicktime(int clicktime) {
        this.clicktime = clicktime;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPull() {
        return pull;
    }

    public void setPull(String pull) {
        this.pull = pull;
    }

    public int getHotvotes() {
        return hotvotes;
    }

    public void setHotvotes(int hotvotes) {
        this.hotvotes = hotvotes;
    }

    public int getIshot() {
        return ishot;
    }

    public void setIshot(int ishot) {
        this.ishot = ishot;
    }

    public int getIsrecommend() {
        return isrecommend;
    }

    public void setIsrecommend(int isrecommend) {
        this.isrecommend = isrecommend;
    }

    public int getIs_home() {
        return is_home;
    }

    public void setIs_home(int is_home) {
        this.is_home = is_home;
    }

    public int getDeletetime() {
        return deletetime;
    }

    public void setDeletetime(int deletetime) {
        this.deletetime = deletetime;
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

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }
}
