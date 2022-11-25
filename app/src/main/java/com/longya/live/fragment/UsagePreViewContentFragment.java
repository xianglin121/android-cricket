package com.longya.live.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.longya.live.R;
import com.longya.live.activity.MainActivity;
import com.longya.live.activity.PreViewActivity;
import com.longya.live.util.SpUtil;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2020/11/24
 */

public class UsagePreViewContentFragment extends Fragment {

    public static UsagePreViewContentFragment newInstant(int image) {
        UsagePreViewContentFragment fragment = new UsagePreViewContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PreViewActivity.IMAGE, image);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_usage_pre_content, null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView image = rootView.findViewById(R.id.image_view);
        Button btn = rootView.findViewById(R.id.btn_start);
        int photo = requireArguments().getInt(PreViewActivity.IMAGE);
        switch (photo) {
            case 1:
                image.setImageResource(R.mipmap.bg_usage_one);
                break;
            case 2:
                image.setImageResource(R.mipmap.bg_usage_two);
                break;
            case 3:
                image.setImageResource(R.mipmap.bg_usage_three);
                break;
            case 4:
                image.setImageResource(R.mipmap.bg_usage_four);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(v -> {
                    if (!SpUtil.getInstance().getBooleanValue(SpUtil.HIDE_USAGE)) {
                        SpUtil.getInstance().setBooleanValue(SpUtil.HIDE_USAGE, true);
                    }
                    MainActivity.forward(getContext());
                });
                break;
        }
    }
}
