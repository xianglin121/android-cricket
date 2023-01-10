package com.onecric.live.model;

public class HistoryLiveBean {

    /**
     * {"MediaUrl":"http://1309782813.vod2.myqcloud.com/f0c263e2vodsgp1309782813/4da2a852243791577628562003/playlist.m3u8",
     * "Rotate":"0",
     * "Size":"53564135",
     * "CreateTime":"2022-12-27T06:55:18Z",
     * "Duration":"160.566",
     * "Bitrate":"2668765",
     * "Name":"1672123920_2022-12-27_2022-12-27-14-52-37_2022-12-27-14-55-16",
     * "Container":"hls",
     * "Type":"m3u8",
     * "VideoDuration":"160.566",
     * "ClassName":"其他",
     * "FileId":"243791577628562003",
     * "Height":"720"}
     * Heat : 0
     * UserHead :”“
     * UserName :”“
     */
    private int Heat;
    private String UserHead;
    private String UserName;
    private String mediaUrl;
    private String rotate;
    private String size;
    private String createTime;
    private String duration;
    private String bitrate;
    private String name;
    private String container;
    private String type;
    private String videoDuration;
    private String className;
    private String fileId;
    private String height;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;

    public int getHeat() {
        return Heat;
    }

    public void setHeat(int heat) {
        Heat = heat;
    }

    public String getUserHead() {
        return UserHead;
    }

    public void setUserHead(String userHead) {
        UserHead = userHead;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
