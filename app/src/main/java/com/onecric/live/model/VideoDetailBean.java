package com.onecric.live.model;

import java.util.List;

public class VideoDetailBean {
    public Integer favorites;
    public Integer commentCount;
    public String source;
    public String title;
    public Integer click;
    public Integer uid;
    public List<FlieDTO> flie;
    public String addtime;
    public Integer id;
    public String sourceId;
    public Integer likes;
    public Integer status;

    public static class FlieDTO {
        public String img;
        public String video;
    }
}
