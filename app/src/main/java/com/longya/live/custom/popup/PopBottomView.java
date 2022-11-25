package com.longya.live.custom.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.R;
import com.longya.live.util.DpUtil;

import java.util.List;


public class PopBottomView {

    private Context mContext;
    private PopupWindow popupWindow;
    private OnPopClickListen listen;
    private RecyclerView data_rv;
    private BottomAdapter adapter;
    private TextView title_tv;

    public PopBottomView(Context context) {
        this.mContext = context;
        initPopupWindow();
    }

    public interface OnPopClickListen{
        void itemClick(int position);
    }

    public void setListen(OnPopClickListen listen){
        this.listen = listen;
    }


    public void initPopupWindow() {
        View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.pop_bottom_view, null);
        view.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        title_tv = view.findViewById(R.id.title_tv);
        data_rv = view.findViewById(R.id.data_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        data_rv.setLayoutManager(linearLayoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(data_rv);
        adapter = new BottomAdapter(mContext, new BottomAdapter.MOnItemClickListener() {
            @Override
            public void onItemClick(int position, Object o) {
                if (listen != null)
                    listen.itemClick(position);
                dismiss();
            }
        });
        data_rv.setAdapter(adapter);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(480));
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置popwindow出现和消失动画
        /*popupWindow.setAnimationStyle(R.style.PopMenuAnimation2);*/
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

    }

    public void setTitle(String title){
        title_tv.setText(title);
        title_tv.setVisibility(View.VISIBLE);
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing(){
        return popupWindow.isShowing();
    }

    public void setData(List<String> value){
        adapter.refreshData(value);
    }

    /**
     * 显示popWindow
     *
     * @param parent
     */
    public void showPop(View parent) {
        // 获取弹框的真实高度
        popupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        // 获取popwindow焦点
        popupWindow.setFocusable(true);
        // 设置popwindow如果点击外面区域，便关闭。
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.5f);

    }
}
