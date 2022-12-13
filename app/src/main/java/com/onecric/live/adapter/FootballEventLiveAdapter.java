package com.onecric.live.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.FootballEventBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class FootballEventLiveAdapter extends BaseQuickAdapter<FootballEventBean, BaseViewHolder> {
    public FootballEventLiveAdapter(int layoutResId, @Nullable List<FootballEventBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FootballEventBean item) {
        ConstraintLayout constraintLayout = helper.getView(R.id.constraintLayout);
        ImageView iv_label = helper.getView(R.id.iv_label);
        TextView tv_time = helper.getView(R.id.tv_time);
        ImageView iv_event = helper.getView(R.id.iv_event);
        TextView tv_content = helper.getView(R.id.tv_content);
        TextView tv_description = helper.getView(R.id.tv_description);
        if (item.getPosition() == 0) {
            constraintLayout.setVisibility(View.GONE);
            tv_description.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(item.getData())) {
                tv_description.setText(item.getData());
            }else {
                tv_description.setText("");
            }
        }else {
            constraintLayout.setVisibility(View.VISIBLE);
            tv_description.setVisibility(View.GONE);
            tv_description.setText("");
            if (item.getPosition() == 1) {
                iv_label.setVisibility(View.VISIBLE);
                iv_label.setBackground(mContext.getResources().getDrawable(R.drawable.shape_blue_team_half));
            }else if (item.getPosition() == 2) {
                iv_label.setVisibility(View.VISIBLE);
                iv_label.setBackground(mContext.getResources().getDrawable(R.drawable.shape_red_team_half));
            }else {
                iv_label.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(item.getTime())) {
            tv_time.setText(item.getTime());
        }else {
            tv_time.setText("");
        }
        iv_event.setImageBitmap(getIcon(item.getType()));
        if (!TextUtils.isEmpty(item.getData())) {
            tv_content.setText(item.getData());
        }else {
            tv_content.setText("");
        }
    }

    private Bitmap getIcon(int type) {
        Bitmap bitmap = null;
        switch (type) {
            case 1://进球
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_goal);
                break;
            case 2://角球
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_corner_kick);
                break;
            case 3://黄牌
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_yellow_card_2);
                break;
            case 4://红牌
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_red_card_2);
                break;
            case 8://点球
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_penalty_kick);
                break;
            case 9://换人
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_substitution);
                break;
            case 15://两黄变红
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_two_yellows_sent_off);
                break;
            case 16://点球未进
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_missed_penalty);
                break;
            case 17://乌龙球
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_own_goals);
                break;
        }
        return bitmap;
    }
}
