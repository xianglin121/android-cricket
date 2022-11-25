package com.longya.live.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/12/18
 */
public class SpeakerBean {

    /**
     * id : 1
     * uid : 2
     * content : 123
     */

    private int id;
    private int uid;
    private String content;
    private JSONObject addtime;
    private JSONObject status;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public JSONObject getAddtime() {
        return addtime;
    }

    public void setAddtime(JSONObject addtime) {
        this.addtime = addtime;
    }

    public JSONObject getStatus() {
        return status;
    }

    public void setStatus(JSONObject status) {
        this.status = status;
    }
}
