package com.onecric.live.fragment;

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
import com.onecric.live.R;
import com.onecric.live.activity.HistoryIndexDetailActivity;
import com.onecric.live.adapter.BasketballMatchBaijiaIndexAdapter;
import com.onecric.live.adapter.BasketballMatchHistoryIndexAdapter;
import com.onecric.live.model.BasketballIndexOneBean;
import com.onecric.live.model.HistoryIndexBean;
import com.onecric.live.model.HistoryIndexInnerBean;

import java.util.ArrayList;
import java.util.List;

public class BasketballMatchIndexInnerFragment extends Fragment implements View.OnClickListener {
    public static final int TYPE_RANGQIU = 0;
    public static final int TYPE_OUPEI = 1;
    public static final int TYPE_DAXIAOQIU = 2;

    public static BasketballMatchIndexInnerFragment newInstance(int type, int id) {
        BasketballMatchIndexInnerFragment fragment = new BasketballMatchIndexInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mType = 0;
    private int mId = 0;
    protected View rootView;
    private RecyclerView rv_baijia;
    private BasketballMatchBaijiaIndexAdapter mBaijiaAdapter;
    private RecyclerView rv_history;
    private BasketballMatchHistoryIndexAdapter mHistoryAdapter;
    private OptionsPickerView pvCustomOptions;

    private List<HistoryIndexBean> mHistoryList;
    private int mCompanyId;//历史同盘选中的公司id

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_basketball_match_index_inner, null);
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
        mType = getArguments().getInt("type");
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
        mBaijiaAdapter = new BasketballMatchBaijiaIndexAdapter(new ArrayList<>(), mType, mId);
        rv_baijia.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_baijia.setAdapter(mBaijiaAdapter);

//        List<IndexBean> twoList = new ArrayList<>();
//        IndexBean historyOne = new IndexBean();
//        historyOne.setItemType(IndexBean.HEAD);
//        twoList.add(historyOne);
//        IndexBean historyTwo = new IndexBean();
//        historyTwo.setName(getContext().getString(R.string.all));
//        historyTwo.setItemType(IndexBean.CONTENT);
//        twoList.add(historyTwo);
//        IndexBean historyThree = new IndexBean();
//        historyThree.setName(getContext().getString(R.string.same_league));
//        historyThree.setItemType(IndexBean.CONTENT);
//        twoList.add(historyThree);
        if (mType == TYPE_OUPEI) {
            rv_history.setVisibility(View.VISIBLE);
            mHistoryAdapter = new BasketballMatchHistoryIndexAdapter(new ArrayList<>(), mType);
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
                        HistoryIndexDetailActivity.forward(getContext(), mId, mCompanyId, mHistoryList, isAll);
                    }
                }
            });
            rv_history.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_history.setAdapter(mHistoryAdapter);
        }else {
            rv_history.setVisibility(View.GONE);
        }
    }

    public void setData(List<BasketballIndexOneBean> list, List<HistoryIndexBean> historyList) {
        if (list != null) {
            BasketballIndexOneBean head = new BasketballIndexOneBean();
            head.setItemType(BasketballIndexOneBean.HEAD);
            list.add(0, head);
            mBaijiaAdapter.setNewData(list);
        }
        if (mType == TYPE_OUPEI && historyList != null && historyList.size() > 0) {
            mHistoryList = historyList;
            List<HistoryIndexInnerBean> innerList = new ArrayList<>();
            if (historyList.get(0).getAnalysis() != null) {
                mCompanyId = historyList.get(0).getId();
                historyList.get(0).getAnalysis().setItemType(HistoryIndexInnerBean.HEAD);
                historyList.get(0).getAnalysis().setCompany_name(historyList.get(0).getName());
                innerList.add(historyList.get(0).getAnalysis());
            }else {
                innerList.add(new HistoryIndexInnerBean());
            }
            if (historyList.get(0).getAll() != null) {
                historyList.get(0).getAll().setItemType(HistoryIndexInnerBean.CONTENT);
                innerList.add(historyList.get(0).getAll());
            }else {
                innerList.add(new HistoryIndexInnerBean());
            }
            if (historyList.get(0).getTonglian() != null) {
                historyList.get(0).getTonglian().setItemType(HistoryIndexInnerBean.CONTENT);
                innerList.add(historyList.get(0).getTonglian());
            }else {
                innerList.add(new HistoryIndexInnerBean());
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
                    HistoryIndexBean historyIndexBean = historyList.get(options1);
                    List<HistoryIndexInnerBean> tempList = new ArrayList<>();
                    if (historyIndexBean.getAnalysis() != null) {
                        mCompanyId = historyIndexBean.getId();
                        historyIndexBean.getAnalysis().setItemType(HistoryIndexInnerBean.HEAD);
                        historyIndexBean.getAnalysis().setCompany_name(historyIndexBean.getName());
                        tempList.add(historyIndexBean.getAnalysis());
                    }else {
                        tempList.add(new HistoryIndexInnerBean());
                    }
                    if (historyIndexBean.getAll() != null) {
                        historyIndexBean.getAll().setItemType(HistoryIndexInnerBean.CONTENT);
                        tempList.add(historyIndexBean.getAll());
                    }else {
                        tempList.add(new HistoryIndexInnerBean());
                    }
                    if (historyIndexBean.getTonglian() != null) {
                        historyIndexBean.getTonglian().setItemType(HistoryIndexInnerBean.CONTENT);
                        tempList.add(historyIndexBean.getTonglian());
                    }else {
                        tempList.add(new HistoryIndexInnerBean());
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
