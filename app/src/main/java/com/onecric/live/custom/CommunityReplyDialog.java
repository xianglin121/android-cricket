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
import com.onecric.live.activity.CommunityCommentActivity;
import com.onecric.live.adapter.AnchorMovingReplyAdapter;
import com.onecric.live.model.CommunityBean;
import com.onecric.live.model.MovingBean;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.GlideUtil;
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
public class CommunityReplyDialog extends Dialog implements View.OnClickListener {
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_like;
    private ImageView iv_like;
    private TextView tv_content;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_reply;
    private AnchorMovingReplyAdapter mAdapter;

    private CommunityBean mCommunityBean;

    private int mPage = 1;

    public CommunityReplyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_moving_comment_reply);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_like = findViewById(R.id.tv_like);
        iv_like = findViewById(R.id.iv_like);
        tv_content = findViewById(R.id.tv_content);
        smart_rl = findViewById(R.id.smart_rl);
        rv_reply = findViewById(R.id.rv_reply);

        findViewById(R.id.ll_reply_input).setOnClickListener(this);
        findViewById(R.id.ll_like).setOnClickListener(this);

        MaterialHeader materialHeader = new MaterialHeader(getContext());
        materialHeader.setColorSchemeColors(getContext().getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(getContext()));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getList(mPage, mCommunityBean.getId());
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

    public void setInfo(CommunityBean bean) {
        if (bean != null) {
            mCommunityBean = bean;
            GlideUtil.loadUserImageDefault(getContext(), bean.getAvatar(), iv_avatar);
            if (!TextUtils.isEmpty(bean.getUser_nickname())) {
                tv_name.setText(bean.getUser_nickname());
            }
            if (!TextUtils.isEmpty(bean.getContent())) {
                SpannableStringBuilder sp = FaceManager.handlerEmojiText(bean.getContent());
                tv_content.setText(sp);
            }
            tv_like.setText(String.valueOf(bean.getLike()));
            updateList(bean.getId());
        }
    }

    public void updateList(int id) {
        mPage = 1;
        getList(mPage, id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_reply_input:
                if (scanForActivity(getContext()) instanceof CommunityCommentActivity) {
                    ((CommunityCommentActivity)scanForActivity(getContext())).showInputDialog(InputCommentMsgDialogFragment.TYPE_REPLY, mCommunityBean.getId());
                }
                break;
            case R.id.ll_like:
                if (mCommunityBean != null) {
                    int like = mCommunityBean.getLike();
                    if (mCommunityBean.getIs_likes() == 0) {
                        mCommunityBean.setIs_likes(1);
                        iv_like.setSelected(true);
                        like++;
                    }else {
                        mCommunityBean.setIs_likes(0);
                        iv_like.setSelected(false);
                        like--;
                    }
                    mCommunityBean.setLike(like);
                    tv_like.setText(String.valueOf(like));
                    if (scanForActivity(getContext()) instanceof CommunityCommentActivity) {
                        ((CommunityCommentActivity)scanForActivity(getContext())).doLike(mCommunityBean.getId());
                    }
                }
                break;
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

    private void getList(int page, int id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("page", page);
        jsonObject.put("id", id);
        ApiClient.retrofit().create(ApiStores.class)
                .getCommunityReplyList(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data, String msg) {
                        List<MovingBean> list = JSONObject.parseArray(JSONObject.parseObject(data).getString("data"), MovingBean.class);
                        mPage++;
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
                .doCommunityCommentLike(CommonAppConfig.getInstance().getToken(), getRequestBody(jsonObject))
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
