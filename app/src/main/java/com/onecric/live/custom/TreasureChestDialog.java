package com.onecric.live.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onecric.live.R;
import com.onecric.live.adapter.TreasureChestAdapter;
import com.onecric.live.fragment.LiveChatFragment;
import com.onecric.live.model.BoxBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/3
 */
public class TreasureChestDialog extends Dialog {
    private Context mContext;
    private LiveChatFragment mFragment;
    private RecyclerView rv_dialog;
    private TreasureChestAdapter mAdapter;

    public TreasureChestDialog(@NonNull Context context, int themeResId, LiveChatFragment fragment) {
        super(context, themeResId);
        this.mContext = context;
        mFragment = fragment;
        initDialog();
    }

    private void initDialog() {
        setContentView(R.layout.dialog_treasure_chest);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);

        rv_dialog = findViewById(R.id.rv_dialog);
        mAdapter = new TreasureChestAdapter(R.layout.item_treasure_chest, new ArrayList<>(), mFragment);
        rv_dialog.setLayoutManager(new GridLayoutManager(mContext, 4));
        rv_dialog.setAdapter(mAdapter);
    }

    public TreasureChestAdapter getAdapter() {
        return mAdapter;
    }

    public List<BoxBean> getData() {
        List<BoxBean> list = new ArrayList<>();
        if (mAdapter != null) {
            list.addAll(mAdapter.getData());
        }
        return list;
    }

    public void addData(BoxBean bean) {
        if (bean != null && mAdapter != null) {
            mAdapter.addData(bean);
        }
    }

    public void setNewData(List<BoxBean> list) {
        if (list != null && list.size() > 0 && mAdapter != null) {
            mAdapter.setNewData(list);
        }
    }

    public void removeData() {
        if (mAdapter != null) {
            mAdapter.setNewData(new ArrayList<>());
        }
    }
}
