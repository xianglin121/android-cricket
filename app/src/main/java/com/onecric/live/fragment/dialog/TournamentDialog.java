package com.onecric.live.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.onecric.live.R;
import com.onecric.live.adapter.SelectTournamentAdapter;
import com.onecric.live.fragment.CricketNewFragment;
import com.onecric.live.model.CricketFiltrateBean;
import com.onecric.live.retrofit.ApiCallback;
import com.onecric.live.retrofit.ApiClient;
import com.onecric.live.retrofit.ApiStores;
import com.onecric.live.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TournamentDialog extends Dialog implements View.OnClickListener {
    private OnSelectTourListener mListener;
    private Context mContext;
    private RecyclerView rv_tournament;
    private EditText et_search;
    private TextView tv_apply;
    private TextView tv_empty;
    private LinearLayout ll_focus;
    public SelectTournamentAdapter mAdapter;
    private String mTournamentId = "";
    private CricketNewFragment fragment;
    private boolean isApply;
    public TournamentDialog(@NonNull Context context, CricketNewFragment fragment, OnSelectTourListener listener) {
        super(context, R.style.dialog);
        this.mListener = listener;
        this.mContext = context;
        this.fragment = fragment;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_search_tournament);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);


        rv_tournament = findViewById(R.id.rv_tournament);
        et_search = findViewById(R.id.et_input);
        tv_apply = findViewById(R.id.tv_apply);
        ll_focus = findViewById(R.id.ll_focus);
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.tv_reset).setOnClickListener(this);
        tv_apply.setOnClickListener(this);

        mAdapter = new SelectTournamentAdapter(R.layout.item_select_tournament, new ArrayList<>());
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_search_empty, null, false);
        inflate.findViewById(R.id.ll_empty).setVisibility(View.VISIBLE);
        mAdapter.setEmptyView(inflate);
        tv_empty = mAdapter.getEmptyView().findViewById(R.id.tv_empty);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.getItem(position).setCheck(!mAdapter.getItem(position).isCheck());
                mAdapter.notifyItemChanged(position);
                String midStr = "";
                List<CricketFiltrateBean> temp = mAdapter.getData();
                int checkNum = 0;
                for (int i = temp.size()-1; i >= 0; i--) {
                    if (temp.get(i).isCheck()) {
                        checkNum++;
                        midStr = midStr + temp.get(i).getId() + ",";
                    }
                }
/*                if (midStr.length() > 0) {
                    midStr = midStr.substring(0, midStr.length() - 1);
                }*/
                //如果没有改变就禁止按apply
                if(midStr.equals(fragment.tag)){
                    tv_apply.setEnabled(false);
                    tv_apply.setTextColor(Color.parseColor("#C6C7CB"));
                    tv_apply.setBackground(getContext().getDrawable(R.drawable.shape_c8c8c8_5dp_rec));
                }else{
                    tv_apply.setEnabled(true);
                    tv_apply.setTextColor(Color.WHITE);
                    tv_apply.setBackground(getContext().getDrawable(R.drawable.shape_dc3c23_5dp_rec));
                }
            }
        });
        rv_tournament.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_tournament.setAdapter(mAdapter);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getTourList();
            }
        });
        getTourList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_reset:
                //重置选择
                if(TextUtils.isEmpty(fragment.tag)){
                    tv_apply.setEnabled(false);
                    tv_apply.setTextColor(Color.parseColor("#C6C7CB"));
                    tv_apply.setBackground(getContext().getDrawable(R.drawable.shape_c8c8c8_5dp_rec));
                }else{
                    tv_apply.setEnabled(true);
                    tv_apply.setTextColor(Color.WHITE);
                    tv_apply.setBackground(getContext().getDrawable(R.drawable.shape_dc3c23_5dp_rec));
                }
                List<CricketFiltrateBean> data = mAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setCheck(false);
                }
                mAdapter.notifyDataSetChanged();
                mTournamentId = "";
                break;
            case R.id.tv_apply:
                isApply = true;
                dismiss();
                //联动top
                for(int f=0;f<fragment.mFiltrateAdapter.getData().size();f++){
                    fragment.mFiltrateAdapter.getData().get(f).setCheck(false);
                }
                List<CricketFiltrateBean> temp = mAdapter.getData();
                List<CricketFiltrateBean> fList = new ArrayList<>();
                fList.addAll(fragment.mFiltrateAdapter.getData());
                int checkNum = 0;
                mTournamentId = "";
                filtrate:for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).isCheck()) {
                        checkNum++;
                        mTournamentId = mTournamentId + temp.get(i).getId() + ",";
                        for(int f=0;f<fList.size();f++){
                            if(fList.get(f).getId() == temp.get(i).getId()){
                                //Filtrate重新排序,选中的放最前面
                                fList.get(f).setCheck(true);
                                fragment.mFiltrateAdapter.addData(0,fList.get(f));
                                fragment.mFiltrateAdapter.remove(f+1);
                                continue filtrate;
                            }
                        }
                    }
                }

                List<CricketFiltrateBean> tList = new ArrayList<>();
                tList.addAll(mAdapter.getData());
                for (int i = 0; i < tList.size(); i++) {
                    if (tList.get(i).isCheck()) {
                        //选中的放最前面
                        mAdapter.addData(0,tList.get(i));
                        mAdapter.remove(i+1);
                    }
                }
                rv_tournament.scrollToPosition(0);

/*                if (mTournamentId.length() > 0) {
                    mTournamentId = mTournamentId.substring(0, mTournamentId.length() - 1);
                }*/

                //应用,给到外面筛选
                mListener.selectedWord(mTournamentId,checkNum);
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void getTourList() {
        ApiClient.retrofit().create(ApiStores.class)
                .getTournamentList(TimeZone.getDefault().getID(),et_search.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ApiCallback() {
                    @Override
                    public void onSuccess(String data,  String msg) {
                        List<CricketFiltrateBean> list = JSONObject.parseArray(data, CricketFiltrateBean.class);
                        List<CricketFiltrateBean> temp = new ArrayList<>();
                        temp.addAll(list);
                        if(list!=null){
                            //联动top状态
                            if(!TextUtils.isEmpty(fragment.tag)){
                                for(int i=0; i<temp.size();i++){
                                    if(fragment.tag.contains(","+temp.get(i).getId()) || fragment.tag.contains(temp.get(i).getId()+",")){
                                        temp.get(i).setCheck(true);
                                        if (temp.get(i).isCheck()) {
                                            //选中的放最前面
                                            list.add(0,temp.get(i));
                                            list.remove(i+1);
                                        }
                                    }
                                }
                            }
                            mAdapter.setNewData(list);
                            rv_tournament.scrollToPosition(0);
                        }else{
                            tv_empty.setText("No Result for '" + et_search.getText().toString() + "'");
                            mAdapter.setNewData(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    @Override
    public void dismiss() {
        if(!isApply){
            List<CricketFiltrateBean> temp = mAdapter.getData();
            for (int i = 0; i < temp.size(); i++) {
                //包含是已选择的，其他都是未选择
                if (!TextUtils.isEmpty(fragment.tag) && fragment.tag.contains(temp.get(i).getId()+"")) {
                    temp.get(i).setCheck(true);
                }else{
                    temp.get(i).setCheck(false);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        super.dismiss();
    }

    public interface OnSelectTourListener{
        void selectedWord(String tourIds,int checkNum);
    }

    @Override
    public void show() {
        super.show();
        isApply = false;
        ll_focus.requestFocus();
        tv_apply.setEnabled(false);
        tv_apply.setTextColor(Color.parseColor("#C6C7CB"));
        tv_apply.setBackground(getContext().getDrawable(R.drawable.shape_c8c8c8_5dp_rec));
    }
}
