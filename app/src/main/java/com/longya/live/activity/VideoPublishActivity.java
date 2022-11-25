package com.longya.live.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.AppManager;
import com.longya.live.CommonAppConfig;
import com.longya.live.CommonCode;
import com.longya.live.R;
import com.longya.live.adapter.CommunityPublishAdapter;
import com.longya.live.adapter.VideoPublishAdapter;
import com.longya.live.custom.listener.SoftKeyBoardListener;
import com.longya.live.model.JsonBean;
import com.longya.live.model.VideoBean;
import com.longya.live.presenter.theme.CommunityPublishPresenter;
import com.longya.live.presenter.video.VideoPublishPresenter;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.ToastUtil;
import com.longya.live.view.MvpActivity;
import com.longya.live.view.theme.CommunityPublishView;
import com.longya.live.view.video.VideoPublishView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.pili.droid.shortvideo.PLShortVideoTranscoder;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;
import com.tencent.qcloud.tuikit.tuichat.component.face.Emoji;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.tencent.qcloud.tuikit.tuichat.ui.view.input.face.FaceFragment;
import com.zhihu.matisse.Matisse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_LOW_MEMORY;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_NO_VIDEO_TRACK;
import static com.qiniu.pili.droid.shortvideo.PLErrorCode.ERROR_SRC_DST_SAME_FILE_PATH;

public class VideoPublishActivity extends MvpActivity<VideoPublishPresenter> implements VideoPublishView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, VideoPublishActivity.class);
        context.startActivity(intent);
    }

    private EditText et_input;
    private RecyclerView recyclerView;
    private VideoPublishAdapter mAdapter;
    private Dialog mLoadingDialog;
    //图片上传
    private List<File> mTempImgList = new ArrayList<>();
    private List<String> mImgList = new ArrayList<>();
    private int mImgSize;
    //视频上传
    private String mCoverImg;
    private String videoPath;
    private LinearLayout ll_bottom;
    //表情
    private RelativeLayout more_container;
    private FaceFragment mFaceFragment;//表情界面
    private FragmentManager mFragmentManager;
    private InputMethodManager imm;

    private String mToken;

    @Override
    public boolean getStatusBarTextColor() {
        return true;
    }

    @Override
    protected VideoPublishPresenter createPresenter() {
        return new VideoPublishPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_publish;
    }

    @Override
    protected void initView() {
        mLoadingDialog = DialogUtil.loadingDialog(this, getString(R.string.uploading));
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        et_input = findViewById(R.id.et_input);
        recyclerView = findViewById(R.id.recyclerView);
        ll_bottom = findViewById(R.id.ll_bottom);
        more_container = findViewById(R.id.more_container);

        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.iv_emoji).setOnClickListener(this);
        findViewById(R.id.iv_img).setOnClickListener(this);
        findViewById(R.id.iv_video).setOnClickListener(this);

        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ll_bottom.getWidth(), ll_bottom.getHeight());
                layoutParams.bottomMargin = height;
                ll_bottom.setLayoutParams(layoutParams);
            }

            @Override
            public void keyBoardHide(int height) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ll_bottom.getWidth(), ll_bottom.getHeight());
                layoutParams.bottomMargin = 0;
                ll_bottom.setLayoutParams(layoutParams);
            }
        });
        et_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    more_container.setVisibility(View.GONE);
                    et_input.requestFocus();
                    imm.showSoftInput(et_input, 0);
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new VideoPublishAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        mAdapter.setListen(new VideoPublishAdapter.OnItemClick() {
            @Override
            public void click(int position, Object value) {
                if (mAdapter.getType().equals(0)) {
                    mTempImgList.remove(position);
                    mImgSize--;
                }else {
                    mCoverImg = "";
                    videoPath = "";
                }
                mAdapter.removeItem(position);
            }
        });
        recyclerView.setAdapter(mAdapter);

        mvpPresenter.getToken();
    }

    @Override
    public void getTokenSuccess(String token) {
        mToken = token;
    }

    @Override
    public void getDataSuccess(JsonBean model) {
        mLoadingDialog.dismiss();
        ToastUtil.show(getString(R.string.tip_publish_success));
        finish();
    }

    @Override
    public void getDataFail(String msg) {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(videoPath)) {
                    ToastUtil.show(getString(R.string.please_select_video));
                    return;
                }
                doUploadVideo();
                break;
            case R.id.iv_emoji:
                if (more_container.getVisibility() == View.GONE) {
                    showFaceViewGroup();
                }else {
                    hideFaceViewGroup();
                }
                break;
            case R.id.iv_img:
                if (mAdapter != null) {
                    mAdapter.openImgSelect();
                }
                break;
            case R.id.iv_video:
                if (mAdapter != null) {
                    mAdapter.openVideoSelect();
                }
                break;
        }
    }

    public void hideSoftInput() {
        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        et_input.clearFocus();
        more_container.setVisibility(View.GONE);
    }

    private void hideFaceViewGroup() {
        more_container.setVisibility(View.GONE);
        et_input.requestFocus();
        imm.showSoftInput(et_input, 0);
    }

    private void showFaceViewGroup() {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        hideSoftInput();
        more_container.setVisibility(View.VISIBLE);
        et_input.requestFocus();
        mFaceFragment.setListener(new FaceFragment.OnEmojiClickListener() {
            @Override
            public void onEmojiDelete() {
                int index = et_input.getSelectionStart();
                Editable editable = et_input.getText();
                boolean isFace = false;
                if (index <= 0) {
                    return;
                }
                if (editable.charAt(index - 1) == ']') {
                    for (int i = index - 2; i >= 0; i--) {
                        if (editable.charAt(i) == '[') {
                            String faceChar = editable.subSequence(i, index).toString();
                            if (FaceManager.isFaceChar(faceChar)) {
                                editable.delete(i, index);
                                isFace = true;
                            }
                            break;
                        }
                    }
                }
                if (!isFace) {
                    editable.delete(index - 1, index);
                }
            }

            @Override
            public void onEmojiClick(Emoji emoji) {
                int index = et_input.getSelectionStart();
                Editable editable = et_input.getText();
                editable.insert(index, emoji.getFilter());
                FaceManager.handlerEmojiText(et_input, editable.toString(), true);
            }

            @Override
            public void onCustomFaceClick(int groupIndex, Emoji emoji) {
//                mMessageHandler.sendMessage(ChatMessageInfoUtil.buildCustomFaceMessage(groupIndex, emoji.getFilter()));
            }
        });
        mFragmentManager.beginTransaction().replace(R.id.more_container, mFaceFragment).commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (more_container.getVisibility() == View.VISIBLE) {
                hideFaceViewGroup();
                return false;
            }else {
                return super.onKeyDown(keyCode, event);
            }
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            List<String> list = Matisse.obtainPathResult(data);
            if (requestCode == 201) {
                if (list != null && list.size() > 0) {
                    mAdapter.setType("0");
                    mAdapter.addItem(list);
                    mImgSize = mImgSize + list.size();
                    compressImage(list);
                }
            } else if (requestCode == 202) {
                if (list != null && list.size() > 0) {
                    mAdapter.setType("1");
                    mAdapter.addVideo(list);
                    doCompressVideo(list);
                }
            }
        }
    }

    private void compressImage(List<String> path) {
        File file = new File(CommonAppConfig.IMAGE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        Luban.with(mActivity)
                .load(path)
                .setTargetDir(CommonAppConfig.IMAGE_PATH)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (file != null) {
                            mTempImgList.add(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    private void doUploadImg() {
        if (mImgSize == mTempImgList.size()) {//确保所有图片压缩完才可以点击确认发布
            mLoadingDialog.show();
            for (int i = 0; i < mTempImgList.size(); i++) {
                AppManager.mContext.getUpLoadManager().put(mTempImgList.get(i), null, mToken, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        try {
                            if (info.isOK()) {
                                if (!TextUtils.isEmpty(response.getString("key"))) {
                                    mImgList.add(response.getString("key"));
                                    if (mImgList.size() == mTempImgList.size()) {
                                        mvpPresenter.doPublish(et_input.getText().toString(), com.alibaba.fastjson.JSONObject.toJSONString(mImgList));
                                    }
                                }
                            } else {
                                ToastUtil.show(getString(R.string.upload_failed));
                            }
                        } catch (JSONException e) {
                            mLoadingDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, null);
            }
        }
    }

    private void doUploadVideo() {
        mLoadingDialog.show();
        AppManager.mContext.getUpLoadManager().put(videoPath, null, mToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                //res包含hash、key等信息，具体字段取决于上传策略的设置
                try {
                    if (info.isOK()) {
                        if (!TextUtils.isEmpty(response.getString("key"))) {
                            VideoBean videoBean = new VideoBean();
                            videoBean.setImg(mCoverImg);
                            videoBean.setVideo(response.getString("key"));
                            List<VideoBean> list = new ArrayList<>();
                            list.add(videoBean);
                            mvpPresenter.doPublish(et_input.getText().toString(), com.alibaba.fastjson.JSONObject.toJSONString(list));
                        }
                    } else {
                        mLoadingDialog.dismiss();
                        ToastUtil.show(getString(R.string.upload_failed));
                    }
                } catch (JSONException e) {
                    mLoadingDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }, null);
    }

    public void addCover(File file) {
        File tempFile = new File(CommonAppConfig.getInstance().getImagePath(this));
        if (!tempFile.exists()) {
            tempFile.mkdir();
        }
        Luban.with(this)
                .load(file)
                .setTargetDir(CommonAppConfig.getInstance().getImagePath(this))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (file != null) {
                            AppManager.mContext.getUpLoadManager().put(file, null, mToken, new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    dismissLoadingDialog();
                                    //res包含hash、key等信息，具体字段取决于上传策略的设置
                                    try {
                                        if (info.isOK()) {
                                            if (!TextUtils.isEmpty(response.getString("key"))) {
                                                mCoverImg = response.getString("key");
                                            }
                                        } else {
                                            ToastUtil.show(getString(R.string.upload_failed));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissLoadingDialog();
                    }
                }).launch();
    }

    private void doCompressVideo(List<String> list) {
        mLoadingDialog.show();
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(list.get(0));
        int originWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
        int originHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
        int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        final String outputPath = CommonAppConfig.VIDEO_PATH + "cricket_" + System.currentTimeMillis() + ".mp4";
        PLShortVideoTranscoder mShortVideoTranscoder = new PLShortVideoTranscoder(this, list.get(0), outputPath);
        int transcodingBitrateLevel = 3;
        mShortVideoTranscoder.transcode(originWidth, originHeight, getEncodingBitrateLevel(transcodingBitrateLevel), new PLVideoSaveListener() {
            @Override
            public void onSaveVideoSuccess(String s) {
                mLoadingDialog.dismiss();
                videoPath = outputPath;
            }

            @Override
            public void onSaveVideoFailed(final int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.dismiss();
                        switch (i) {
                            case ERROR_NO_VIDEO_TRACK:
                                ToastUtil.show(getString(R.string.publish_upload_file_fail_one));
                                break;
                            case ERROR_SRC_DST_SAME_FILE_PATH:
                                ToastUtil.show(getString(R.string.publish_upload_file_fail_two));
                                break;
                            case ERROR_LOW_MEMORY:
                                ToastUtil.show(getString(R.string.publish_upload_file_fail_three));
                                break;
                            default:
                                ToastUtil.show("transcode failed: " + i);
                        }
                    }
                });
            }

            @Override
            public void onSaveVideoCanceled() {
            }

            @Override
            public void onProgressUpdate(float v) {

            }
        });
    }

    /**
     * 设置压缩质量
     *
     * @param position
     * @return
     */
    private int getEncodingBitrateLevel(int position) {
        return ENCODING_BITRATE_LEVEL_ARRAY[position];
    }

    /**
     * 选的越高文件质量越大，质量越好,对应压缩后尺寸更大
     */
    public static final int[] ENCODING_BITRATE_LEVEL_ARRAY = {
            500 * 1000,
            800 * 1000,
            1000 * 1000,
            1200 * 1000,
            1600 * 1000,
            2000 * 1000,
            2500 * 1000,
            4000 * 1000,
            8000 * 1000,
    };
}
