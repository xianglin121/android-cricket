package com.onecric.live.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class CricketLiveBean implements MultiItemEntity {


    public static final int LIVE_ITEM_TYPE_START = 0;
    public static final int LIVE_ITEM_TYPE_NARRATE = 1;
    public static final int LIVE_ITEM_TYPE_HEAD = 2;
    public static final int LIVE_ITEM_TYPE_END = 3;

    public Integer id;
    public Integer type;
    public Integer page;
    public String commentaries;
    public String serial_number;
    public String display_overs;
    public Integer runs_scored;
    public HeadDTO head;

    @Override
    public int getItemType() {
        return type;
    }

    public static class HeadDTO {
        public String sessions_number;
        public String display_score;
        public Integer sum;
        public String country_code;
        public String ball;
        public StrikerDTO striker;
        public NonStrikerDTO non_striker;

        public static class StrikerDTO {
            public String id;
            public String name;
            public String country_code;
            public Integer runs_scored_so_far;
            public Integer balls_faced_so_far;
            public Integer fours;
            public Integer sixes;
        }

        public static class NonStrikerDTO {
            public String id;
            public String name;
            public String country_code;
            public Integer runs_scored_so_far;
            public Integer balls_faced_so_far;
            public Integer fours;
            public Integer sixes;
        }
    }

//    public Integer id;
//    public Integer type;
//    public Integer page;
//    public HeadDTO head;
//    public String commentaries;
//    public String serial_number;
//    public Integer runs_scored;
//
//    @Override
//    public int getItemType() {
//        return type;
//    }
//
//    public static class HeadDTO {
//        public String sessions_number;
//        public String display_score;
//        public Integer sum;
//        public String country_code;
//        public String ball;
//        public StrikerDTO striker;
//        public NonStrikerDTO non_striker;
//
//        public static class StrikerDTO {
//            public String id;
//            public String name;
//            public String country_code;
//            public Integer runs_scored_so_far;
//            public Integer balls_faced_so_far;
//            public Integer fours;
//            public Integer sixes;
//        }
//
//        public static class NonStrikerDTO {
//            public String id;
//            public String name;
//            public String country_code;
//            public Integer runs_scored_so_far;
//            public Integer balls_faced_so_far;
//            public Integer fours;
//            public Integer sixes;
//        }
//    }


}
