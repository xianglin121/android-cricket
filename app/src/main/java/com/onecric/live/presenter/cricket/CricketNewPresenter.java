package com.onecric.live.presenter.cricket;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.model.CricketDayBean;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.model.CricketNewBean;
import com.onecric.live.model.CricketTournamentBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.CricketNewView;
import com.onecric.live.view.cricket.CricketView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CricketNewPresenter extends BasePresenter<CricketNewView> {
    public CricketNewPresenter(CricketNewView view) {
        attachView(view);
    }

    public void getFiltrateList() {
        addSubscription(apiStores.getFiltrateList(), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                List<CricketFiltrateBean> list = JSONObject.parseArray(data, CricketFiltrateBean.class);
                mvpView.getDataSuccess(list);
            }

            @Override
            public void onFailure(String msg) {
            }

            @Override
            public void onError(String msg) {
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void getCricketMatchList(int type,String data,String tagIds,int streamType,boolean isLiveNow) {
/*        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        if(type == 0){
            c.add(Calendar.DATE, -page);
        }else if(type == 2){
            c.add(Calendar.DATE, page);
        }
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());*/

        if(TextUtils.isEmpty(data)){
            ToastUtil.show("No data yet");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = null;
        try {
            dateStr = new SimpleDateFormat("yyyy-MM-dd").format(sdf.parse(data).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String finalDateStr = dateStr;
        addSubscription(apiStores.getCricketNewMatchList(CommonAppConfig.getInstance().getToken(), TimeZone.getDefault().getID(),dateStr,tagIds,streamType,isLiveNow?1:0), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
/*                String endDay = JSONObject.parseObject(data).getString("end_day");
                String lastDay = JSONObject.parseObject(data).getString("front_date");
                List<CricketNewBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), CricketNewBean.class);
                for(CricketNewBean bean : list){
                    bean.date = finalDateStr;
                    bean.lastDay = lastDay;
                    bean.endDay = endDay;
                }*/
                JSONObject.parseObject(data).getString("data");
//                List<CricketDayBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), CricketDayBean.class);
                mvpView.getDataSuccess(type,null,"","");
            }

            @Override
            public void onFailure(String msg) {
                mvpView.getDataFail(type,msg);
            }

            @Override
            public void onError(String msg) {
                mvpView.getDataFail(type,msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
