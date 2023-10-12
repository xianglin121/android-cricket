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

import com.onecric.live.R;
import com.onecric.live.activity.CricketInnerActivity;
import com.onecric.live.fragment.CricketNewFragment;
import com.onecric.live.model.CricketAllBean;
import com.onecric.live.model.CricketNewBean;
import com.tencent.qcloud.tuicore.util.DateTimeUtil;

import java.util.Date;
import java.util.List;

//public class CricketDayAdapter extends BaseQuickAdapter<CricketAllBean.CricketDayBean, BaseViewHolder> {
public class CricketDayAdapter extends RecyclerView.Adapter<CricketDayAdapter.ViewHolder>  {
    CricketNewFragment fragment;
    Context context;
    List<CricketAllBean.CricketDayBean> bean;
    public RecyclerView todayRecyclerView;
    //最近的位置
    public int todayBeginIndex;

    public CricketDayAdapter(CricketNewFragment fragment, Context context, List<CricketAllBean.CricketDayBean> mList) {
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
        // 设置选中item中的内部recyclerView的LayoutManager和标记
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        //不回收子view
        layoutManager.setItemPrefetchEnabled(false);
        layoutManager.setRecycleChildrenOnDetach(true);


        helper.rv_cricket.setLayoutManager(layoutManager);
        helper.rv_cricket.setTag(layoutManager);
        helper.rv_cricket.setNestedScrollingEnabled(false);

        String dates[] = getDayInfo(bean.get(position).getDay(),context);
//        fragment.setDayInfo(dates,new Date().getTime());
        helper.tv_date.setText(dates[0]);
        helper.tv_month.setText(dates[1]);
        helper.tv_day.setText(dates[2]);
        if(dates[2].equals(context.getString(R.string.today))){
            fragment.todayPosition = helper.getLayoutPosition();
            helper.isToday = true;
            //Fixme 滚动到今天最早正在比赛的赛事>距离现在最近的未开赛
            todayRecyclerView = helper.rv_cricket;
            todayLately:{
                int todayLatelyIndex = 0;
                long minTime = 0;
                long nowTime = new Date().getTime();
                for(int i=0 ; i<bean.get(position).getList().size() ; i++){
                    List<CricketNewBean.CricketMatchNewBean> cList = bean.get(position).getList().get(i).getCricketMatch();
                    for(int j=0 ; j<cList.size() ; j++){
                        if(cList.get(j).getStatus() == 1){
                            todayBeginIndex = i;
                            break todayLately;
                        }else if(cList.get(j).getStatus() == 0){
                            long countTime = DateTimeUtil.getStringToDate(cList.get(j).getScheduled(), "yyyy-MM-dd HH:mm:ss") - nowTime;
                            if(countTime>0 &&(minTime == 0 || countTime<minTime)){
                                minTime = countTime;
                                todayLatelyIndex = i;
                            }
                        }
                    }
                }
                todayBeginIndex = todayLatelyIndex;
            }
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
        public boolean isToday;

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
