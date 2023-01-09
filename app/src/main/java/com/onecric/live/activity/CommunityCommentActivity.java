package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.adapter.CommunityCommentAdapter;
import com.onecric.live.adapter.ImageAdapter;
import com.onecric.live.custom.CommunityReplyDialog;
import com.onecric.live.custom.Glide4Engine;
import com.onecric.live.custom.InputCommentMsgDialogFragment;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.JsonBean;
import com.onecric.live.presenter.CommunityCommentPresenter;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.live.CommunityCommentView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class CommunityCommentActivity extends MvpActivity<CommunityCommentPresenter> implements CommunityCommentView, View.OnClickListener {

    private NestedScrollView nsv;

    public static void forward(Context context, int anchorId, int communityId) {
        Intent intent = new Intent(context, CommunityCommentActivity.class);
        intent.putExtra("anchorId", anchorId);
        intent.putExtra("communityId", communityId);
        context.startActivity(intent);
    }

    private int mAnchorId;
    private int mCommunityId;
    private ImageView iv_title_icon;
    private TextView tv_title_name;
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_date;
    private LinearLayout ll_follow;
    private TextView tv_follow;
    private ImageView iv_icon;
    private TextView tv_content;
    private ImageView iv_cover;
    private ImageView icon_play;
    private RecyclerView rv_image;
    private ImageView iv_community_icon;
    private TextView tv_community_name;
    private TextView tv_read_count;
    private ImageView iv_like;
    private TextView tv_like;
    private ImageView iv_collect;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_comment;
    private CommunityCommentAdapter mCommentAdapter;

    private InputCommentMsgDialogFragment inputCommentMsgDialog;
    private CommunityReplyDialog replyDialog;
    private String mToken;
    private List<String> mImgList = new ArrayList<>();

    private CommunityBean mCommunityBean;
    private int mPage = 1;

    @Override
    protected CommunityCommentPresenter createPresenter() {
        return new CommunityCommentPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_community_comment;
    }

    @Override
    protected void initView() {
        mAnchorId = getIntent().getIntExtra("anchorId", 0);
        mCommunityId = getIntent().getIntExtra("communityId", 0);
        nsv = findViewById(R.id.nsv);
        iv_title_icon = findViewById(R.id.iv_title_icon);
        tv_title_name = findViewById(R.id.tv_title_name);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_date = findViewById(R.id.tv_date);
        ll_follow = findViewById(R.id.ll_follow);
        tv_follow = findViewById(R.id.tv_follow);
        iv_icon = findViewById(R.id.iv_icon);
        tv_content = findViewById(R.id.tv_content);
        iv_cover = findViewById(R.id.iv_cover);
        icon_play = findViewById(R.id.icon_play);
        rv_image = findViewById(R.id.rv_image);
        iv_community_icon = findViewById(R.id.iv_community_icon);
        tv_community_name = findViewById(R.id.tv_community_name);
        tv_read_count = findViewById(R.id.tv_read_count);
        iv_like = findViewById(R.id.iv_like);
        tv_like = findViewById(R.id.tv_like);
        iv_collect = findViewById(R.id.iv_collect);
        smart_rl = findViewById(R.id.smart_rl);
        rv_comment = findViewById(R.id.rv_comment);

        ll_follow.setOnClickListener(this);
        iv_collect.setOnClickListener(this);
        findViewById(R.id.ll_community).setOnClickListener(this);
        findViewById(R.id.ll_input).setOnClickListener(this);
        findViewById(R.id.ll_like).setOnClickListener(this);

        //初始化回复弹窗
        replyDialog = new CommunityReplyDialog(this, R.style.dialog);
    }

    @Override
    protected void initData() {
        if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
            findViewById(R.id.fl_board).setVisibility(View.VISIBLE);
            findViewById(R.id.fl_board).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.forward(mActivity);
                }
            });
        }
        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(this));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getCommunityInfo(mPage, mCommunityId);
            }
        });

        mCommentAdapter = new CommunityCommentAdapter(R.layout.item_live_moving_comment, new ArrayList<>());
        mCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_reply) {
                    if (replyDialog != null) {
                        replyDialog.setInfo(mCommentAdapter.getItem(position));
                        replyDialog.show();
                    }
                } else if (view.getId() == R.id.ll_like) {
                    CommunityBean item = mCommentAdapter.getItem(position);
                    int like = item.getLike();
                    if (item.getIs_likes() == 0) {
                        item.setIs_likes(1);
                        like++;
                    } else {
                        item.setIs_likes(0);
                        like--;
                    }
                    item.setLike(like);
                    mCommentAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                    mvpPresenter.doLike(item.getId());
                }
            }
        });
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setAdapter(mCommentAdapter);

        mvpPresenter.getCommunityInfo(1, mCommunityId);
        mvpPresenter.getToken();
    }

    //更新回复弹窗的列表数据
    public void updateReplyDialog(int cid) {
        replyDialog.updateList(cid);
    }

    public void doLike(int id) {
        mCommentAdapter.notifyDataSetChanged();
        mvpPresenter.doLike(id);
    }

    @Override
    public void getDataSuccess(JsonBean model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    private int nestedScrollViewTop;

    public void scrollByDistance(int dy) {
        if (nestedScrollViewTop == 0) {
            int[] intArray = new int[2];
            nsv.getLocationOnScreen(intArray);
            nestedScrollViewTop = intArray[1];
        }
        int distance = dy - nestedScrollViewTop;//必须算上nestedScrollView本身与屏幕的距离
        nsv.fling(distance);//添加上这句滑动才有效
        nsv.smoothScrollBy(0, distance);
    }


    @Override
    public void doCommentSuccess(Integer cid) {
        mImgList.clear();
        if (cid != null) {
            updateReplyDialog(cid);
            for (int i = 0; i < mCommentAdapter.getItemCount(); i++) {
                CommunityBean item = mCommentAdapter.getItem(i);
                if (item.getId() == cid) {
                    int comment = item.getComment();
                    comment++;
                    item.setComment(comment);
                    mCommentAdapter.notifyItemChanged(i);
                    int[] intArray4 = new int[2];
                    rv_comment.getLocationOnScreen(intArray4);//测量某View相对于屏幕的距离
                    scrollByDistance(intArray4[1]);
                    break;
                }
            }
        } else {
            mvpPresenter.getCommunityInfo(1, mCommunityId);
        }
    }

    @Override
    public void getData(CommunityBean bean) {
        if (mPage == 1) {
            if (bean != null) {
                mCommunityBean = bean;
                GlideUtil.loadImageDefault(this, bean.getClassification_icon(), iv_title_icon);
                if (!TextUtils.isEmpty(bean.getClassification_name())) {
                    tv_title_name.setText(bean.getClassification_name());
                }
                GlideUtil.loadUserImageDefault(this, bean.getAvatar(), iv_avatar);
                if (!TextUtils.isEmpty(bean.getUser_nickname())) {
                    tv_name.setText(bean.getUser_nickname());
                }
                if (!TextUtils.isEmpty(bean.getAddtimeString())) {
                    tv_date.setText(bean.getAddtimeString());
                }
                if (!TextUtils.isEmpty(bean.getTitle())) {
                    SpannableStringBuilder sp = FaceManager.handlerEmojiText(bean.getTitle());
                    tv_content.setText(sp);
                }
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
                    if (bean.getUid() == Integer.valueOf(CommonAppConfig.getInstance().getUid())) {
                        ll_follow.setVisibility(View.GONE);
                    } else {
                        ll_follow.setVisibility(View.VISIBLE);
                    }
                } else {
                    ll_follow.setVisibility(View.VISIBLE);
                }
                if (bean.getIs_attention() == 1) {
                    ll_follow.setBackgroundResource(R.drawable.bg_live_followed_two);
                    tv_follow.setText(getString(R.string.followed));
                    iv_icon.setVisibility(View.GONE);
                } else {
                    ll_follow.setBackgroundResource(R.mipmap.bg_live_follow_two);
                    tv_follow.setText(getString(R.string.follow));
                    iv_icon.setVisibility(View.VISIBLE);
                }
                if (bean.getIs_flie_type() == 0) {
                    iv_cover.setVisibility(View.GONE);
                    icon_play.setVisibility(View.GONE);
                    rv_image.setVisibility(View.VISIBLE);
                    if (bean.getImg() != null) {
                        rv_image.setLayoutManager(new GridLayoutManager(this, 3));
                        rv_image.setAdapter(new ImageAdapter(this, bean.getImg()));
                    }
                } else {
                    iv_cover.setVisibility(View.VISIBLE);
                    icon_play.setVisibility(View.VISIBLE);
                    rv_image.setVisibility(View.GONE);
                    if (bean.getVideo() != null && bean.getVideo().size() > 0) {
                        GlideUtil.loadImageDefault(this, bean.getVideo().get(0).getImg(), iv_cover);
                    }
                    iv_cover.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VideoCompletePlayActivity.forward(mActivity, bean.getVideo().get(0).getVideo());
                        }
                    });
                    icon_play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            VideoCompletePlayActivity.forward(mActivity, bean.getVideo().get(0).getVideo());
                        }
                    });
                }
                GlideUtil.loadImageDefault(this, bean.getClassification_icon(), iv_community_icon);
                if (!TextUtils.isEmpty(bean.getClassification_name())) {
                    tv_community_name.setText(bean.getClassification_name());
                }
                tv_read_count.setText(String.valueOf(bean.getClick()));
                tv_like.setText(String.valueOf(bean.getLike()));
                if (bean.getIs_likes() == 1) {
                    iv_like.setSelected(true);
                } else {
                    iv_like.setSelected(false);
                }
                if (bean.getIs_favorites() == 1) {
                    iv_collect.setSelected(true);
                } else {
                    iv_collect.setSelected(false);
                }
            }
        }
    }

    @Override
    public void getTokenSuccess(String token) {
        mToken = token;
    }

    @Override
    public void getList(List<CommunityBean> list) {
        mPage++;
        if (list != null && list.size() > 0) {
            smart_rl.finishLoadMore();
            mCommentAdapter.setNewData(list);
            int[] intArray4 = new int[2];
            rv_comment.getLocationOnScreen(intArray4);//测量某View相对于屏幕的距离
            scrollByDistance(intArray4[1]);
        } else {
            smart_rl.finishLoadMoreWithNoMoreData();
        }
    }

    public void doComment(String content, Integer cid) {
        mvpPresenter.doComment(mCommunityId, content, cid, mImgList);
    }

    public void showInputDialog(int type, Integer cid) {
        inputCommentMsgDialog = new InputCommentMsgDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        if (cid != null) {
            bundle.putInt("cid", cid);
        }
        inputCommentMsgDialog.setArguments(bundle);
        inputCommentMsgDialog.show(getSupportFragmentManager(), "InputCommentMsgDialogFragment");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_input:
                showInputDialog(InputCommentMsgDialogFragment.TYPE_COMMENT, null);
                break;
            case R.id.ll_like:
                int like = mCommunityBean.getLike();
                if (mCommunityBean.getIs_likes() == 1) {
                    like--;
                    mCommunityBean.setIs_likes(0);
                    iv_like.setSelected(false);
                } else {
                    like++;
                    mCommunityBean.setIs_likes(1);
                    iv_like.setSelected(true);
                }
                mCommunityBean.setLike(like);
                tv_like.setText(String.valueOf(like));
                mvpPresenter.doMovingLike(mCommunityId);
                break;
            case R.id.iv_collect:
                if (mCommunityBean.getIs_favorites() == 1) {
                    mCommunityBean.setIs_favorites(0);
                    iv_collect.setSelected(false);
                } else {
                    mCommunityBean.setIs_favorites(1);
                    iv_collect.setSelected(true);
                }
                mvpPresenter.doCommunityCollect(mCommunityId);
                break;
            case R.id.ll_follow:
                if (!TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    if (mCommunityBean.getIs_attention() == 0) {
                        ll_follow.setBackgroundResource(R.drawable.bg_live_followed_two);
                        tv_follow.setText(getString(R.string.followed));
                        iv_icon.setVisibility(View.GONE);
                        mvpPresenter.doFollow(mAnchorId);
                    }
                } else {
                    LoginActivity.forward(mActivity);
                }
                break;
        }
    }

    public void openPicsSelect() {
        if (TextUtils.isEmpty(mToken)) {
            return;
        }
        if (!ToolUtil.checkPermission(this)) {
            return;
        }
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, AppManager.mContext.getPackageName() + ".fileProvider"))
                .maxSelectable(1)
                .gridExpectedSize(DpUtil.dp2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .originalEnable(true)
                .maxOriginalSize(10)
                .showSingleMediaType(true)
                .forResult(205);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 205) {
            if (mImgList.size() == 3) {
                ToastUtil.show(getString(R.string.tip_send_image_fail));
                return;
            }
            List<String> list = Matisse.obtainPathResult(data);
            if (list != null && list.size() > 0) {
                compressImage(list);
            }
        }
    }

    private void compressImage(List<String> path) {
        File file = new File(CommonAppConfig.IMAGE_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        Luban.with(this)
                .load(path)
                .setTargetDir(CommonAppConfig.IMAGE_PATH)
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
                                                mImgList.add(response.getString("key"));
                                                if (inputCommentMsgDialog != null) {
                                                    inputCommentMsgDialog.addImg(file.getPath());
                                                }
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
                    }
                }).launch();
    }
}
