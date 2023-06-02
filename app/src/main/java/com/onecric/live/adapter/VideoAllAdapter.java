package com.onecric.live.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.model.ViewMoreBean;
import com.onecric.live.util.GlideUtil;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class VideoAllAdapter extends BaseQuickAdapter<ViewMoreBean, BaseViewHolder> {
    public VideoAllAdapter(int layoutResId, @Nullable List<ViewMoreBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ViewMoreBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        ImageView iv_cover = helper.getView(R.id.iv_cover);

        int width = UIUtil.getScreenWidth(mContext);
        android.view.ViewGroup.LayoutParams pp = iv_cover.getLayoutParams();
        pp.height = (int)((width) * 0.447);
        iv_cover.setLayoutParams(pp);
        GlideUtil.loadLiveImageDefault(mContext, (item.flie.size()>0)?item.flie.get(0).img:"", iv_cover);

        if (!TextUtils.isEmpty(item.title)) {
            tv_title.setText(item.title);
        }else {
            tv_title.setText("");
        }

    }
}
