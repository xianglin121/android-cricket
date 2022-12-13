package com.onecric.live.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.RangqiuIndexDetailActivity;
import com.onecric.live.fragment.BasketballMatchIndexInnerFragment;
import com.onecric.live.model.BasketballIndexOneBean;
import com.onecric.live.model.MatchListBean;

import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/11
 */
public class BasketballMatchBaijiaIndexAdapter extends BaseMultiItemQuickAdapter<BasketballIndexOneBean, BaseViewHolder> {
    private int mType;
    private int mId;

    public BasketballMatchBaijiaIndexAdapter(List<BasketballIndexOneBean> data, int type, int id) {
        super(data);
        mType = type;
        mId = id;
        if (type == BasketballMatchIndexInnerFragment.TYPE_RANGQIU) {
            addItemType(MatchListBean.HEAD, R.layout.item_football_baijia_rangqiu_index_head);
        }else if (type == BasketballMatchIndexInnerFragment.TYPE_OUPEI) {
            addItemType(MatchListBean.HEAD, R.layout.item_football_baijia_oupei_index_head);
        }else if (type == BasketballMatchIndexInnerFragment.TYPE_DAXIAOQIU) {
            addItemType(MatchListBean.HEAD, R.layout.item_football_baijia_daxiaoqiu_index_head);
        }
        addItemType(MatchListBean.CONTENT, R.layout.item_football_baijia_index_content);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BasketballIndexOneBean item) {
        switch (helper.getItemViewType()) {
            case MatchListBean.HEAD:
                break;
            case MatchListBean.CONTENT:
                LinearLayout rootView = helper.getView(R.id.rootView);
                int position = helper.getLayoutPosition()-1;
                if (mContext.getString(R.string.highest_value).equals(item.getCompany_name())
                        || mContext.getString(R.string.lowest_value).equals(item.getCompany_name())
                        || mContext.getString(R.string.average_value).equals(item.getCompany_name())){
                    rootView.setBackgroundColor(mContext.getResources().getColor(R.color.c_FFF1E0));
                    helper.getView(R.id.iv_arrow).setVisibility(View.GONE);
                }else {
                    helper.getView(R.id.iv_arrow).setVisibility(View.VISIBLE);
                    helper.getView(R.id.cl_company).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RangqiuIndexDetailActivity.forward(mContext, RangqiuIndexDetailActivity.TYPE_BASKETBALL, item.getCompany_id(), mId, mType);
                        }
                    });
                    if (position%2 == 0) {
                        rootView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                    }else {
                        rootView.setBackgroundColor(mContext.getResources().getColor(R.color.c_FFFBF6));
                    }
                }
                 TextView tv_name = helper.getView(R.id.tv_name);
                TextView tv_start_host = helper.getView(R.id.tv_start_host);
                TextView tv_start_index = helper.getView(R.id.tv_start_index);
                TextView tv_start_guest = helper.getView(R.id.tv_start_guest);
                TextView tv_current_host = helper.getView(R.id.tv_current_host);
                TextView tv_current_index = helper.getView(R.id.tv_current_index);
                TextView tv_current_guest = helper.getView(R.id.tv_current_guest);
                if (!TextUtils.isEmpty(item.getCompany_name())) {
                    tv_name.setText(item.getCompany_name());
                }else {
                    tv_name.setText("");
                }
                if (!TextUtils.isEmpty(item.getEarly_host())) {
                    tv_start_host.setText(item.getEarly_host());
                }else {
                    tv_start_host.setText("");
                }
                if (!TextUtils.isEmpty(item.getEarly_early())) {
                    tv_start_index.setText(item.getEarly_early());
                }else {
                    tv_start_index.setText("");
                }
                if (!TextUtils.isEmpty(item.getEarly_guest())) {
                    tv_start_guest.setText(item.getEarly_guest());
                }else {
                    tv_start_guest.setText("");
                }
                if (!TextUtils.isEmpty(item.getScilicet_host())) {
                    tv_current_host.setText(item.getScilicet_host());
                }else {
                    tv_current_host.setText("");
                }
                if (!TextUtils.isEmpty(item.getScilicet_early())) {
                    tv_current_index.setText(item.getScilicet_early());
                }else {
                    tv_current_index.setText("");
                }
                if (!TextUtils.isEmpty(item.getScilicet_guest())) {
                    tv_current_guest.setText(item.getScilicet_guest());
                }else {
                    tv_current_guest.setText("");
                }
                break;
        }
    }
}
