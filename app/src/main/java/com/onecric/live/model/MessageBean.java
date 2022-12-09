package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/4
 */
public class MessageBean {
    /**
     * id : 2
     * addtime : 1641267292
     * content : 1111
     * addtimeStr : 2022-01-04 11:34
     */

    private int id;
    private int addtime;
    private String content;
    private String addtimeStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddtime() {
        return addtime;
    }

    public void setAddtime(int addtime) {
        this.addtime = addtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddtimeStr() {
        return addtimeStr;
    }

    public void setAddtimeStr(String addtimeStr) {
        this.addtimeStr = addtimeStr;
    }
}
