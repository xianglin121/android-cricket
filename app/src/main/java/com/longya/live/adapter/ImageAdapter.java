package com.longya.live.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.R;
import com.longya.live.activity.PreViewActivity;
import com.longya.live.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 东莞市梦幻科技有限公司
 * 开发公司：东莞星轨科技有限公司
 * 时间：2020/5/7
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

    private List<String> mList;
    private Context mContext;
    private LayoutInflater inflater;

    public ImageAdapter(Context Context, List<String> mList) {
        this.mList = mList;
        this.mContext = Context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ImageAdapter.ViewHolder(inflater.inflate(R.layout.item_image,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder viewHolder, int i) {
        GlideUtil.loadImageDefault(mContext,mList.get(i),viewHolder.img_iv);
        viewHolder.img_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<String> images = new ArrayList<>(mList);
//                ImagePager.StartByImages(images,i,mContext);
                Intent intent = new Intent(mContext, PreViewActivity.class);
                intent.putStringArrayListExtra(PreViewActivity.IMAGE, (ArrayList<String>) mList);
                intent.putExtra(PreViewActivity.POSITION, i);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_iv = itemView.findViewById(R.id.img_iv);
        }
    }


}
