package com.onecric.live.model;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/11
 */
public class TaskBean {

    /**
     * id : 1
     * task : 邀请好友注册
     * status : 0
     * type : 0
     * now_value : 0
     * value : 1
     * lz_num : 51
     * withdraw : 1
     * finish_status : 0
     */

    private int id;
    private String task;
    private int status;
    private int type;
    private int now_value;
    private String value;
    private int lz_num;
    private int withdraw;
    private int finish_status;
    private LiveBean anchor;
    private List<TaskInnerBean> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNow_value() {
        return now_value;
    }

    public void setNow_value(int now_value) {
        this.now_value = now_value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLz_num() {
        return lz_num;
    }

    public void setLz_num(int lz_num) {
        this.lz_num = lz_num;
    }

    public int getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(int withdraw) {
        this.withdraw = withdraw;
    }

    public int getFinish_status() {
        return finish_status;
    }

    public void setFinish_status(int finish_status) {
        this.finish_status = finish_status;
    }

    public LiveBean getAnchor() {
        return anchor;
    }

    public void setAnchor(LiveBean anchor) {
        this.anchor = anchor;
    }

    public List<TaskInnerBean> getList() {
        return list;
    }

    public void setList(List<TaskInnerBean> list) {
        this.list = list;
    }
}
