package com.onecric.live.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.onecric.live.R;
import com.onecric.live.activity.VideoCompletePlayActivity;
import com.onecric.live.custom.FoldTextView;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.util.GlideUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/9/14
 */
public class ThemeHeadlineAdapter extends RecyclerView.Adapter {
    private int TYPE_NORMAL = 1;
    private int TYPE_THIRD = 2;
    private List<HeadlineBean> mList;
    private Activity mContext;
    public ThemeHeadlineAdapter(List<HeadlineBean> data, Activity context) {
        this.mList = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_theme_headline, parent, false);
            return new NormalViewHolder(itemView);
        } else if (viewType == TYPE_THIRD) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_news_third, parent, false);
            return new ThirdViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            onBindNormal(mList.get(position), (NormalViewHolder) holder, position);
        }else if(getItemViewType(position) == TYPE_THIRD){
            onBindThird(mList.get(position), (ThirdViewHolder) holder, position);
        }
    }

    private void onBindNormal(HeadlineBean item, NormalViewHolder mHolder, int position) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            mHolder.tv_title.setText(item.getTitle());
        }else {
            mHolder.tv_title.setText("");
        }
        ImageView iv_cover = mHolder.iv_cover;
        Glide.with(mContext).load(item.getImg()).placeholder(R.mipmap.img_updates_default).into(iv_cover);
        if (item.getIs_top() == 1) {
            mHolder.tv_top.setVisibility(View.VISIBLE);
        }else {
            mHolder.tv_top.setVisibility(View.GONE);
        }

        mHolder.iv_hot.setVisibility(View.GONE);
        mHolder.tv_comment.setVisibility(View.GONE);
        if (item.getComment_count() > 0) {
            mHolder.tv_comment.setVisibility(View.VISIBLE);
            //评论数>=百万时加上热门图标并用百万为单位 1M+ 2M+
            if(item.getComment_count() >= 1000000){
                mHolder.iv_hot.setVisibility(View.VISIBLE);
                mHolder.tv_comment.setText(String.valueOf( item.getComment_count()/1000000 + "M+"));
            }else{
                mHolder.tv_comment.setText(String.valueOf(item.getComment_count()));
            }
        }

        if (position == (getItemCount()-1)) {
            mHolder.view_line.setVisibility(View.INVISIBLE);
        }else {
            mHolder.view_line.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.getAddtime())) {
            mHolder.tv_time.setText(item.getAddtime());
        }else {
            mHolder.tv_time.setText("");
        }
        mHolder.itemView.setOnClickListener((v) -> {
            mOnItemClickListener.onItemClick(v, position, mList.get(position));
        });
    }

    private void onBindThird(HeadlineBean bean, ThirdViewHolder mHolder, int position) {
        mHolder.itemView.setOnClickListener(view -> {
            if(!TextUtils.isEmpty(bean.getSource_url())){
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(bean.getSource_url());
                intent.setData(content_url);
                mContext.startActivity(intent);
            }
        });
        mHolder.tvContent.setVisibility(View.GONE);
        mHolder.tv_title.setVisibility(View.GONE);
        mHolder.rv_image.setVisibility(View.GONE);
        mHolder.flowLayout.setVisibility(View.GONE);
        mHolder.iv_icon.setVisibility(View.GONE);
        mHolder.iv_cover.setVisibility(View.GONE);
        mHolder.cl_center.setVisibility(View.GONE);
        mHolder.ivLogo.setVisibility(View.INVISIBLE);

        GlideUtil.loadUserImageDefault(mContext, bean.getAvatar(), mHolder.ivHead);
        mHolder.tvUserName.setText(!TextUtils.isEmpty(bean.getUser_nickname())?bean.getUser_nickname():"");
        mHolder.tvTime.setText(!TextUtils.isEmpty(bean.getAddtime())?(" · "+bean.getAddtime()):"");
        if (!TextUtils.isEmpty(bean.getTitle())) {
            mHolder.tv_title.setVisibility(View.VISIBLE);
            mHolder.tv_title.setText(bean.getTitle());
        }

        if (!TextUtils.isEmpty(bean.getContent())) {
            mHolder.tvContent.setVisibility(View.VISIBLE);
            SpannableStringBuilder msg = FaceManager.handlerEmojiText(bean.getContent());
            mHolder.tvContent.setText(msg);
        }

        String name = bean.getSource_name();
        if(!TextUtils.isEmpty(name)){
            mHolder.ivLogo.setVisibility(View.VISIBLE);
            if(name.equals("Reddit")){
                mHolder.ivLogo.setImageResource(R.mipmap.icon_reddit);
            }else if(name.equals("Twitter")){
                mHolder.ivLogo.setImageResource(R.mipmap.icon_twitter);
            }
        }

        String tags = bean.getTags();
        if (!TextUtils.isEmpty(tags)) {
            mHolder.flowLayout.setVisibility(View.VISIBLE);
            String[] splitList = tags.split(",");
            TagAdapter tagRecordAdapter = new TagAdapter<String>(new ArrayList<>(Arrays.asList(splitList))) {
                @Override
                public View getView(FlowLayout flowLayout, int i, String s) {
                    TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_tv, flowLayout, false);
                    tv.setTextColor(Color.parseColor("#00ABF0"));
                    tv.setText("#" + s + "#");
                    return tv;
                }
            };
            mHolder.flowLayout.setAdapter(tagRecordAdapter);
            tagRecordAdapter.notifyDataChanged();
        }

        String video = bean.getVideo();
        if (TextUtils.isEmpty(video)) {
            String url = bean.getImg();
            if (!TextUtils.isEmpty(url)) {
                mHolder.cl_center.setVisibility(View.VISIBLE);
                String[] splitList = url.split(",");
                ArrayList<String> imageInfo = new ArrayList<>();
                if (splitList.length <= 1) {
                    mHolder.iv_cover.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(url)
                            .placeholder(R.mipmap.img_updates_default).error(R.mipmap.img_updates_default)
                            .dontAnimate().into(mHolder.iv_cover);
                } else {
                    mHolder.rv_image.setVisibility(View.VISIBLE);
                    for (int i = 0; i < splitList.length; i++) {
                        imageInfo.add(splitList[i]);
                    }
                    mHolder.rv_image.setLayoutManager(new GridLayoutManager(mContext, 3));
                    mHolder.rv_image.setAdapter(new ImageAdapter(mContext, imageInfo));
                }
            }
        } else {
            mHolder.cl_center.setVisibility(View.VISIBLE);
            mHolder.iv_cover.setVisibility(View.VISIBLE);
            mHolder.iv_icon.setVisibility(View.VISIBLE);
            String[] splitList = video.split(",");
            Glide.with(mContext).load(splitList[0])
                    .placeholder(R.mipmap.img_updates_default).error(R.mipmap.img_updates_default)
                    .dontAnimate().into(mHolder.iv_cover);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (!TextUtils.isEmpty(mList.get(position).getSource_name())){
            return TYPE_THIRD;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setData(List<HeadlineBean> list) {
        if (mList != null && mList.size() > 0) {
            mList.clear();
        }
        this.mList = list;
        notifyDataSetChanged();
    }

    public void addData(List<HeadlineBean> data) {
        if (mList != null && data != null) {
            mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_top,tv_comment,tv_time;
        ImageView iv_cover,iv_hot;
        View view_line;
        private NormalViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_top = itemView.findViewById(R.id.tv_top);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv_cover = itemView.findViewById(R.id.iv_cover);
            iv_hot = itemView.findViewById(R.id.iv_hot);
            view_line = itemView.findViewById(R.id.view_line);
        }

    }

    private class ThirdViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvUserName,tv_title,tv_full_text;
        RecyclerView rv_image;
        ImageView iv_cover, ivHead, iv_icon, ivLogo;
        TagFlowLayout flowLayout;
        View view;
        ConstraintLayout cl_center;
        FoldTextView tvContent;

        private ThirdViewHolder(View itemView) {
            super(itemView);
            rv_image = itemView.findViewById(R.id.rv_image);
            tvContent = itemView.findViewById(R.id.tv_content);
            iv_cover = itemView.findViewById(R.id.iv_cover);
            ivHead = itemView.findViewById(R.id.iv_head);
            ivLogo = itemView.findViewById(R.id.iv_logo);
            tvTime = itemView.findViewById(R.id.tv_date);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            flowLayout = itemView.findViewById(R.id.flowlayout);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            view = itemView.findViewById(R.id.view_line);
            cl_center = itemView.findViewById(R.id.cl_center);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_full_text = itemView.findViewById(R.id.tv_full_text);
            initView();
        }

        private void initView() {
            int width = UIUtil.getScreenWidth(mContext);
            rv_image.addItemDecoration(new GridSpacingItemDecoration(3, UIUtil.dip2px(mContext,5), false));
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, HeadlineBean bean);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
        private int spanCount; //列数
        private int spacing; //间隔
        private boolean includeEdge; //是否包含边缘
        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //这里是关键，需要根据你有几列来判断
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
