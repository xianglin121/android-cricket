package com.onecric.live.model;

import android.os.CountDownTimer;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PlayCardsBean implements MultiItemEntity {
    public static final int LIVE_ITEM_TYPE_HEAD = 1;
    public static final int LIVE_ITEM_TYPE_NARRATE = 2;

    public String awayName;
    public String homeDisplayScore;
    public String scheduled;
    public String startTimeTbd;
    public Double homeWinProbabilities;
    public Integer channel;
    public String createdAt;
    public Integer starttime;
    public String tournament;
    public String tournamentId;
    public Integer awayId;
    public String matchNum;
    public String updatedAt;
    public String awayDisplayScore;
    public String homeName;
    public String matchResult;
    public Integer homeId;
    public Integer id;
    public Integer liveStatus;
    public String awayLogo;
    public String liveTime;
    public Integer seasonId;
    public String todaystart;
    public String matchStatus;
    public Double awayWinProbabilities;
    public Integer liveId;
    public String homeLogo;
    public Integer matchDays;
    public Integer liveUid;
//    public Integer liveTimeUnix;
    public Integer matchDay;
    public Integer isSubscribe;
    public int status;

    public int type = 2;
    public int winId;
    public int fastStatus;
    public String thumb;

    public CountDownTimer countDownTimer;
    @Override
    public int getItemType() {
        return type;
    }
}
