package com.onecric.live.model;

import java.util.List;

public class AreasModel {
    private List<CountryModel> data;

    public void setData(List<CountryModel> data) {
        this.data = data;
    }

    public List<CountryModel> getData() {
        return this.data;
    }

    public class CountryModel {
        private String name; //名字
        private String shortName; //首字母
        private String tel; //编号
        private String en;
        private String pinyin;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }
    }
}
