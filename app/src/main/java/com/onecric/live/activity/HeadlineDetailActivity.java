package com.onecric.live.activity;

import static com.tencent.thumbplayer.core.downloadproxy.api.TPDownloadProxyHelper.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.AppManager;
import com.onecric.live.CommonAppConfig;
import com.onecric.live.Constant;
import com.onecric.live.R;
import com.onecric.live.adapter.LiveMovingCommentAdapter;
import com.onecric.live.adapter.ThemeHeadlineAdapter;
import com.onecric.live.custom.Glide4Engine;
import com.onecric.live.custom.HeadlineCommentReplyDialog;
import com.onecric.live.custom.InputCommentMsgDialogFragment;
import com.onecric.live.model.HeadlineBean;
import com.onecric.live.model.MovingBean;
import com.onecric.live.presenter.theme.HeadlineDetailPresenter;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.util.ToolUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.theme.HeadlineDetailView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import pro.piwik.sdk.extra.TrackHelper;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * ????????????????????????????????????????????????
 * ?????????2021/12/23
 * ????????????
 */
public class HeadlineDetailActivity extends MvpActivity<HeadlineDetailPresenter> implements HeadlineDetailView, View.OnClickListener {
    public static void forward(Context context, int id) {
        Intent intent = new Intent(context, HeadlineDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private int mId;


    private NestedScrollView scroll_view;
    private ConstraintLayout cl_title;
    private TextView tv_title;
    private TextView tv_date;
    //    private ImageView iv_avatar;
//    private ButtonFollowView iv_follow;
//    private ImageView iv_title_avatar;
//    private ButtonFollowView iv_title_follow;
//    private TextView tv_name;
    private TextView tv_title_name;
    private WebView wv_content;
    private RecyclerView rv_article;
    private ThemeHeadlineAdapter mAdapter;
    private TextView tv_time_sort;
    private TextView tv_hot_sort;
    private SmartRefreshLayout smart_rl;
    private RecyclerView rv_comment;
    private LiveMovingCommentAdapter mCommentAdapter;
    private ImageView iv_like;
    private TextView tv_like;
    private ImageView iv_collect;
    private TextView tv_pre;

    private InputCommentMsgDialogFragment inputCommentMsgDialog;
    private HeadlineCommentReplyDialog replyDialog;
    private String mToken;
    private List<String> mImgList = new ArrayList<>();

    private int mOrder;//0???????????? 1????????????
    private int mPage = 1;

    private HeadlineBean mModel;

    @Override
    public boolean getStatusBarTextColor() {
        return true;
    }

    @Override
    protected HeadlineDetailPresenter createPresenter() {
        return new HeadlineDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_headline_detail_new;
    }

    @Override
    protected void initView() {
        mId = getIntent().getIntExtra("id", 0);
//        tv_name = findViewById(R.id.tv_name);
        scroll_view = findViewById(R.id.scroll_view);
        cl_title = findViewById(R.id.cl_title);

        tv_date = findViewById(R.id.tv_date);
        wv_content = findViewById(R.id.wv_content);
        rv_article = findViewById(R.id.rv_article);
        tv_time_sort = findViewById(R.id.tv_time_sort);
        tv_hot_sort = findViewById(R.id.tv_hot_sort);
        smart_rl = findViewById(R.id.smart_rl);
        rv_comment = findViewById(R.id.rv_comment);
        iv_like = findViewById(R.id.iv_like);
        tv_like = findViewById(R.id.tv_like);
        tv_title = findViewById(R.id.tv_title);
        iv_collect = findViewById(R.id.iv_collect);
        tv_title_name = findViewById(R.id.tv_title_name);
        tv_pre = findViewById(R.id.tv_pre);
//        iv_avatar = findViewById(R.id.iv_avatar);
//        iv_follow = findViewById(R.id.iv_follow);
//        iv_title_follow = findViewById(R.id.iv_title_follow);
//        iv_title_avatar = findViewById(R.id.iv_title_avatar);
//        iv_title_avatar.setOnClickListener(this);
//        iv_follow.setOnClickListener(this);
//        iv_title_follow.setOnClickListener(this);
//        iv_avatar.setOnClickListener(this);
        tv_time_sort.setOnClickListener(this);
        tv_hot_sort.setOnClickListener(this);
        iv_collect.setOnClickListener(this);
        findViewById(R.id.ll_input).setOnClickListener(this);
        findViewById(R.id.ll_like).setOnClickListener(this);
        tv_title_name.setOnClickListener(this);

/*        scroll_view.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > cl_title.getHeight()) {
                    iv_avatar.setVisibility(View.VISIBLE);
                    tv_name.setVisibility(View.VISIBLE);
                    iv_follow.setVisibility(View.VISIBLE);
                } else {
                    iv_avatar.setVisibility(View.GONE);
                    tv_name.setVisibility(View.GONE);
                    iv_follow.setVisibility(View.GONE);
                }
            }
        });*/

        //?????????????????????
        replyDialog = new HeadlineCommentReplyDialog(this, R.style.dialog);
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

        tv_time_sort.setSelected(true);

        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeColors(getResources().getColor(R.color.c_DC3C23));
        smart_rl.setRefreshHeader(materialHeader);
        smart_rl.setRefreshFooter(new ClassicsFooter(this));
        smart_rl.setEnableRefresh(false);
        smart_rl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mvpPresenter.getInfo(false, mPage, mOrder, mId);
            }
        });

        mCommentAdapter = new LiveMovingCommentAdapter(R.layout.item_live_moving_comment, new ArrayList<>());
        mCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_reply) {
                    if (replyDialog != null) {
                        replyDialog.setInfo(mCommentAdapter.getItem(position));
                        replyDialog.show();
                    }
                } else if (view.getId() == R.id.ll_like) {
                    MovingBean item = mCommentAdapter.getItem(position);
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

        mvpPresenter.getInfo(true, 1, mOrder, mId);
        mvpPresenter.getToken();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_title_name:
            case R.id.iv_avatar:
            case R.id.iv_title_avatar:
                if (TextUtils.isEmpty(CommonAppConfig.getInstance().getToken())) {
                    ToastUtil.show(getString(R.string.please_login));
                    return;
                }
                MySpaceActivity.forward(this, mModel.getUid());
                break;
            case R.id.iv_follow:
            case R.id.iv_title_follow:
                if (mModel != null) {
                    if (mModel.getIs_attention() == 0) {
                        mvpPresenter.doFollow(mModel.getUid());
                    }
                }
                break;
            case R.id.ll_input:
                showInputDialog(InputCommentMsgDialogFragment.TYPE_COMMENT, null);
                break;
            case R.id.ll_like:
                if (mModel != null) {
                    int like = mModel.getLikes();
                    if (mModel.getIs_likes() == 1) {
                        like--;
                        mModel.setIs_likes(0);
                        iv_like.setSelected(false);
                    } else {
                        like++;
                        mModel.setIs_likes(1);
                        iv_like.setSelected(true);
                    }
                    mModel.setLikes(like);
                    tv_like.setText(String.valueOf(like));
                    mvpPresenter.doHeadlineLike(mModel.getId());
//                    TrackHelper.track().socialInteraction("Like", "Headlines_user").target("onecric.live.app").with(((AppManager) getApplication()).getTracker());
                }
                break;
            case R.id.iv_collect:
                if (mModel != null) {
                    if (mModel.getIs_favorites() == 1) {
                        mModel.setIs_favorites(0);
                        iv_collect.setSelected(false);
                    } else {
                        mModel.setIs_favorites(1);
                        iv_collect.setSelected(true);
                    }
                    mvpPresenter.doHeadlineCollect(mModel.getId());
//                    TrackHelper.track().socialInteraction("Collect", "Headlines_user").target("onecric.live.app").with(((AppManager) getApplication()).getTracker());
                }
                break;
            case R.id.tv_time_sort:
                if (tv_time_sort.isSelected()) {
                    return;
                }
                mOrder = 0;
                tv_time_sort.setSelected(true);
                tv_hot_sort.setSelected(false);
                mvpPresenter.getInfo(true, 1, mOrder, mId);
                break;
            case R.id.tv_hot_sort:
                if (tv_hot_sort.isSelected()) {
                    return;
                }
                mOrder = 1;
                tv_time_sort.setSelected(false);
                tv_hot_sort.setSelected(true);
                mvpPresenter.getInfo(true, 1, mOrder, mId);
                break;
        }
    }

    @Override
    public void getDataSuccess(HeadlineBean model, List<HeadlineBean> list) {
        if (model != null) {
            mModel = model;
/*          GlideUtil.loadImageDefault(this, model.getAvatar(), iv_avatar);
            GlideUtil.loadImageDefault(this, model.getAvatar(), iv_title_avatar);
            if (!TextUtils.isEmpty(model.getUser_nickname())) {
                tv_name.setText(model.getUser_nickname());
            }
            if (model.getIs_attention() == 1) {
                iv_follow.setFollow(true);
            } else {
                iv_follow.setFollow(false);
            }
            if (model.getIs_attention() == 1) {
                iv_title_follow.setFollow(true);
            } else {
                iv_title_follow.setFollow(false);
            }
            */
            if (!TextUtils.isEmpty(model.getTitle())) {
                tv_title.setText(model.getTitle());
            }

            if (!TextUtils.isEmpty(model.getUser_nickname())) {
                tv_title_name.setText(model.getUser_nickname());
            }
            if (!TextUtils.isEmpty(model.getAddtime())) {
                tv_date.setText(" ??? Updated on " + model.getAddtime());
            }

            if (!TextUtils.isEmpty(model.getContent())) {
                wv_content.getSettings().setJavaScriptEnabled(true);//??????JS??????
                String htmlPart1 = "<!DOCTYPE HTML html>\n" +
                        "<head><meta charset=\"utf-8\"/>\n" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no\"/>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<style> \n" +
                        "@font-face {\n" +
                        "    font-family: 'serif';\n" +
                        "    src: url('fonts/Serif.ttf');\n" +
                        "}\n" +
                        "\n" +
                        "body{font-family: 'serif';margin: 0;}" +
                        "img{width:100%!important;height:auto!important;margin: 0;border-radius:0;}\n" +
                        "section{line-height:170%;font-size:100%;text-color:#333333;margin: 0px 15px 0px 15px;}\n" +
                        "a:link{color:#1866DB;text-decoration:none;}\n" +
                        " </style>";
                String htmlPart2 = "</body></html>";

/*                //fixme 1 ???????????????????????????
                String replaceStr = model.getContent() ;
                //????????????????????????
                if(replaceStr.indexOf("@B0$")!=-1){
                    replaceStr = replaceStr.replace("@B0$","<b>In a Nutshell</b>");
                }

                if(replaceStr.indexOf("@B1$")!=-1){
                    replaceStr = replaceStr.replace("@B1$","<b>Brief Scores</b>");
                }

                //?????????????????????
                if(replaceStr.indexOf("@L0$")!=-1){
                    replaceStr = replaceStr.replace("@L0$","<a href=\"app://player_profile\">greatest highs</a>");
                }

                if(replaceStr.indexOf("@L1$")!=-1){
                    replaceStr = replaceStr.replace("@L1$","<a href=\"app://cricket_detail\">thumping 221-run victory</a>");
                }

                if(replaceStr.indexOf("@L2$")!=-1){
                    replaceStr = replaceStr.replace("@L2$","<a href=\"app://cricket_league\">Abu Dhabi T10 League</a>");
                }

                if(replaceStr.indexOf("@L3$")!=-1){
                    replaceStr = replaceStr.replace("@L3$","<a href=\"app://cricket_team\">hard-fought</a>");
                }

                String html = htmlPart1 + replaceStr + htmlPart2;
                wv_content.setWebViewClient (new WebViewClient() {
                    public boolean shouldOverrideUrlLoading (WebView view, String url) {
                        if(url.indexOf("app://player_profile")!=-1){
                            PlayerProfileActivity.forward(mActivity, 686858);//?????? getPlayer_id() 686858  ????????????????????????id????????????
                        }else if(url.indexOf("app://cricket_detail")!=-1){
                            CricketDetailActivity.forward(mActivity, 37493661);//?????? getMatch_id() 37493661
                        }else if(url.indexOf("app://cricket_league")!=-1){
                            CricketInnerActivity.forward(mActivity, "Abu Dhabi T10 League", "t10", 38573);//?????? getTournament_name() getType() getTournament_id()
                        }else if(url.indexOf("app://cricket_team")!=-1){
                            CricketTeamsActivity.forward(mActivity, "", 0);//??????
                        }
                        return true;
                    }
                });*/


                //??????<img?????????section?????????border-radius:4px
                StringBuilder builder = new StringBuilder(model.getContent());
                int preIndex = builder.indexOf("<section>");
                if (preIndex != -1) {
                    builder.replace(preIndex, preIndex + "<section>".length(), "");
                }
                int sufIndex = builder.indexOf("</section>");
                if (sufIndex != -1) {
                    builder.replace(sufIndex, sufIndex + "</section>".length(), "");
                }
                int radiusIndex = builder.indexOf("4px");
                if (radiusIndex != -1) {
                    builder.replace(radiusIndex, radiusIndex + 1, "0");
                }

                String html = htmlPart1 + builder + htmlPart2;
//              String html = htmlPart1 + updateContent(model.getContent()) + htmlPart2;
//              String html = htmlPart1 + model.getContent() + htmlPart2;
                wv_content.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
            }
            if (list != null) {
                mAdapter = new ThemeHeadlineAdapter(R.layout.item_theme_headline, list);
                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        HeadlineDetailActivity.forward(mActivity, mAdapter.getItem(position).getId());
                    }
                });
                rv_article.setLayoutManager(new LinearLayoutManager(mActivity));
                rv_article.setAdapter(mAdapter);
            }
            tv_like.setText(String.valueOf(model.getLikes()));
            if (model.getIs_likes() == 1) {
                iv_like.setSelected(true);
            } else {
                iv_like.setSelected(false);
            }
            if (model.getIs_favorites() == 1) {
                iv_collect.setSelected(true);
            } else {
                iv_collect.setSelected(false);
            }
        }
    }

    //AllCric?????? ?????????????????????????????????
/*    private String updateContent(String content){
        String suffixTag = "/section>";
        StringBuilder builder = new StringBuilder(content);

        //??????????????????
        int end = builder.indexOf(suffixTag) + suffixTag.length();
        String imgStr = builder.substring(0,end) + "</br><section>";
        builder.replace(0,end,"").toString();

        //???????????????????????????????????????????????????????????????.??????
        String sectionArr[] = builder.toString().split(suffixTag);
        int i = 0;
        for(;i<sectionArr.length;i++){
            if(!sectionArr[i].contains("<img") && !sectionArr[i].contains("@B")){
                break;
            }
        }
        int oneIndex = builder.indexOf(sectionArr[i]);
//        builder.insert(builder.indexOf(". ",oneIndex)+2,imgStr);

        //?????????????????????
        Typeface tf = Typeface.createFromAsset(HeadlineDetailActivity.this.getResources().getAssets(), "fonts/ritalic.ttf");
        tv_pre.setTypeface(tf);
        tv_pre.setText(Html.fromHtml(builder.substring(0,builder.indexOf(". ",oneIndex)+2)));

        //?????????????????????????????????br
        builder.replace(0,builder.indexOf(". ",oneIndex)+2,"");
        builder.replace(builder.indexOf("<br>"),builder.indexOf("<br>")+4,"");
        builder.insert(0,imgStr);
        return builder.toString();
    }*/

    @Override
    public void getTokenSuccess(String token) {
        mToken = token;
    }

    @Override
    public void getList(boolean isRefresh, List<MovingBean> list) {
        if (isRefresh) {
            mPage = 2;
            if (list == null) {
                list = new ArrayList<>();
            }
            mCommentAdapter.setNewData(list);
            int[] intArray4 = new int[2];
            rv_comment.getLocationOnScreen(intArray4);//?????????View????????????????????????
            scrollByDistance(intArray4[1]);
        } else {
            mPage++;
            if (list != null && list.size() > 0) {
                smart_rl.finishLoadMore();
                mCommentAdapter.addData(list);
            } else {
                smart_rl.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void doFollowSuccess() {
/*        if (mModel.getIs_attention() == 1) {
            mModel.setIs_attention(0);
            iv_follow.setFollow(false);
            iv_title_follow.setFollow(false);
        } else {
            mModel.setIs_attention(1);
            iv_follow.setFollow(true);
            iv_title_follow.setFollow(true);
        }*/
    }

    public void showInputDialog(int type, Integer cid) {
        if (inputCommentMsgDialog == null) {
            inputCommentMsgDialog = new InputCommentMsgDialogFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        if (cid != null) {
            bundle.putInt("cid", cid);
        }
        inputCommentMsgDialog.setArguments(bundle);
        inputCommentMsgDialog.show(getSupportFragmentManager(), "InputCommentMsgDialogFragment");
    }

    public void doComment(String content, Integer cid) {
        mvpPresenter.doComment(mId, content, cid, mImgList);
    }

    @Override
    public void doCommentSuccess(Integer cid) {
        mImgList.clear();
        if (cid != null) {
            updateReplyDialog(cid);
            for (int i = 0; i < mCommentAdapter.getItemCount(); i++) {
                MovingBean item = mCommentAdapter.getItem(i);
                if (item.getId() == cid) {
                    int comment = item.getComment();
                    comment++;
                    item.setComment(comment);
                    mCommentAdapter.notifyItemChanged(i);
                    int[] intArray4 = new int[2];
                    rv_comment.getLocationOnScreen(intArray4);//?????????View????????????????????????
                    scrollByDistance(intArray4[1]);
                    break;
                }
            }
        } else {
            mvpPresenter.getInfo(true, 1, mOrder, mId);
        }
    }


    private int nestedScrollViewTop;

    public void scrollByDistance(int dy) {
        if (nestedScrollViewTop == 0) {
            int[] intArray = new int[2];
            scroll_view.getLocationOnScreen(intArray);
            nestedScrollViewTop = intArray[1];
        }
        int distance = dy - nestedScrollViewTop;//????????????nestedScrollView????????????????????????
        scroll_view.fling(distance);//??????????????????????????????
        scroll_view.smoothScrollBy(0, distance);
    }


    //?????????????????????????????????
    public void updateReplyDialog(int cid) {
        replyDialog.updateList(cid);
    }

    public void doLike(int id) {
        mCommentAdapter.notifyDataSetChanged();
        mvpPresenter.doLike(id);
    }

    @Override
    public void getDataSuccess(HeadlineBean model) {

    }

    @Override
    public void getDataFail(String msg) {

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
        if (data == null) {
            return;
        }
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
                                    //res??????hash???key??????????????????????????????????????????????????????
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
