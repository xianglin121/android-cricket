package com.tencent.liteav.demo.superplayer.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.model.CompetitionBean;

import java.util.List;


public class CricketScorecardAdapter extends BaseQuickAdapter<CompetitionBean, BaseViewHolder> {
    public int match_id;
    public int selectPosition = 0;
    public CricketScorecardAdapter(int layoutResId, @Nullable List<CompetitionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CompetitionBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(item.name)) {
            builder.append(item.name);
        }
        if (!TextUtils.isEmpty(item.order)) {
            builder.append(" "+item.order);
        }
        if (!TextUtils.isEmpty(item.score)) {
            builder.append(" "+item.score);
        }
        tv_name.setText(builder);
        if(helper.getPosition() % 2 ==0){
            tv_name.setBackground(mContext.getResources().getDrawable(R.drawable.selector_match_team_left));
        }else{
            tv_name.setBackground(mContext.getResources().getDrawable(R.drawable.selector_match_team_right));
        }

        if(selectPosition == 0 && helper.getAbsoluteAdapterPosition() == 0){
            item.isSelected = true;
        }

        tv_name.setSelected(item.isSelected);
    }

    public void refreshChecked(int index){
        if(getData().size()<index || index == selectPosition){
            return;
        }
        int last = selectPosition;
        getData().get(index).isSelected = true;
        selectPosition = index;
        notifyItemChanged(index);
        getData().get(last).isSelected = false;
        notifyItemChanged(last);
    }

}
