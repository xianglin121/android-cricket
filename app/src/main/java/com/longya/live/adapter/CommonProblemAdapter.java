package com.longya.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.R;
import com.longya.live.model.ProblemBean;
import com.longya.live.model.ShortVideoBean;
import com.longya.live.util.GlideUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class CommonProblemAdapter extends BaseQuickAdapter<ProblemBean, BaseViewHolder> {
    public CommonProblemAdapter(int layoutResId, @Nullable List<ProblemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProblemBean item) {
        if (!TextUtils.isEmpty(item.getPost_title())) {
            helper.setText(R.id.tv_title, item.getPost_title());
        }else {
            helper.setText(R.id.tv_title, "");
        }
        if (!TextUtils.isEmpty(item.getPost_content())) {
            helper.setText(R.id.tv_content, item.getPost_content());
        }else {
            helper.setText(R.id.tv_content, "");
        }
        ImageView iv_arrow = helper.getView(R.id.iv_arrow);
        helper.getView(R.id.ll_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_arrow.setSelected(!iv_arrow.isSelected());
                if (iv_arrow.isSelected()) {
                    iv_arrow.setBackgroundResource(R.mipmap.icon_arrow_up);
                    helper.getView(R.id.tv_content).setVisibility(View.VISIBLE);
                }else {
                    iv_arrow.setBackgroundResource(R.mipmap.icon_arrow_down_two);
                    helper.getView(R.id.tv_content).setVisibility(View.GONE);
                }
            }
        });
    }
}
