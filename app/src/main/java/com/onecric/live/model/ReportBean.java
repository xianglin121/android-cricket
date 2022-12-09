package com.onecric.live.model;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/17
 */
public class ReportBean {

    /**
     * id : 5
     * addtime : 1641177691
     * name : 测试
     * content : 1112
     * type : 1
     * status : 1
     */

    private int id;
    private int addtime;
    private String name;
    private String content;
    private int type;
    private int status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
