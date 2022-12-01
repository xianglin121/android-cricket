package com.longya.live.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.longya.live.Constant;
import com.longya.live.R;
import com.longya.live.adapter.ChannelAdapter;
import com.longya.live.adapter.UnSelectedChannelAdapter;
import com.longya.live.custom.listener.ItemDragHelperCallBack;
import com.longya.live.custom.listener.OnChannelDragListener;
import com.longya.live.custom.listener.OnChannelListener;
import com.longya.live.model.Channel;
import com.longya.live.util.WordUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChannelDialogFragment extends DialogFragment implements OnChannelDragListener {

    public static ChannelDialogFragment newInstance(List<Channel> selectedDatas, List<Channel> unselectedFootballDatas, List<Channel> unselectedBasketballDatas) {
        ChannelDialogFragment dialogFragment = new ChannelDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA_SELECTED, (Serializable) selectedDatas);
        bundle.putSerializable(Constant.DATA_UNSELECTED_FOOTBALL, (Serializable) unselectedFootballDatas);
        bundle.putSerializable(Constant.DATA_UNSELECTED_BASKETBALL, (Serializable) unselectedBasketballDatas);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    private List<Channel> mDatas = new ArrayList<>();
    private ChannelAdapter mAdapter;
    RecyclerView mRecyclerView;
    private ItemTouchHelper mHelper;
    //未选中的足球频道
    private TextView tv_football;
    private View line_football;
    private RecyclerView rv_football;
    private UnSelectedChannelAdapter mFootballAdapter;
    private List<Channel> mFootballDatas = new ArrayList<>();
    //未选中的篮球频道
    private TextView tv_basketball;
    private View line_basketball;
    private RecyclerView rv_basketball;
    private UnSelectedChannelAdapter mBasketballAdapter;
    private List<Channel> mBasketballDatas = new ArrayList<>();

    private boolean isShowFootball = true;

    private OnChannelListener mOnChannelListener;

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        mOnChannelListener = onChannelListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }
        return inflater.inflate(R.layout.fragment_channel_manage, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerview);

        tv_football = view.findViewById(R.id.tv_football);
        line_football = view.findViewById(R.id.line_football);
        rv_football = view.findViewById(R.id.rv_football);

        tv_basketball = view.findViewById(R.id.tv_basketball);
        line_basketball = view.findViewById(R.id.line_basketball);
        rv_basketball = view.findViewById(R.id.rv_basketball);
        view.findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ((TextView)view.findViewById(R.id.title)).setText(WordUtil.getString(getActivity(), R.string.match_hot_channel_title));
        processLogic();
        view.findViewById(R.id.ll_football).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowFootball) {
                    return;
                }
                isShowFootball = true;
                tv_football.setTextColor(getResources().getColor(R.color.c_E3AC72));
                line_football.setVisibility(View.VISIBLE);
                tv_basketball.setTextColor(getResources().getColor(R.color.c_666666));
                line_basketball.setVisibility(View.GONE);
                rv_football.setVisibility(View.VISIBLE);
                rv_basketball.setVisibility(View.GONE);
            }
        });
        view.findViewById(R.id.ll_basketball).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowFootball) {
                    return;
                }
                isShowFootball = false;
                tv_football.setTextColor(getResources().getColor(R.color.c_666666));
                line_football.setVisibility(View.GONE);
                tv_basketball.setTextColor(getResources().getColor(R.color.c_E3AC72));
                line_basketball.setVisibility(View.VISIBLE);
                rv_football.setVisibility(View.GONE);
                rv_basketball.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setDataType(List<Channel> datas, int type) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setItemType(type);
        }
    }


    private void processLogic() {
        Channel one = new Channel();
        one.short_name_zh = getString(R.string.my_hot);
        one.itemType = Channel.TYPE_MY;
        mDatas.add(one);
        Bundle bundle = getArguments();
        List<Channel> selectedDatas = (List<Channel>) bundle.getSerializable(Constant.DATA_SELECTED);
        List<Channel> unselectedFootballDatas = (List<Channel>) bundle.getSerializable(Constant.DATA_UNSELECTED_FOOTBALL);
        List<Channel> unselectedBasketballDatas = (List<Channel>) bundle.getSerializable(Constant.DATA_UNSELECTED_BASKETBALL);
        setDataType(selectedDatas, Channel.TYPE_MY_CHANNEL);

        mDatas.addAll(selectedDatas);
        mFootballDatas.addAll(unselectedFootballDatas);
        mBasketballDatas.addAll(unselectedBasketballDatas);


        mAdapter = new ChannelAdapter(mDatas);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                return itemViewType == Channel.TYPE_MY_CHANNEL || itemViewType == Channel.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });
        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(callBack);
        mAdapter.setOnChannelDragListener(this);
        //attachRecyclerView
        mHelper.attachToRecyclerView(mRecyclerView);

        mFootballAdapter = new UnSelectedChannelAdapter(R.layout.item_unselected_channel, mFootballDatas);
        mFootballAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onMoveToMyChannel(mFootballAdapter.getItem(position).getType(), position, 0);
            }
        });
        rv_football.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rv_football.setAdapter(mFootballAdapter);

        mBasketballAdapter = new UnSelectedChannelAdapter(R.layout.item_unselected_channel, mBasketballDatas);
        mBasketballAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                onMoveToMyChannel(mBasketballAdapter.getItem(position).getType(), position, 0);
            }
        });
        rv_basketball.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rv_basketball.setAdapter(mBasketballAdapter);
    }

    public void onClick(View v) {
        dismiss();
    }

    private DialogInterface.OnDismissListener mOnDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null)
            mOnDismissListener.onDismiss(dialog);
    }

    @Override
    public void onStarDrag(BaseViewHolder baseViewHolder) {
        //开始拖动
//        KLog.i("开始拖动");
        mHelper.startDrag(baseViewHolder);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
//        if (starPos < 0||endPos<0) return;
        //我的频道之间移动
        if (mOnChannelListener != null)
            mOnChannelListener.onItemMove(starPos - 1, endPos - 1);//去除标题所占的一个index
        onMove(starPos, endPos);
    }

    private void onMove(int starPos, int endPos) {
        Channel startChannel = mDatas.get(starPos);
        //先删除之前的位置
        mDatas.remove(starPos);
        //添加到现在的位置
        mDatas.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int type, int starPos, int endPos) {
        //移动到我的频道
//        onMove(starPos, endPos);
//        if (!TextUtils.isEmpty(type)) {
            Channel channel = null;
            if (type == 0) {
                channel = mFootballAdapter.getItem(starPos);
                mFootballAdapter.remove(starPos);
            }else if (type == 1) {
                channel = mBasketballAdapter.getItem(starPos);
                mBasketballAdapter.remove(starPos);
            }
            if (channel != null) {
                mAdapter.addData(channel);
            }
            if (mOnChannelListener != null)
                mOnChannelListener.onMoveToMyChannel(type, starPos, endPos - 1);
//        }
    }

    @Override
    public void onMoveToOtherChannel(int type, int starPos, int endPos) {
        //移动到推荐频道
//        onMove(starPos, endPos);
//        if (!TextUtils.isEmpty(type)) {
            Channel startChannel = mDatas.remove(starPos);
            mAdapter.notifyItemRemoved(starPos);
//            mAdapter.notifyDataSetChanged();
            if (type == 0) {
                mFootballAdapter.addData(startChannel);
            }else if (type == 1) {
                mBasketballAdapter.addData(startChannel);
            }
            if (mOnChannelListener != null)
                mOnChannelListener.onMoveToOtherChannel(type, starPos - 1, endPos - 2 - mAdapter.getMyChannelSize());
//        }
    }
}
