package com.onecric.live.adapter;

import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.R;
import com.onecric.live.activity.LoginActivity;
import com.onecric.live.activity.MainActivity;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.BannerBean;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.SubscribeTypeBean;
import com.onecric.live.presenter.match.SubscribePresenter;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.util.DialogUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.TimeUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;

import java.util.Date;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class CricketInnerAdapter extends BaseQuickAdapter<CricketMatchBean, BaseViewHolder> {
    MainActivity mainActivity;

    public CricketInnerAdapter(MainActivity mainActivity, int layoutResId, @Nullable List<CricketMatchBean> data) {
        super(layoutResId, data);
        this.mainActivity = mainActivity;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketMatchBean item) {
        if (helper.getLayoutPosition() == getItemCount() - 1) {
            helper.getView(R.id.line).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        }

        helper.setTextColor(R.id.tv_time, mContext.getResources().getColor(R.color.c_901D2550));
        TextView resultTv = helper.getView(R.id.tv_result);
        ImageView subscribeIv = helper.getView(R.id.iv_subscribe);
        helper.getView(R.id.ll_alarm).setVisibility(View.GONE);
        helper.getView(R.id.tv_live).setVisibility(View.GONE);
        if (item.getStatus() == 2) {//已结束
            subscribeIv.setVisibility(View.GONE);
            resultTv.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_display_bold));
        } else {
            //先判断是否登陆了账号
//            if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            subscribeIv.setVisibility(View.VISIBLE);// TODO: 2023/2/15  这里在订阅接口调试好后要放开为visible
            if (item.getIs_subscribe() == 1) {//已经订阅过了
                subscribeIv.setImageResource(R.mipmap.subscribe);
            } else {
                subscribeIv.setImageResource(R.mipmap.unsubscribe);
            }
//            }
//            else {
//                subscribeIv.setVisibility(View.GONE);
//            }
            subscribeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                        ToastUtil.show(mContext.getString(R.string.please_login));
                        if (mainActivity.loginDialog != null) {
                            mainActivity.loginDialog.show();
                        } else {
                            mainActivity.newLoginDialog();
                        }
                        return;
                    }
                    getSubscribeType(item, subscribeIv);
                    // TODO: 2023/2/14  订阅消息推送
//                    //这里先弹出一个订阅消息的内容选择框  待选择好后点击确定订阅按钮再调用订阅接口
//                    DialogUtil.showSelectSubscribeDialog(mContext, item.getHome_name() + " VS " + item.getAway_name(), new DialogUtil.SelectSubscribeBack() {
//                        @Override
//                        public void onSelectSubscribe(String type) {
//                            doSubscribe(item.getMatch_id() + "", , subscribeIv);
//                        }
//                    });
                }
            });
            resultTv.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_display_regular));
            if (item.getStatus() == 0) {//未开始
                helper.getView(R.id.iv_alarm).setVisibility(View.GONE);
                if (!TextUtils.isEmpty(item.getLive_time())) {
                    helper.setText(R.id.tv_time, item.getLive_time());
                    helper.getView(R.id.ll_alarm).setVisibility(View.VISIBLE);
                    TextView tv_time = helper.getView(R.id.tv_time);
                    //转时间戳 得到倒计时毫秒数
                    long time = DateTimeUtil.getStringToDate(item.getScheduled(), "yyyy-MM-dd HH:mm:ss");
                    long countTime = time - new Date().getTime();
                    if (countTime > 0) {
                        //开始倒计时
                        new CountDownTimer(countTime, 1000) {
                            public void onTick(long millisUntilFinished) {
                                tv_time.setText(TimeUtil.timeConversion(millisUntilFinished / 1000));
                            }

                            public void onFinish() {
                                item.setStatus(1);
                                notifyItemChanged(helper.getLayoutPosition());
                            }
                        }.start();
                    }
                } else {
                    helper.setText(R.id.tv_time, "");
                    helper.getView(R.id.ll_alarm).setVisibility(View.GONE);
                }
            } else {//已开始
                resultTv.setTypeface(ResourcesCompat.getFont(mContext, R.font.noto_sans_display_semibold));
                if (item.getLive_id() != 0) {
                    helper.getView(R.id.tv_live).setVisibility(View.VISIBLE);
                }
            }
        }


        if (!TextUtils.isEmpty(item.getMatch_num())) {
            helper.setText(R.id.tv_date, item.getMatch_num());
        } else {
            helper.setText(R.id.tv_date, "");
        }
        ImageView iv_home_logo = helper.getView(R.id.iv_home_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getHome_logo(), iv_home_logo);
        if (!TextUtils.isEmpty(item.getHome_name())) {
            helper.setText(R.id.tv_home_name, item.getHome_name());
        } else {
            helper.setText(R.id.tv_home_name, "");
        }
        if (!TextUtils.isEmpty(item.getHome_display_score())) {
            if (item.getHome_display_score().contains(" ")) {
                String[] split = item.getHome_display_score().split(" ");
                helper.setText(R.id.tv_home_score, split[0]);
                helper.setText(R.id.tv_home_score2, " " + split[1]);
            } else {
                helper.setText(R.id.tv_home_score, item.getHome_display_score());
            }
        } else {
            helper.setText(R.id.tv_home_score, "");
            helper.setText(R.id.tv_home_score2, " ");
        }
        ImageView iv_away_logo = helper.getView(R.id.iv_away_logo);
        GlideUtil.loadTeamImageDefault(mContext, item.getAway_logo(), iv_away_logo);
        if (!TextUtils.isEmpty(item.getAway_name())) {
            helper.setText(R.id.tv_away_name, item.getAway_name());
        } else {
            helper.setText(R.id.tv_away_name, "");
        }
        if (!TextUtils.isEmpty(item.getAway_display_score())) {
            if (item.getAway_display_score().contains(" ")) {
                String[] split = item.getAway_display_score().split(" ");
                helper.setText(R.id.tv_away_score, split[0]);
                helper.setText(R.id.tv_away_score2, " " + split[1]);
            } else {
                helper.setText(R.id.tv_away_score, item.getAway_display_score());
            }
        } else {
            helper.setText(R.id.tv_away_score, "");
            helper.setText(R.id.tv_away_score2, "");
        }
        if (!TextUtils.isEmpty(item.getMatch_result())) {
            helper.setText(R.id.tv_result, item.getMatch_result());
        } else {
            helper.setText(R.id.tv_result, "");
        }

//        if (item.getHome_id() == item.getWinner_id()) {
//            helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_333333));
//            helper.setTextColor(R.id.tv_home_score2, mContext.getResources().getColor(R.color.c_333333));
//            helper.setTextColor(R.id.tv_away_score, mContext.getResources().getColor(R.color.c_666666));
//            helper.setTextColor(R.id.tv_away_score2, mContext.getResources().getColor(R.color.c_666666));
//
//        } else {
//            helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_666666));
//            helper.setTextColor(R.id.tv_home_score2, mContext.getResources().getColor(R.color.c_666666));
//            helper.setTextColor(R.id.tv_away_score, mContext.getResources().getColor(R.color.c_333333));
//            helper.setTextColor(R.id.tv_away_score2, mContext.getResources().getColor(R.color.c_333333));
//        }
        if (item.getStatus() == 2) {
            helper.setTextColor(R.id.tv_result, mContext.getResources().getColor(R.color.c_ff5000));
            if (item.getHome_id() == item.getWinner_id()) {
                helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_333333));
                helper.setTextColor(R.id.tv_home_score2, mContext.getResources().getColor(R.color.c_333333));
                helper.setTextColor(R.id.tv_away_score, mContext.getResources().getColor(R.color.c_666666));
                helper.setTextColor(R.id.tv_away_score2, mContext.getResources().getColor(R.color.c_666666));
            } else {
                helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_666666));
                helper.setTextColor(R.id.tv_home_score2, mContext.getResources().getColor(R.color.c_666666));
                helper.setTextColor(R.id.tv_away_score, mContext.getResources().getColor(R.color.c_333333));
                helper.setTextColor(R.id.tv_away_score2, mContext.getResources().getColor(R.color.c_333333));
            }
        } else if (item.getStatus() == 1) {
            helper.setTextColor(R.id.tv_result, mContext.getResources().getColor(R.color.c_1D2550));
            helper.setTextColor(R.id.tv_home_score, mContext.getResources().getColor(R.color.c_00E150));
            helper.setTextColor(R.id.tv_home_score2, mContext.getResources().getColor(R.color.c_00E150));
            helper.setTextColor(R.id.tv_away_score, mContext.getResources().getColor(R.color.c_00E150));
            helper.setTextColor(R.id.tv_away_score2, mContext.getResources().getColor(R.color.c_00E150));
        } else {
            helper.setTextColor(R.id.tv_result, mContext.getResources().getColor(R.color.c_1D2550));
        }
    }

    private void doSubscribe(String matchId, String type, ImageView subscribeIv) {//订阅推送消息
        new SubscribePresenter().doSubscribe(matchId, type, new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                if (!TextUtils.isEmpty(type)) {
                    subscribeIv.setImageResource(R.mipmap.subscribe);
                } else {
                    subscribeIv.setImageResource(R.mipmap.unsubscribe);
                }
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onError(String msg) {
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void getSubscribeType(CricketMatchBean item, ImageView subscribeIv) {//订阅推送消息
        mainActivity.showLoadingDialog();
        new SubscribePresenter().getSubscribeType(item.getId(), new ApiCallback() {
            @Override
            public void onSuccess(String data, String msg) {
                mainActivity.dismissLoadingDialog();
                if (data != null) {
                    List<SubscribeTypeBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("list"), SubscribeTypeBean.class);
                    //这里先弹出一个订阅消息的内容选择框  待选择好后点击确定订阅按钮再调用订阅接口
                    DialogUtil.showSelectSubscribeDialog(mContext, item.getHome_name() + " VS " + item.getAway_name(), list, new DialogUtil.SelectSubscribeBack() {
                        @Override
                        public void onSelectSubscribe(String type) {
                            doSubscribe(item.getId() + "", type, subscribeIv);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String msg) {
                mainActivity.dismissLoadingDialog();
            }

            @Override
            public void onError(String msg) {
                mainActivity.dismissLoadingDialog();
            }

            @Override
            public void onFinish() {
                mainActivity.dismissLoadingDialog();
            }
        });
    }
}
