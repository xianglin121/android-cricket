package com.longya.live.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.FootballEventBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class FootballMatchImportantAdapter extends BaseQuickAdapter<FootballEventBean, BaseViewHolder> {
    public FootballMatchImportantAdapter(int layoutResId, @Nullable List<FootballEventBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FootballEventBean item) {
        View dash_line = helper.getView(R.id.dash_line);
        if (helper.getLayoutPosition() == (getItemCount()-1)) {
            dash_line.setVisibility(View.GONE);
        }else {
            dash_line.setVisibility(View.VISIBLE);
        }
        ImageView iv_label = helper.getView(R.id.iv_label);
        TextView tv_home_time = helper.getView(R.id.tv_home_time);
        TextView tv_away_time = helper.getView(R.id.tv_away_time);
        TextView tv_home_content = helper.getView(R.id.tv_home_content);
        TextView tv_away_content = helper.getView(R.id.tv_away_content);
        if (item.getPosition() == 1) {//主队发生事件
            tv_home_time.setVisibility(View.GONE);
            tv_away_time.setVisibility(View.VISIBLE);
            tv_home_content.setVisibility(View.VISIBLE);
            tv_away_content.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(item.getTime())) {
                tv_away_time.setText(item.getTime());
            }else {
                tv_away_time.setText("");
            }
            if (!TextUtils.isEmpty(item.getData())) {
                tv_home_content.setText(item.getData());
            }else {
                tv_home_content.setText("");
            }
        }else {//客队发生事件
            tv_home_time.setVisibility(View.VISIBLE);
            tv_away_time.setVisibility(View.GONE);
            tv_home_content.setVisibility(View.GONE);
            tv_away_content.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(item.getTime())) {
                tv_home_time.setText(item.getTime());
            }else {
                tv_home_time.setText("");
            }
            if (!TextUtils.isEmpty(item.getData())) {
                tv_away_content.setText(item.getData());
            }else {
                tv_away_content.setText("");
            }
        }
        iv_label.setImageBitmap(getIcon(item.getType()));
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
