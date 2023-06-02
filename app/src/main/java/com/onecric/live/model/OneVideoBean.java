package com.onecric.live.model;

import java.io.Serializable;
import java.util.List;

public class OneVideoBean implements Serializable {

    private List<ShortVideoBean> shows;
    private List<FirstCategoryBean> list;
    private List<SecondCategoryBean> others;
    private BannerBean banner;
    public static class BannerBean{
        public Integer favorites;
        public Integer commentCount;
        public String source;
        public String title;
        public Integer click;
        public Integer isTop;
        public Integer uid;
        public List<FlieBean> flie;
        public Integer addtime;
        public Integer id;
        public String sourceId;
        public Integer likes;
        public Integer status;
    }

    public static class FirstCategoryBean {
        public String name;
        public List<SecondCategoryBean> list;
    }

    public static class SecondCategoryBean {
        public Integer favorites;
        public Integer commentCount;
        public String source;
        public String title;
        public Integer click;
        public int uid;
        public List<FlieBean> flie;
        public String addtime;
        public int id;
        public String sourceId;
        public Integer likes;
        public Integer status;
        public String username;

        public SecondCategoryBean(){}
    }

    public static class FlieBean {
        public String img;
        public String video;
    }


}
