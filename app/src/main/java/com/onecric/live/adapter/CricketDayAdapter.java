package com.onecric.live.adapter;

import static com.onecric.live.util.TimeUtil.getDayInfo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.CricketInnerActivity;
import com.onecric.live.activity.SearchMatchActivity;
import com.onecric.live.fragment.CricketNewFragment;
import com.onecric.live.model.CricketAllBean;

import java.util.List;

//public class CricketDayAdapter extends BaseQuickAdapter<CricketAllBean.CricketDayBean, BaseViewHolder> {
public class CricketDayAdapter extends RecyclerView.Adapter<CricketDayAdapter.ViewHolder>  {
    CricketNewFragment fragment;
    Context context;
    List<CricketAllBean.CricketDayBean> bean;

    public CricketDayAdapter(CricketNewFragment fragment,Context context, List<CricketAllBean.CricketDayBean> mList) {
        this.context = context;
        this.fragment = fragment;
        this.bean = mList;
    }

    @NonNull
    @Override
    public CricketDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_cricket_day, parent, false);
        return new CricketDayAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CricketDayAdapter.ViewHolder helper, int position) {
        String dates[] = getDayInfo(bean.get(position).getDay());
//        fragment.setDayInfo(dates,new Date().getTime());
        helper.tv_date.setText(dates[0]);
        helper.tv_month.setText(dates[1]);
        helper.tv_day.setText(dates[2]);
        if(dates[0].equals("Today")){
            fragment.todayPosition = helper.getLayoutPosition();
        }

        CricketNewAdapter mAdapter = new CricketNewAdapter(R.layout.item_cricket_new, bean.get(position).getList());
        mAdapter.setOnItemChildClickListener((adapter, view, index) -> {
            if (view.getId() == R.id.ll_title && !TextUtils.isEmpty(mAdapter.getItem(index).getTournamentId())) {
                CricketInnerActivity.forward(context, mAdapter.getItem(index).getName(), mAdapter.getItem(index).getType(), Integer.parseInt(mAdapter.getItem(index).getTournamentId()));
            }
        });
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_match_rv_empty, null, false);
        mAdapter.setEmptyView(inflate);
        RecyclerView rv_cricket = helper.rv_cricket;
        rv_cricket.setLayoutManager(new LinearLayoutManager(context));
        rv_cricket.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return bean.size();
    }

    public void setData(List<CricketAllBean.CricketDayBean> list) {
        if (bean != null && bean.size() > 0) {
            bean.clear();
        }
        this.bean = list;
        notifyDataSetChanged();
    }

    public void setPositionData(int index,CricketAllBean.CricketDayBean b){
        if (bean != null && bean.size() > 0) {
            bean.set(index,b);
            notifyItemChanged(index);
        }
    }

    public void addData(List<CricketAllBean.CricketDayBean> data) {
        if (bean != null && data != null) {
            bean.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addData(int position,List<CricketAllBean.CricketDayBean> data) {
        if (bean != null && data != null) {
            bean.addAll(position,data);
            notifyDataSetChanged();
        }
    }

    public List<CricketAllBean.CricketDayBean> getData(){
        return bean;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_time_title;
        public TextView tv_day;
        public TextView tv_date;
        public TextView tv_month;
        public View line;
        public RecyclerView rv_cricket;

        ViewHolder(View itemView) {
            super(itemView);
            rl_time_title = itemView.findViewById(R.id.rl_time_title);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_month = itemView.findViewById(R.id.tv_month);
            line = itemView.findViewById(R.id.line);
            rv_cricket = itemView.findViewById(R.id.rv_cricket);
        }
    }

    //-------
/*    public CricketDayAdapter(CricketNewFragment fragment,int layoutResId, List<CricketAllBean.CricketDayBean> list) {
        super(layoutResId,list);
        this.fragment = fragment;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, CricketAllBean.CricketDayBean item) {
        String dates[] = getDayInfo(item.getDay());
        helper.setText(R.id.tv_date,dates[0]);
        helper.setText(R.id.tv_month,dates[1]);
        helper.setText(R.id.tv_day,dates[2]);

        CricketNewAdapter mAdapter = new CricketNewAdapter(R.layout.item_cricket_new, item.getList());
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_title && !TextUtils.isEmpty(mAdapter.getItem(position).getTournamentId())) {
                CricketInnerActivity.forward(mContext, mAdapter.getItem(position).getName(), mAdapter.getItem(position).getType(), Integer.parseInt(mAdapter.getItem(position).getTournamentId()));
            }
        });
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_match_rv_empty, null, false);
        mAdapter.setEmptyView(inflate);
        RecyclerView rv_cricket = helper.getView(R.id.rv_cricket);

        rv_cricket.setLayoutManager(new LinearLayoutManager(mContext));
        rv_cricket.setAdapter(mAdapter);
    }*/

}
