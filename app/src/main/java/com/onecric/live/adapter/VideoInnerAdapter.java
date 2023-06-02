package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.OneVideoBean;
import com.onecric.live.util.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VideoInnerAdapter extends BaseQuickAdapter<OneVideoBean.SecondCategoryBean, BaseViewHolder> {
    private SimpleDateFormat sfdate1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sfdate2 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    private String tournament;

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public VideoInnerAdapter(int layoutResId, String name, @Nullable List<OneVideoBean.SecondCategoryBean> data) {
        super(layoutResId, data);
        setTournament(name);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, OneVideoBean.SecondCategoryBean item) {
        if(item.id == 0){
            helper.getView(R.id.cl_main).setVisibility(View.GONE);
            helper.setVisible(R.id.iv_footer,true);
            return;
        }

        if(helper.getView(R.id.cl_main) != null && helper.getView(R.id.iv_footer) != null){
            helper.getView(R.id.cl_main).setVisibility(View.VISIBLE);
            helper.getView(R.id.iv_footer).setVisibility(View.GONE);
        }

        ImageView iv_cover = helper.getView(R.id.iv_cover);

        GlideUtil.loadLiveImageDefault(mContext, (item.flie.size()>0)?item.flie.get(0).img:"", iv_cover);
        if (!TextUtils.isEmpty(item.title)) {
            helper.setText(R.id.tv_title,item.title);
        }else {
            helper.setText(R.id.tv_title,"");
        }

        if (!TextUtils.isEmpty(item.username)) {
            helper.setText(R.id.tv_name,item.username);
        }else {
            helper.setText(R.id.tv_name,"");
        }

        if (!TextUtils.isEmpty(item.addtime)) {
            helper.setText(R.id.tv_time,item.addtime);
        }else {
            helper.setText(R.id.tv_time,"");
        }

/*        try {
            String dateStr = sfdate2.format(sfdate1.parse(item.addtime));
            helper.setText(R.id.tv_time, dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            helper.setText(R.id.tv_time, item.addtime);
        }*/
    }
}
