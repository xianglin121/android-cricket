package com.longya.live.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.longya.live.CommonAppConfig;
import com.longya.live.R;
import com.longya.live.activity.OpenNobleActivity;
import com.longya.live.adapter.MyFollowAdapter;
import com.longya.live.model.LiveUserBean;
import com.longya.live.model.NobelListBean;
import com.longya.live.util.DialogUtil;
import com.longya.live.util.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/10/25
 */
public class OpenNobleFragment extends Fragment {
    public static OpenNobleFragment newInstance(int position, LiveUserBean liveUserBean, NobelListBean nobelListBean) {
        OpenNobleFragment fragment = new OpenNobleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putSerializable("user", liveUserBean);
        bundle.putSerializable("bean", nobelListBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected View rootView;
    private ImageView iv_logo;
    private TextView tv_noble_mount;
    private TextView tv_noble_discount;
    private TextView tv_noble_identity;
    private TextView tv_noble_speak;
    private TextView tv_one, tv_price_one;
    private TextView tv_two, tv_price_two;
    private TextView tv_balance;
    private TextView tv_desc;
    private TextView tv_noble_name;
    private TextView tv_time_limit;
    private TextView tv_open;

    private int mPosition;
    private LiveUserBean mLiveUserBean;
    private NobelListBean mNobelListBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_open_noble, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPosition = getArguments().getInt("position");
        if (getArguments().getSerializable("user") != null) {
            mLiveUserBean = (LiveUserBean) getArguments().getSerializable("user");
        }
        mNobelListBean = (NobelListBean) getArguments().getSerializable("bean");
        initUI();
        initData();
    }

    private void initUI() {
        iv_logo = rootView.findViewById(R.id.iv_logo);
        tv_noble_mount = rootView.findViewById(R.id.tv_noble_mount);
        tv_noble_discount = rootView.findViewById(R.id.tv_noble_discount);
        tv_noble_identity = rootView.findViewById(R.id.tv_noble_identity);
        tv_noble_speak = rootView.findViewById(R.id.tv_noble_speak);
        tv_one = rootView.findViewById(R.id.tv_one);
        tv_price_one = rootView.findViewById(R.id.tv_price_one);
        tv_two = rootView.findViewById(R.id.tv_two);
        tv_price_two = rootView.findViewById(R.id.tv_price_two);
        tv_balance = rootView.findViewById(R.id.tv_balance);
        tv_desc = rootView.findViewById(R.id.tv_desc);
        tv_noble_name = rootView.findViewById(R.id.tv_noble_name);
        tv_time_limit = rootView.findViewById(R.id.tv_time_limit);
        tv_open = rootView.findViewById(R.id.tv_open);

        tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNobelListBean != null) {
                    DialogUtil.showBuyNobleDialog(getContext(), mNobelListBean.getName(), new DialogUtil.SimpleCallback() {
                        @Override
                        public void onConfirmClick(Dialog dialog, String content) {
                            if (getActivity() instanceof OpenNobleActivity) {
                                ((OpenNobleActivity)getActivity()).openNoble(mNobelListBean.getId());
                            }
                        }
                    });
                }
            }
        });
    }

    private void initData() {
        if (mNobelListBean != null) {
            GlideUtil.loadImageDefault(getContext(), mNobelListBean.getIcon(), iv_logo);
            switch (mPosition) {
                case 0:
                    tv_noble_mount.setText(getActivity().getString(R.string.noble_mount_five));
                    tv_noble_discount.setText(String.format(getActivity().getString(R.string.noble_discount_desc), "5.4"));
                    tv_noble_speak.setText(String.format(getActivity().getString(R.string.noble_speak_desc), 5, 88, 5));
                    break;
                case 1:
                    tv_noble_mount.setText(getActivity().getString(R.string.noble_mount_four));
                    tv_noble_discount.setText(String.format(getActivity().getString(R.string.noble_discount_desc), "5.0"));
                    tv_noble_speak.setText(String.format(getActivity().getString(R.string.noble_speak_desc), 4, 66, 4));
                    break;
                case 2:
                    tv_noble_mount.setText(getActivity().getString(R.string.noble_mount_three));
                    tv_noble_discount.setText(String.format(getActivity().getString(R.string.noble_discount_desc), "5.0"));
                    tv_noble_speak.setText(String.format(getActivity().getString(R.string.noble_speak_desc), 3, 50, 3));
                    break;
                case 3:
                    tv_noble_mount.setText(getActivity().getString(R.string.noble_mount_two));
                    tv_noble_discount.setText(String.format(getActivity().getString(R.string.noble_discount_desc), "4.5"));
                    tv_noble_speak.setText(String.format(getActivity().getString(R.string.noble_speak_desc), 2, 30, 2));
                    break;
                case 4:
                    tv_noble_mount.setText(getActivity().getString(R.string.noble_mount_one));
                    tv_noble_discount.setText(String.format(getActivity().getString(R.string.noble_discount_desc), "5.0"));
                    tv_noble_speak.setText(String.format(getActivity().getString(R.string.noble_speak_desc), 1, 20, 1));
                    break;
            }
            if (mPosition == 0) {
                rootView.findViewById(R.id.ll_trumpet).setVisibility(View.VISIBLE);
            }else {
                rootView.findViewById(R.id.ll_trumpet).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mNobelListBean.getName())) {
                tv_noble_identity.setText(String.format(getActivity().getString(R.string.noble_identity_desc), mNobelListBean.getName()));
                tv_one.setText(String.format(getActivity().getString(R.string.text_charge_noble_one), mNobelListBean.getName()));
                tv_two.setText(String.format(getActivity().getString(R.string.text_charge_noble_two), mNobelListBean.getName()));
            }
            tv_price_one.setText(String.valueOf(mNobelListBean.getCoin()));
            tv_price_two.setText(String.valueOf(mNobelListBean.getRenew_coin()));
            if (CommonAppConfig.getInstance().getUserBean() != null && !TextUtils.isEmpty(CommonAppConfig.getInstance().getUserBean().getBalance())) {
                tv_balance.setText(getActivity().getString(R.string.noble_diamond_balance) + CommonAppConfig.getInstance().getUserBean().getBalance());
            }
            if (mLiveUserBean != null) {
                if (!TextUtils.isEmpty(mLiveUserBean.getUser_nickname())) {
                    tv_desc.setText("由主播“" + mLiveUserBean.getUser_nickname() + "”为你开通");
                }
            }
        }

        if (CommonAppConfig.getInstance().getUserBean() != null && CommonAppConfig.getInstance().getUserBean().getGuard() != null) {
            NobelListBean guard = CommonAppConfig.getInstance().getUserBean().getGuard();
            if (!TextUtils.isEmpty(guard.getName())) {
                tv_noble_name.setText("您的贵族为：" + guard.getName());
            }
            if (guard.getEndtime() > 0) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = simpleDateFormat.format(guard.getEndtime() * 1000);
                tv_time_limit.setText("截止日期：" + format);
            }
            if (mNobelListBean.getList_order() == guard.getList_order()) {
                tv_open.setText(getString(R.string.btn_renewal_noble));
            }else {
                tv_open.setText(getString(R.string.btn_charge_noble));
            }
        }
    }
}
