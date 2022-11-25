package com.longya.live.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.longya.live.R;
import com.longya.live.activity.FootballHistoryIndexDetailActivity;
import com.longya.live.activity.HistoryIndexDetailActivity;
import com.longya.live.adapter.BasketballMatchHistoryIndexAdapter;
import com.longya.live.adapter.FootballMatchBaijiaIndexAdapter;
import com.longya.live.adapter.FootballMatchHistoryIndexAdapter;
import com.longya.live.model.BasketballIndexBean;
import com.longya.live.model.BasketballIndexOneBean;
import com.longya.live.model.BasketballIndexTwoBean;
import com.longya.live.model.FootballHistoryIndexBean;
import com.longya.live.model.FootballHistoryIndexInnerBean;
import com.longya.live.model.HistoryIndexBean;
import com.longya.live.model.HistoryIndexInnerBean;
import com.longya.live.model.IndexBean;
import com.longya.live.model.JsonBean;
import com.longya.live.presenter.match.FootballMatchIndexPresenter;
import com.longya.live.view.MvpFragment;
import com.longya.live.view.match.FootballMatchIndexView;

import java.util.ArrayList;
import java.util.List;

public class FootballMatchIndexInnerFragment extends Fragment implements View.OnClickListener {
    public static final int TYPE_RANGQIU = 0;
    public static final int TYPE_OUPEI = 1;
    public static final int TYPE_DAXIAOQIU = 2;

    public static FootballMatchIndexInnerFragment newInstance(int type, int id) {
        FootballMatchIndexInnerFragment fragment = new FootballMatchIndexInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType = 0;
    private int mId;
    protected View rootView;
    private RecyclerView rv_baijia;
    private FootballMatchBaijiaIndexAdapter mBaijiaAdapter;
    private RecyclerView rv_history;
    private FootballMatchHistoryIndexAdapter mHistoryAdapter;
    private OptionsPickerView pvCustomOptions;

    private List<FootballHistoryIndexBean> mHistoryList;
    private int mCompanyId;//历史同盘选中的公司id

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_football_match_index_inner, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mType = getArguments().getInt("type");
        mId = getArguments().getInt("id");
        initUI();
        initData();
    }

    protected void initUI() {
        rv_baijia = rootView.findViewById(R.id.rv_baijia);
        rv_history = rootView.findViewById(R.id.rv_history);
    }

    protected void initData() {
//        List<IndexBean> oneList = new ArrayList<>();
//        IndexBean indexOne = new IndexBean();
//        indexOne.setItemType(IndexBean.HEAD);
//        oneList.add(indexOne);
//        IndexBean indexTwo = new IndexBean();
//        indexTwo.setItemType(IndexBean.CONTENT);
//        oneList.add(indexTwo);
//        IndexBean indexThree = new IndexBean();
//        indexThree.setItemType(IndexBean.CONTENT);
//        oneList.add(indexThree);
//        IndexBean indexFour = new IndexBean();
//        indexFour.setName(getContext().getString(R.string.highest_value));
//        indexFour.setItemType(IndexBean.CONTENT);
//        oneList.add(indexFour);
//        IndexBean indexFive = new IndexBean();
//        indexFive.setName(getContext().getString(R.string.lowest_value));
//        indexFive.setItemType(IndexBean.CONTENT);
//        oneList.add(indexFive);
//        if (mType == TYPE_OUPEI) {
//            IndexBean indexSix = new IndexBean();
//            indexSix.setName(getContext().getString(R.string.average_value));
//            indexSix.setItemType(IndexBean.CONTENT);
//            oneList.add(indexSix);
//        }
        mBaijiaAdapter = new FootballMatchBaijiaIndexAdapter(new ArrayList<>(), mType, mId);
        rv_baijia.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_baijia.setAdapter(mBaijiaAdapter);

        if (mType == TYPE_OUPEI) {
            rv_history.setVisibility(View.VISIBLE);
            mHistoryAdapter = new FootballMatchHistoryIndexAdapter(new ArrayList<>(), mType);
            mHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.cl_company) {
                        pvCustomOptions.show();
                    }else if (view.getId() == R.id.cl_company_content) {
                        boolean isAll = false;
                        if (position == 1) {
                            isAll = true;
                        }
                        FootballHistoryIndexDetailActivity.forward(getContext(), mId, mCompanyId, mHistoryList, isAll);
                    }
                }
            });
            rv_history.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_history.setAdapter(mHistoryAdapter);
        }else {
            rv_history.setVisibility(View.GONE);
        }
    }

    public void setData(List<BasketballIndexOneBean> list, List<FootballHistoryIndexBean> historyList) {
        if (list != null) {
            BasketballIndexOneBean head = new BasketballIndexOneBean();
            head.setItemType(BasketballIndexOneBean.HEAD);
            list.add(0, head);
            mBaijiaAdapter.setNewData(list);
        }
        if (mType == TYPE_OUPEI && historyList != null && historyList.size() > 0) {
            mHistoryList = historyList;
            List<FootballHistoryIndexInnerBean> innerList = new ArrayList<>();
            if (historyList.get(0) != null) {
                FootballHistoryIndexInnerBean bean = new FootballHistoryIndexInnerBean();
                mCompanyId = historyList.get(0).getId();
                bean.setItemType(HistoryIndexInnerBean.HEAD);
                bean.setCompany_name(historyList.get(0).getName());
                bean.setWon(historyList.get(0).getInitial_won());
                bean.setDraw(historyList.get(0).getInitial_draw());
                bean.setLoss(historyList.get(0).getInitial_loss());
                innerList.add(bean);
            }else {
                innerList.add(new FootballHistoryIndexInnerBean());
            }
            if (historyList.get(0).getAll() != null) {
                historyList.get(0).getAll().setItemType(HistoryIndexInnerBean.CONTENT);
                innerList.add(historyList.get(0).getAll());
            }else {
                innerList.add(new FootballHistoryIndexInnerBean());
            }
            if (historyList.get(0).getTonglian() != null) {
                historyList.get(0).getTonglian().setItemType(HistoryIndexInnerBean.CONTENT);
                innerList.add(historyList.get(0).getTonglian());
            }else {
                innerList.add(new FootballHistoryIndexInnerBean());
            }
            mHistoryAdapter.setNewData(innerList);
            //历史同盘公司选择弹窗初始化
            List<String> optionList = new ArrayList<>();
            for (int i = 0; i < historyList.size(); i++) {
                optionList.add(historyList.get(i).getName());
            }
            pvCustomOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    FootballHistoryIndexBean historyIndexBean = historyList.get(options1);
                    List<FootballHistoryIndexInnerBean> tempList = new ArrayList<>();
                    if (historyIndexBean != null) {
                        FootballHistoryIndexInnerBean bean = new FootballHistoryIndexInnerBean();
                        mCompanyId = historyIndexBean.getId();
                        bean.setItemType(HistoryIndexInnerBean.HEAD);
                        bean.setCompany_name(historyIndexBean.getName());
                        bean.setWon(historyIndexBean.getInitial_won());
                        bean.setDraw(historyIndexBean.getInitial_draw());
                        bean.setLoss(historyIndexBean.getInitial_loss());
                        tempList.add(bean);
                    }else {
                        tempList.add(new FootballHistoryIndexInnerBean());
                    }
                    if (historyIndexBean.getAll() != null) {
                        historyIndexBean.getAll().setItemType(HistoryIndexInnerBean.CONTENT);
                        tempList.add(historyIndexBean.getAll());
                    }else {
                        tempList.add(new FootballHistoryIndexInnerBean());
                    }
                    if (historyIndexBean.getTonglian() != null) {
                        historyIndexBean.getTonglian().setItemType(HistoryIndexInnerBean.CONTENT);
                        tempList.add(historyIndexBean.getTonglian());
                    }else {
                        tempList.add(new FootballHistoryIndexInnerBean());
                    }
                    mHistoryAdapter.setNewData(tempList);
                }
            })
                    .setLayoutRes(R.layout.pickerview_index_company, new CustomListener() {
                        @Override
                        public void customLayout(View v) {
                            TextView tv_cancel = v.findViewById(R.id.tv_cancel);
                            TextView tv_confirm = v.findViewById(R.id.tv_confirm);
                            tv_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pvCustomOptions.returnData();
                                    pvCustomOptions.dismiss();
                                }
                            });

                            tv_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pvCustomOptions.dismiss();
                                }
                            });
                        }
                    })
                    .isDialog(false)
                    .setOutSideCancelable(true)
                    .build();
            pvCustomOptions.setPicker(optionList);//添加数据
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
