package com.longya.live.presenter.theme;

import com.longya.live.presenter.BasePresenter;
import com.longya.live.view.theme.ThemeCollectionView;
import com.longya.live.view.theme.ThemeView;


public class ThemeCollectionPresenter extends BasePresenter<ThemeCollectionView> {
    public ThemeCollectionPresenter(ThemeCollectionView view) {
        attachView(view);
    }
}
