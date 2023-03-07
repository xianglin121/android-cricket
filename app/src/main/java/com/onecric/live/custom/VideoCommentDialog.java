package com.onecric.live.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.activity.VideoPagerActivity;
import com.onecric.live.adapter.AnchorMovingReplyAdapter;
import com.onecric.live.adapter.VideoCommentAdapter;
import com.onecric.live.fragment.dialog.LoginDialog;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.MovingBean;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ToastUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/11/18
 */
public class VideoCommentDialog extends Dialog implements View.OnClickListener {
    //头部共用部分
    private ImageView iv_back;
    private TextView tv_title;
    //评论布局
    private SmartRefreshLayout srl_comment;
    private RecyclerView rv_comment;
    private VideoCommentAdapter mCommentAdapter;
    //回复布局
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_like;
    private ImageView iv_like;
    private TextView tv_content;
    private TextView tv_time;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_reply;
    private AnchorMovingReplyAdapter mAdapter;

//    private CommunityBean mCommentBean;
    private int mVideoId;
    private CommunityBean mReplyBean;

    private int mCommentPage = 1;
    private int mReplyPage = 1;

    private LoginDialog loginDialog;
    public void setLoginDialog(LoginDialog dialog){
        this.loginDialog = dialog;
    }

    public VideoCommentDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_video_comment);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);

        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        srl_comment = findViewById(R.id.srl_comment);
        rv_comment = findViewById(R.id.rv_comment);
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_like = findViewById(R.id.tv_like);
        iv_like = findViewById(R.id.iv_like);
        tv_content = findViewById(R.id.tv_content);
        tv_time = findViewById(R.id.tv_time);
        smart_rl = findViewById(R.id.smart_rl);
        rv_reply = findViewById(R.id.rv_reply);

        iv_back.setOnClickListener(this);
        findViewById(R.id.ll_reply_input).setOnClickListener(this);
        findViewById(R.id.ll_like).setOnClickListener(this);

        //评论
        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        srl_comment.setRefreshHeader(materialHeader);
        srl_comment.setRefreshFooter(new ClassicsFooter(getContext()));
        srl_comment.setEnableRefresh(false);
        srl_comment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getCommentList(mCommentPage, mVideoId);
            }
        });
        mCommentAdapter = new VideoCommentAdapter(R.layout.item_video_comment, new ArrayList<>());
        mCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CommunityBean item = mCommentAdapter.getItem(position);
                if (view.getId() == R.id.ll_like) {
                    int like = item.getLike();
                    if (item.getIs_likes() == 0) {
                        item.setIs_likes(1);
                        like++;
                    }else {
                        item.setIs_likes(0);
                        like--;
                    }
                    item.setLike(like);
                    mCommentAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                    doLike(item.getId());
                }
                if (view.getId() == R.id.tv_reply) {
                    setReplyInfo(item);
                }
            }
        });
        rv_comment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_comment.setAdapter(mCommentAdapter);

        //回复
        MaterialHeader materialHeader2 = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader2);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mReplyBean != null) {
                    getReplyList(mReplyPage, mReplyBean.getId());
                }
            }
        });
        mAdapter = new AnchorMovingReplyAdapter(R.layout.item_anchor_moving_reply, new ArrayList<>());
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.ll_like) {
                    MovingBean item = mAdapter.getItem(position);
                    int like = item.getLike();
                    if (item.getIs_likes() == 0) {
                        item.setIs_likes(1);
                        like++;
                    }else {
                        item.setIs_likes(0);
                        like--;
                    }
                    item.setLike(like);
                    mAdapter.notifyItemChanged(position, Constant.PAYLOAD);
                    doLike(item.getId());
                }
            }
        });
        rv_reply.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_reply.setAdapter(mAdapter);
    }

    public void updateList(int id) {
        mVideoId = id;
        mCommentPage = 1;
        getCommentList(mCommentPage, id);
        //如果一开始在回复页先退回评论
        backComment();
    }

    public void updateReplyList(int id) {
        for (int i = 0; i < mCommentAdapter.getData().size(); i++) {
            CommunityBean communityBean = mCommentAdapter.getData().get(i);
            if (communityBean.getId() == id) {
                int comment = communityBean.getComment();
                comment++;
                communityBean.setComment(comment);
                mCommentAdapter.notifyItemChanged(i);
                break;
            }
        }
        mReplyPage = 1;
        getReplyList(mReplyPage, id);
    }

    public void setReplyInfo(CommunityBean bean) {
        if (bean != null) {
            srl_comment.setVisibility(View.GONE);
            iv_back.setVisibility(View.VISIBLE);
            findViewById(R.id.ll_reply).setVisibility(View.VISIBLE);
            mReplyBean = bean;
            GlideUtil.loadUserImageDefault(getContext(), bean.getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(bean.getUser_nickname())) {
                tv_name.setText(bean.getUser_nickname());
            }
            if (!TextUtils.isEmpty(bean.getContent())) {
                SpannableStringBuilder sp = FaceManager.handlerEmojiText(bean.getContent());
                tv_content.setText(sp);
            }
            if (!TextUtils.isEmpty(bean.getAddtime())) {
                tv_time.setText(bean.getAddtime());
            }
            if (bean.getIs_likes() == 1) {
                iv_like.setSelected(true);
            }else {
                iv_like.setSelected(false);
            }
            tv_like.setText(String.valueOf(bean.getLike()));
            mReplyPage = 1;
            getReplyList(mReplyPage, bean.getId());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_reply_input:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getUid())) {
                    if(loginDialog!=null){
                        loginDialog.show();
                    }else{
                        ToastUtil.show(getContext().getString(R.string.please_login));
                    }
                    return;
                }
                if (scanForActivity(getContext()) instanceof VideoPagerActivity) {
                    if (mReplyBean != null) {
                        ((VideoPagerActivity)scanForActivity(getContext())).showInputDialog(InputCommentMsgDialogFragment.TYPE_REPLY, mReplyBean.getId());
                    }else {
                        ((VideoPagerActivity)scanForActivity(getContext())).showInputDialog(InputCommentMsgDialogFragment.TYPE_COMMENT, null);
                    }
                }
                break;
            case R.id.ll_like:
                if (mReplyBean != null) {
                    int like = mReplyBean.getLike();
                    if (mReplyBean.getIs_likes() == 0) {
                        mReplyBean.setIs_likes(1);
                        iv_like.setSelected(true);
                        like++;
                    }else {
                        mReplyBean.setIs_likes(0);
                        iv_like.setSelected(false);
                        like--;
                    }
                    mReplyBean.setLike(like);
                    tv_like.setText(String.valueOf(like));
                    doLike(mReplyBean.getId());
                }
                break;
            case R.id.iv_back:
                backComment();
                break;
        }
    }

    private void backComment() {
        if (mReplyBean != null) {
            mReplyBean = null;
            mReplyPage = 1;
            srl_comment.setVisibility(View.VISIBLE);
            findViewById(R.id.ll_reply).setVisibility(View.GONE);
            iv_back.setVisibility(View.GONE);
            tv_title.setText(mCommentAdapter.getItemCount() + " " + getContext().getString(R.string.replies));
        }
    }

    private static AppCompatActivity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof AppCompatActivity)
            return (AppCompatActivity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }

    private void getCommentList(int page, int id) {
        ApiClient.retrofit().create(ApiStores.class)
                .getVideoCommentList(CommonAppConfig.getInstance().getToken(), page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<CommunityBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), CommunityBean.class);
                        tv_title.setText(list.size() + " " +getContext().getString(R.string.comments));
                        mCommentPage++;
                        if (list != null && list.size() > 0) {
                            srl_comment.finishLoadMore();
                            mCommentAdapter.setNewData(list);
                        }else {
                            srl_comment.finishLoadMoreWithNoMoreData();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onError(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    private void getReplyList(int page, int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page", page);
        jsonObject.put("id", id);
        ApiClient.retrofit().create(ApiStores.class)
                .getVideoReplyList(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<MovingBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), MovingBean.class);
                        tv_title.setText(list.size() + " " + getContext().getString(R.string.replies));
                        mReplyPage++;
                        if (list != null && list.size() > 0) {
                            smart_rl.finishLoadMore();
                            mAdapter.setNewData(list);
                        }else {
                            smart_rl.finishLoadMoreWithNoMoreData();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onError(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public void doLike(int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        ApiClient.retrofit().create(ApiStores.class)
                .doVideoCommentLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                    }

                    @Override
                    public void onFailure(String msg) {

                    }

                    @Override
                    public void onError(String msg) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    public RequestBody getRequestBody(JSONObject jsonObject) {
        MediaType CONTENT_TYPE = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(CONTENT_TYPE, jsonObject.toString());
        return requestBody;
    }
}
