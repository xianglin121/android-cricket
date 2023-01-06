package com.onecric.live.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.AppManager;
import com.onecric.live.R;
import com.onecric.live.activity.PreViewActivity;
import com.onecric.live.activity.VideoCompletePlayActivity;
import com.onecric.live.activity.VideoPublishActivity;
import com.onecric.live.custom.Glide4Engine;
import com.onecric.live.custom.popup.PopBottomView;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.view.MvpActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2020/4/27
 */
public class VideoPublishAdapter extends RecyclerView.Adapter<VideoPublishAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> list;
    private List<String> showList;
    private String type;//0:相册 1:视频
    private PopBottomView photo_view;
    private OnItemClick listen;
    private Bitmap scaledBitmap;
    private String format;

    public void setListen(OnItemClick listen) {
        this.listen = listen;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public VideoPublishAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        list = new ArrayList<>();
        showList = new ArrayList<>();
    }

    public void addItem(String uri) {
        this.list.add(uri);
        showList.add(uri);
        notifyDataSetChanged();
    }

    public void addItem(List<String> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                this.list.add(list.get(i));
                showList.add(list.get(i));
            }
            notifyDataSetChanged();
        }
    }

    public void addVideo(List<String> list) {
        this.list.add(list.get(0));
        showList.add(list.get(0));
        try {
            // 获取预览图
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(list.get(0));
            Bitmap previewBitmap = mmr.getFrameAtTime();

            // 缩放
            int PREVIEW_VIDEO_IMAGE_HEIGHT = 1000; // Pixels
            int videoWidth = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            int videoHeight = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            int videoViewWidth = PREVIEW_VIDEO_IMAGE_HEIGHT * videoWidth / videoHeight;
            int videoViewHeight = PREVIEW_VIDEO_IMAGE_HEIGHT;
            scaledBitmap = Bitmap.createScaledBitmap(previewBitmap, videoViewWidth, videoViewHeight, true);
            File fm_file = ToolUtil.saveLocalBitmap(scaledBitmap, "video_fm_" + System.currentTimeMillis());
            ((VideoPublishActivity) mContext).addCover(fm_file);
            // 获取时长
            String strDuration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            int duration = Integer.parseInt(strDuration) / 1000;
            String min = String.valueOf(duration / 60).length() >= 2 ? String.valueOf(duration / 60) : "0" + String.valueOf(duration / 60);
            String sec = String.valueOf(duration % 60).length() >= 2 ? String.valueOf(duration % 60) : "0" + String.valueOf(duration % 60);
            format = String.format("%s:%s", min, sec);

            mmr.release();
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int position) {
        this.list.remove(position);
        this.showList.remove(position);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.item_community_publish, viewGroup, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (i >= list.size()) {
            viewHolder.img_ic.setImageResource(R.mipmap.bg_community_publish_add);
            viewHolder.close_iv.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showPop(v);
                    openVideoSelect();
                }
            });
        } else {
            if (type.equals("0")) {
                GlideUtil.loadImageDefault(mContext, list.get(i), viewHolder.img_ic);
            } else {
                viewHolder.img_ic.setImageBitmap(scaledBitmap);
            }
            viewHolder.close_iv.setVisibility(View.VISIBLE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showList != null && showList.size() > 0) {
                        if ("0".equals(type)) {
                            Intent intent = new Intent(mContext, PreViewActivity.class);
                            intent.putStringArrayListExtra(PreViewActivity.IMAGE, (ArrayList<String>) showList);
                            intent.putExtra(PreViewActivity.POSITION, 0);
                            mContext.startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, VideoCompletePlayActivity.class);
                            intent.putExtra("videoUrl", showList.get(0));
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
            viewHolder.close_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listen != null)
                        listen.click(i, list.get(i));
                }
            });
        }
    }

    public void showPop(View v) {
        if (photo_view == null) {
            photo_view = new PopBottomView(mContext);

        }
        List<String> value = new ArrayList<>();
        if ("0".equals(type)) {
            value.add("图片");
        } else {
            value.add("图片");
            value.add("视频");
        }

        photo_view.setData(value);
        photo_view.setListen(new PopBottomView.OnPopClickListen() {
            @Override
            public void itemClick(int position) {
                switch (position) {
                    case 0:
                        openPicsSelect(9 - list.size());
                        break;
                    case 1:
                        openVoiceSelect();
                        break;
                }
            }
        });
        photo_view.showPop(v);
    }

    public void openImgSelect() {
        openPicsSelect(9 - list.size());
    }

    public void openVideoSelect() {
        if (!ToolUtil.checkPermission((MvpActivity) mContext)) {
            return;
        }
        openVoiceSelect();
    }

    public void openPicsSelect(int size) {
        Matisse.from((Activity) mContext)
                .choose(MimeType.ofImage())
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, AppManager.mContext.getPackageName() + ".fileProvider"))
                .maxSelectable(size)
                .gridExpectedSize(DpUtil.dp2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())
                .originalEnable(true)
                .maxOriginalSize(10)
                .showSingleMediaType(true)
                .forResult(201);
    }

    public void openVoiceSelect() {
        Matisse.from((Activity) mContext)
                .choose(MimeType.ofVideo())
                .countable(true)
                .capture(false)
                .captureStrategy(
                        new CaptureStrategy(true, AppManager.mContext.getPackageName() + ".fileProvider"))
                .maxSelectable(1)
                .gridExpectedSize(DpUtil.dp2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())
                .originalEnable(true)
                .maxOriginalSize(10)
                .showSingleMediaType(true)
                .forResult(202);
    }

    @Override
    public int getItemCount() {
        if (list.size() >= 9) {
            return list.size();
        } else if ("0".equals(type)) {
            return list.size() + 1;
        }
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_ic;
        ImageView close_iv;

        ViewHolder(View itemView) {
            super(itemView);
            img_ic = itemView.findViewById(R.id.img_ic);
            close_iv = itemView.findViewById(R.id.close_iv);
        }
    }

    public interface OnItemClick {
        void click(int position, Object value);
    }

}
