package com.longya.live.custom.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.R;

import java.util.ArrayList;
import java.util.List;

public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mList;

    public BottomAdapter(Context context, MOnItemClickListener onItemClick){
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        mList = new ArrayList<>();
        this.onItemClick = onItemClick;
    }

    public interface MOnItemClickListener {
        void onItemClick(int position, Object o);
    }

    public MOnItemClickListener onItemClick;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.item_bottom_view,viewGroup,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.content_tv.setText(mList.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(i,mList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView content_tv;

        ViewHolder(View itemView) {
            super(itemView);
            content_tv = itemView.findViewById(R.id.content_tv);
        }
    }

    public void refreshData(List<String> list){
        this.mList = list;
        notifyDataSetChanged();
    }
}
