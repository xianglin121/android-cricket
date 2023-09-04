package com.onecric.live.model;

import java.util.List;

public class LiveVideoBean {
    public String name;
    public List<LBean> list;

    public static class LBean {
        public Integer heat;
        public String awayName;
        public Integer isHome;
        public String week;
        public Integer deletetime;
        public String thumb;
        public String tournament;
        public Integer starttime;
        public Integer type;
        public String title;
        public Integer uid;
        public Integer isrecommend;
        public String stream;
        public String homeName;
        public String userNickname;
        public int id;
        public Integer ishot;
        public Integer live;
        public Integer islive;
        public String awayLogo;
        public String votestotal;
        public String bottom;
        public Integer matchId;
        public String avatar;
        public Integer startLike;
        public String push;
        public int hotvotes;
        public String pull;
        public int viewers;
        public int liveId;
        public String votestotalIcon;
        public ClarityDTO clarity;
        public String liveDuration;
        public Integer addtime;
        public String homeLogo;
        public Integer clicktime;
        public Integer updatetime;

        public static class ClarityDTO {
            public String sd;
            public String hd;
            public String smooth;
        }
    }



}
