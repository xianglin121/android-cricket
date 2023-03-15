package com.onecric.live.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.MatchSearchResultAdapter;
import com.onecric.live.model.Channel;
import com.onecric.live.model.MatchSearchBean;
import com.onecric.live.presenter.match.SearchMatchPresenter;
import com.onecric.live.util.ToastUtil;
import com.onecric.live.view.MvpActivity;
import com.onecric.live.view.SearchMatchNewView;
import com.onecric.live.view.match.SearchMatchView;

import java.util.ArrayList;
import java.util.List;

public class SearchMatchActivity extends MvpActivity<SearchMatchPresenter> implements SearchMatchView, View.OnClickListener {

    public static void forward(Context context) {
        Intent intent = new Intent(context, SearchMatchActivity.class);
        context.startActivity(intent);
    }

    private EditText et_input;
    private ImageView iv_close;
    private RecyclerView recyclerView_match;
    private RecyclerView recyclerView_tours;
    private MatchSearchResultAdapter mMatchAdapter;
    private MatchSearchResultAdapter mToursAdapter;
    private FrameLayout fl_tab_all;
    private FrameLayout fl_tab_matches;
    private FrameLayout fl_tab_tours;
    private TextView tv_tab_all;
    private TextView tv_tab_matches;
    private TextView tv_tab_tours;
    private TextView tv_chat_info;
    private LinearLayout ll_tab;
    private TextView tv_title_match;
    private TextView tv_title_tours;
    private LinearLayout ll_empty;
    private TextView tv_empty;
    private String searchWord;
    private Drawable drawableArrRight;
    private FrameLayout fl_loading;
    private View view_line;
    private ScrollView sl_main;

    private List<MatchSearchBean> matchsList;
    private List<MatchSearchBean> toursList;
    private int mMPage = 1;
    private int mTPage = 1;

    @Override
    protected SearchMatchPresenter createPresenter() {
        return new SearchMatchPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_match_new;
    }

    @Override
    protected void initView() {
        et_input = findViewById(R.id.et_input);
        iv_close = findViewById(R.id.iv_close);
        recyclerView_match = findViewById(R.id.recyclerView_match);
        recyclerView_tours = findViewById(R.id.recyclerView_tours);
        fl_tab_all = findViewById(R.id.fl_tab_all);
        fl_tab_matches = findViewById(R.id.fl_tab_matches);
        fl_tab_tours = findViewById(R.id.fl_tab_tours);
        tv_tab_all = findViewById(R.id.tv_tab_all);
        tv_tab_matches = findViewById(R.id.tv_tab_matches);
        tv_tab_tours = findViewById(R.id.tv_tab_tours);
        tv_chat_info = findViewById(R.id.tv_chat_info);
        ll_tab = findViewById(R.id.ll_tab);
        tv_title_match = findViewById(R.id.tv_title_match);
        tv_title_tours = findViewById(R.id.tv_title_tours);
        ll_empty = findViewById(R.id.ll_empty);
        tv_empty = findViewById(R.id.tv_empty);
        fl_loading = findViewById(R.id.fl_loading);
        view_line = findViewById(R.id.view_line);
        sl_main = findViewById(R.id.sl_main);

        iv_close.setOnClickListener(this);
        tv_tab_all.setOnClickListener(this);
        tv_tab_matches.setOnClickListener(this);
        tv_tab_tours.setOnClickListener(this);
        tv_title_match.setOnClickListener(this);
        tv_title_tours.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        drawableArrRight = getDrawable(R.mipmap.icon_arrow_right_full);
        drawableArrRight.setBounds(0, 0, drawableArrRight.getMinimumWidth(),drawableArrRight.getMinimumHeight());
        matchsList = new ArrayList<>();
        toursList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“发送”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchWord = et_input.getText().toString();
                    searchContent();
                    return true;
                }
                return false;
            }
        });

        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    iv_close.setVisibility(View.GONE);
                } else {
                    iv_close.setVisibility(View.VISIBLE);
                }
                searchWord = et_input.getText().toString();
                searchContent();
            }
        });

        //搜索出来的内容
        mMatchAdapter = new MatchSearchResultAdapter(R.layout.item_search_matchs,matchsList);
        mMatchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(mMatchAdapter.getItem(position).id!=0){
                    CricketDetailActivity.forward(mActivity, mMatchAdapter.getItem(position).id);
                }
            }
        });
        recyclerView_match.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_match.setAdapter(mMatchAdapter);

        mToursAdapter = new MatchSearchResultAdapter(R.layout.item_search_tours,toursList);
        mToursAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CricketInnerActivity.forward(mActivity, mToursAdapter.getItem(position).name, "1", mToursAdapter.getItem(position).id);
            }
        });
        recyclerView_tours.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_tours.setAdapter(mToursAdapter);

        recyclerView_match.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (tv_tab_all.isSelected() != true){
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastVisibleItem = manager.findLastVisibleItemPosition();
                        int totalItemCount = manager.getItemCount();
                        if (lastVisibleItem == (totalItemCount - 1)) {
                            mMPage++;
                            mvpPresenter.searchMatch(searchWord, 1, mMPage,true);
                        }
                    }
                }
            }
        });

        recyclerView_tours.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (tv_tab_all.isSelected() != true){
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int lastVisibleItem = manager.findLastVisibleItemPosition();
                        int totalItemCount = manager.getItemCount();
                        if (lastVisibleItem == (totalItemCount - 1)) {
                            mTPage++;
                            mvpPresenter.searchMatch(searchWord, 2, mTPage,true);
                        }
                    }
                }
            }
        });

    }

    private void searchContent() {
        mTPage = 1;
        mMPage = 1;
        toursList = null;
        matchsList = null;
        mToursAdapter.setNewData(toursList);
        mMatchAdapter.setNewData(matchsList);
        ll_tab.setVisibility(View.GONE);
        ll_empty.setVisibility(View.GONE);
        sl_main.setVisibility(View.GONE);
        tv_title_match.setVisibility(View.GONE);
        tv_title_tours.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(searchWord) && searchWord.length()>2) {
            tv_chat_info.setVisibility(View.GONE);
            fl_loading.setVisibility(View.VISIBLE);
            mvpPresenter.searchMatch(searchWord,1,mMPage,false);
        }else{
            tv_chat_info.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getDataSuccess(MatchSearchBean bean) {}


    @Override
    public void getDataFail(String msg) {}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                et_input.setText("");
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_tab_all:
                showSelectTabList(0);
                break;
            case R.id.tv_tab_matches:
                showSelectTabList(1);
                break;
            case R.id.tv_tab_tours:
                showSelectTabList(2);
                break;
            case R.id.tv_title_match:
                if(tv_tab_all.isSelected()){
                    showSelectTabList(1);
                }
                break;
            case R.id.tv_title_tours:
                if(tv_tab_all.isSelected()){
                    showSelectTabList(2);
                }
                break;
        }
    }

    private void showSelectTabList(int type){
        tv_tab_all.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_regular));
        tv_tab_matches.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_regular));
        tv_tab_tours.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_regular));
        tv_title_match.setCompoundDrawables(null,null,null,null);
        tv_title_tours.setCompoundDrawables(null,null,null,null);
        tv_tab_all.setSelected(false);
        tv_tab_tours.setSelected(false);
        tv_tab_matches.setSelected(false);
        recyclerView_match.setVisibility(View.VISIBLE);
        recyclerView_tours.setVisibility(View.VISIBLE);
        tv_title_match.setVisibility((matchsList != null && matchsList.size()>0)?View.VISIBLE:View.GONE);
        tv_title_tours.setVisibility((toursList != null && toursList.size()>0)?View.VISIBLE:View.GONE);
        switch (type){
            case 0:
                if(!tv_tab_all.isSelected()){
                    if(matchsList != null && matchsList.size()>9){
                        mMatchAdapter.setNewData(matchsList.subList(0,9));
                        tv_title_match.setCompoundDrawables(null,null,drawableArrRight,null);
                    }else{
                        mMatchAdapter.setNewData(matchsList);
                    }

                    if(toursList != null && toursList.size()>9){
                        mToursAdapter.setNewData(toursList.subList(0,9));
                        tv_title_tours.setCompoundDrawables(null,null,drawableArrRight,null);
                    }else{
                        mToursAdapter.setNewData(toursList);
                    }

                    tv_tab_all.setSelected(true);
                    tv_tab_all.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_semibold));
                    if(mMatchAdapter.getItemCount() != 0 && mToursAdapter.getItemCount() != 0){
                        view_line.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 1:
                if(!tv_tab_matches.isSelected()){
                    mMatchAdapter.setNewData(matchsList);
                    tv_tab_matches.setSelected(true);
                    recyclerView_tours.setVisibility(View.GONE);
                    tv_title_tours.setVisibility(View.GONE);
                    tv_tab_matches.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_semibold));
                    view_line.setVisibility(View.GONE);
                }
                break;
            case 2:
                if(!tv_tab_tours.isSelected()){
                    mToursAdapter.setNewData(toursList);
                    tv_tab_tours.setSelected(true);
                    recyclerView_match.setVisibility(View.GONE);
                    tv_title_match.setVisibility(View.GONE);
                    tv_tab_tours.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_semibold));
                    view_line.setVisibility(View.GONE);
                }
                break;
            default:break;
        }
    }

    @Override
    public void getMoreData(int type, List<MatchSearchBean> list) {
        if(list == null || list.size() <= 0){
            if(type == 1){
                mMPage--;
            }else {
                mTPage--;
            }
            return;
        }
        if(type == 1){
            mMatchAdapter.addData(list);
        }else if(type == 2){
            mToursAdapter.addData(list);
        }
    }

    @Override
    public void getDataSuccess(List<MatchSearchBean> bean, int type) {
        fl_loading.setVisibility(View.GONE);
        sl_main.setVisibility(View.VISIBLE);

        if(type == 1){
            tv_tab_all.setSelected(true);
            tv_tab_matches.setSelected(false);
            tv_tab_tours.setSelected(false);
            tv_tab_all.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_semibold));
            tv_tab_matches.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_regular));
            tv_tab_tours.setTypeface(ResourcesCompat.getFont(this, R.font.noto_sans_display_regular));
            if (bean != null && bean.size() > 0) {
                ll_tab.setVisibility(View.VISIBLE);
                fl_tab_matches.setVisibility(View.VISIBLE);
                recyclerView_match.setVisibility(View.VISIBLE);
                tv_title_match.setVisibility(View.VISIBLE);
                matchsList = bean;
                if(bean.size()>9){
                    mMatchAdapter.setNewData(bean.subList(0,9));
                    tv_title_match.setCompoundDrawables(null,null,drawableArrRight,null);
                }else{
                    mMatchAdapter.setNewData(bean);
                    tv_title_match.setCompoundDrawables(null,null,null,null);
                }
            }else{
                fl_tab_matches.setVisibility(View.GONE);
                tv_title_match.setVisibility(View.GONE);
            }
        }else if(type == 2){
            if (bean != null && bean.size() > 0) {
                ll_tab.setVisibility(View.VISIBLE);
                fl_tab_tours.setVisibility(View.VISIBLE);
                recyclerView_tours.setVisibility(View.VISIBLE);
                tv_title_tours.setVisibility(View.VISIBLE);
                toursList = bean;
                if(bean.size()>9){
                    mToursAdapter.setNewData(bean.subList(0,9));
                    tv_title_tours.setCompoundDrawables(null,null,drawableArrRight,null);
                }else{
                    mToursAdapter.setNewData(bean);
                    tv_title_tours.setCompoundDrawables(null,null,null,null);
                }
            }else{
                fl_tab_tours.setVisibility(View.GONE);
                tv_title_tours.setVisibility(View.GONE);
            }

            if(mMatchAdapter.getItemCount() == 0){
                if(mToursAdapter.getItemCount() == 0){
                    recyclerView_match.setVisibility(View.GONE);
                    recyclerView_tours.setVisibility(View.GONE);
                    sl_main.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                    tv_empty.setText("No Result for '" + searchWord + "'");
                }
            }

        }

    }

    @Override
    public void getDataFail(String msg, int type) {
        fl_loading.setVisibility(View.GONE);
        if(type == 1){
            recyclerView_match.setVisibility(View.GONE);
            tv_title_match.setVisibility(View.GONE);
        }else{
            recyclerView_tours.setVisibility(View.GONE);
            tv_title_tours.setVisibility(View.GONE);
        }
        ToastUtil.show(msg);
    }
}
