package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.onecric.live.R;
import com.onecric.live.activity.PreViewActivity;

import java.util.Objects;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2020/11/24
 */

public class PreViewContentFragment extends Fragment {

    public static PreViewContentFragment newInstant(String image) {
        PreViewContentFragment fragment = new PreViewContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PreViewActivity.IMAGE, image);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pre_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        ImageViewTouch image = (ImageViewTouch) view.findViewById(R.id.image_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        String photo = Objects.requireNonNull(getArguments()).getString(PreViewActivity.IMAGE);
        //这边可以通过后缀名来判断图片类型 然后加载不同的
        Glide.with(getActivity()).load(photo).into(image);
    }




    public void resetView() {
        if (getView() != null) {
            ((ImageViewTouch) getView().findViewById(R.id.image_view))
                    .resetMatrix();
        }
    }
}
