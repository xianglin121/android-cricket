package com.onecric.live.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CenterShowHorizontalScrollView extends HorizontalScrollView {
    private LinearLayout mShowLinear;

    public CenterShowHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mShowLinear = new LinearLayout(context);
        mShowLinear.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mShowLinear.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(mShowLinear, params);
    }

    public void onClicked(View v) {
        int position = (Integer) v.getTag();
        View itemView = mShowLinear.getChildAt(position);
        int itemWidth = itemView.getWidth();
        int scrollViewWidth = this.getWidth();

        smoothScrollTo(itemView.getLeft() - (scrollViewWidth / 2 - itemWidth / 2), 0);
    }

    public void onClicked(int position) {
        if ((position+1) > mShowLinear.getChildCount()) {
            return;
        }
        View itemView = mShowLinear.getChildAt(position);
        int itemWidth = itemView.getWidth();
        int scrollViewWidth = this.getWidth();

        smoothScrollTo(itemView.getLeft() - (scrollViewWidth / 2 - itemWidth / 2), 0);
    }

    public View getView(int position) {
        View itemView = mShowLinear.getChildAt(position);
        return itemView;
    }

    public LinearLayout getLinear() {
        return mShowLinear;
    }

    public void addItemView(View itemView, int position) {
        itemView.setTag(position);
        mShowLinear.addView(itemView);
    }

}