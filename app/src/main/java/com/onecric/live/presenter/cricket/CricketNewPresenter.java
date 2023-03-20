package com.onecric.live.presenter.cricket;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.model.CricketAllBean;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.presenter.BasePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.CricketNewView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public void getCricketMatchListOld(int type,String data,String tagIds,int streamType,boolean isLiveNow) {
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
                }
                JSONObject.parseObject(data).getString("data");
//                List<CricketDayBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), CricketDayBean.class);
                mvpView.getDataSuccess(type,null,"","");*/
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

    public void getCricketMatchList(int type,String data,String tagIds,int streamType,boolean isLiveNow) {
        if(TextUtils.isEmpty(data)){
            ToastUtil.show("No data yet");
            return;
        }

        addSubscription(apiStores.getCricketDayMatchList(CommonAppConfig.getInstance().getToken(), TimeZone.getDefault().getID(),data,tagIds,streamType,isLiveNow?1:0), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                CricketAllBean bean;
                try{
                    bean = JSONObject.parseObject(JSONObject.parseObject(data).toString(), CricketAllBean.class);
                }catch (Exception e){
                    bean = null;
                }
                mvpView.getDataSuccess(type,bean);
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

    public void getRefreshTodayData(String tagIds,int streamType,boolean isLiveNow){
        addSubscription(apiStores.getCricketDayMatchList(CommonAppConfig.getInstance().getToken(), TimeZone.getDefault().getID(),new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime()),tagIds,streamType,isLiveNow?1:0), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                CricketAllBean bean;
                try{
                    bean = JSONObject.parseObject(JSONObject.parseObject(data).toString(), CricketAllBean.class);
                }catch (Exception e){
                    bean = null;
                }
                mvpView.getRefreshSuccess(bean);
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
}
