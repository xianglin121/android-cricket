package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.CricketDetailActivity;
import com.onecric.live.model.CricketMatchBean;
import com.onecric.live.model.CricketTournamentBean;
import com.onecric.live.model.SubscribeTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/8/27
 */
public class SubscribeTypeAdapter extends BaseQuickAdapter<SubscribeTypeBean, BaseViewHolder> {
    private Switch aSwitch;

    public SubscribeTypeAdapter(Switch aSwitch, int layoutResId, @Nullable List<SubscribeTypeBean> data) {
        super(layoutResId, data);
        this.aSwitch = aSwitch;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SubscribeTypeBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(item.getName());
        CheckBox checkbox = helper.getView(R.id.checkbox);
        if (aSwitch.isChecked()) {
            checkbox.setEnabled(true);
        } else {
            checkbox.setEnabled(false);
        }
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item.setIs_subscribe(1);
                } else {
                    item.setIs_subscribe(0);
                }
            }
        });
        List<SubscribeTypeBean> list = getData();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    for (SubscribeTypeBean item : list) {
                        item.setSelect_subscribe(item.getIs_subscribe());
                        if (item.getIs_subscribe() == 1) {
                            item.setIs_subscribe(0);
                        }
                    }
                } else {
                    for (SubscribeTypeBean item : list) {
                        if (item.getSelect_subscribe() == 1) {
                            item.setIs_subscribe(1);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
        if (item.getIs_subscribe() == 1) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }
    }
}
