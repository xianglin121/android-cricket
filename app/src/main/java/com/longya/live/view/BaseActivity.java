package com.longya.live.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.longya.live.AppManager;
import com.longya.live.R;
import com.longya.live.retrofit.ApiClient;
import com.longya.live.retrofit.ApiStores;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.KeyboardsUtils;
import com.longya.live.util.LogUtil;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
//import pro.piwik.sdk.Tracker;
//import pro.piwik.sdk.extra.TrackHelper;
import retrofit2.Call;

/**
 * Created by WuXiaolong on 2015/9/23.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Activity mActivity;
    private CompositeDisposable mCompositeDisposable;
    private List<Call> calls;
    private Dialog mLoadingDialog;
    protected boolean mIsBlack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Tracker tracker = ((AppManager) getApplication()).getTracker();
//        TrackHelper.track().screen(this).with(tracker);
        setClassicsLanguage();
        mIsBlack = getStatusBarTextColor();
        setStatusBar();
        setContentView(getLayoutId());
        mLoadingDialog = DialogUtil.loadingDialog(mActivity);
        setBackBtn();
        initView();
        initData();
    }

    private void setClassicsLanguage() {
        ClassicsHeader.REFRESH_HEADER_PULLING = getResources().getString(R.string.refresh_header_pulling);
        ClassicsHeader.REFRESH_HEADER_UPDATE = getResources().getString(R.string.refresh_header_update);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getResources().getString(R.string.refresh_header_refreshing);
        ClassicsHeader.REFRESH_HEADER_LOADING = getResources().getString(R.string.refresh_header_loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getResources().getString(R.string.refresh_header_release);
        ClassicsHeader.REFRESH_HEADER_FINISH = getResources().getString(R.string.refresh_header_finish);
        ClassicsHeader.REFRESH_HEADER_FAILED = getResources().getString(R.string.refresh_header_failed);

        ClassicsFooter.REFRESH_FOOTER_PULLING = getResources().getString(R.string.refresh_footer_pulling);
        ClassicsFooter.REFRESH_FOOTER_NOTHING = getResources().getString(R.string.refresh_footer_nothing);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getResources().getString(R.string.refresh_footer_release);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getResources().getString(R.string.refresh_footer_refreshing);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getResources().getString(R.string.refresh_footer_loading);
//        ClassicsFooter.REFRESH_FOOTER_FINISH = getResources().getString(R.string.refresh_footer_finish);
        ClassicsFooter.REFRESH_FOOTER_FINISH = "";
        ClassicsFooter.REFRESH_FOOTER_FAILED = getResources().getString(R.string.refresh_footer_failed);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mActivity = this;
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mActivity = this;
    }

    /**
     * 设置状态栏字体是白色还是黑色
     */
    public boolean getStatusBarTextColor() {
        return false;
    }

    /**
     * 设置布局文件
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();


    /**
     * 点击非编辑区域收起键盘
     * 获取点击事件
     * CSDN-深海呐
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (KeyboardsUtils.isShouldHideKeyBord(view, ev)) {
                KeyboardsUtils.hintKeyBoards(view);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    protected void setBackBtn() {
        LinearLayout btn_left = findViewById(R.id.btn_left);
        if (btn_left != null) {
            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected void setTitleText(String text) {
        TextView title = findViewById(R.id.title);
        if (title != null) {
            title.setText(text);
        }
    }

    protected void setRightTitleText(String text) {
        TextView title = findViewById(R.id.right_title);
        if (title != null) {
            title.setText(text);
        }
    }

    /**
     * 设置透明状态栏
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            if (isStatusBarWhite()) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            } else {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            }
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(0);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (mIsBlack) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0);
        }
    }

    protected boolean isStatusBarWhite() {
        return false;
    }

    @Override
    protected void onDestroy() {
        callCancel();
        onUnsubscribe();
        super.onDestroy();
    }

    public ApiStores apiStores() {
        return ApiClient.retrofit().create(ApiStores.class);
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }

    public <T> void addSubscription(Observable<T> observable, DisposableObserver<T> observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void onUnsubscribe() {
        LogUtil.d("onUnSubscribe");
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void toastShow(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    public void dismissLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }

    public void showEmptyView() {
        if (findViewById(R.id.ll_empty) != null) {
            findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        }
    }

    public void hideEmptyView() {
        if (findViewById(R.id.ll_empty) != null) {
            findViewById(R.id.ll_empty).setVisibility(View.INVISIBLE);
        }
    }

    public void setEmptyText(String text) {
        TextView tv_empty = findViewById(R.id.tv_empty);
        if (tv_empty != null) {
            if (!TextUtils.isEmpty(text)) {
                tv_empty.setText(text);
            }
        }
    }

    //防止连续点击
    private long lastClickTime;

    public boolean isFastDoubleClick() {
        //获取系统当前时间
        long time = System.currentTimeMillis();

        if (time - lastClickTime < 1000) {
            return true;
        }

        lastClickTime = time;

        return false;
    }
}
