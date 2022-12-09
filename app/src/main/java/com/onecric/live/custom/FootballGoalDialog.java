package com.onecric.live.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.FootballGoalAdapter;
import com.onecric.live.model.MatchSocketBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/3
 */
public class FootballGoalDialog extends Dialog {
    private Context mContext;
    private RecyclerView rv_dialog;
    private FootballGoalAdapter mAdapter;

    public FootballGoalDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        initDialog();
    }

    private void initDialog() {
        setContentView(R.layout.dialog_football_goal);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);

        rv_dialog = findViewById(R.id.rv_dialog);
        mAdapter = new FootballGoalAdapter(R.layout.item_football_goal, new ArrayList<>());
        rv_dialog.setLayoutManager(new LinearLayoutManager(mContext));
        rv_dialog.setAdapter(mAdapter);
    }

    public List<MatchSocketBean> getData() {
        List<MatchSocketBean> list = new ArrayList<>();
        if (mAdapter != null) {
            list.addAll(mAdapter.getData());
        }
        return list;
    }

    public void addData(MatchSocketBean bean) {
        if (bean != null && mAdapter != null) {
            mAdapter.addData(bean);
        }
    }

    public void removeData() {
        if (mAdapter != null) {
            mAdapter.setNewData(new ArrayList<>());
        }
    }
}
