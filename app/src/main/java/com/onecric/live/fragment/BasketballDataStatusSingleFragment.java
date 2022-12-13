package com.onecric.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.onecric.live.R;
import com.onecric.live.custom.VerticalProgress;
import com.onecric.live.view.BaseFragment;

import java.math.BigDecimal;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/10
 */
public class BasketballDataStatusSingleFragment extends BaseFragment implements View.OnClickListener {
    public static BasketballDataStatusSingleFragment newInstance() {
        BasketballDataStatusSingleFragment fragment = new BasketballDataStatusSingleFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView tv_one, tv_two, tv_three, tv_four, tv_five;
    private VerticalProgress progress_one, progress_two, progress_three, progress_four, progress_five;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_basketball_data_status_singer;
    }

    @Override
    protected void initUI() {
        tv_one = findViewById(R.id.tv_one);
        tv_two = findViewById(R.id.tv_two);
        tv_three = findViewById(R.id.tv_three);
        tv_four = findViewById(R.id.tv_four);
        tv_five = findViewById(R.id.tv_five);
        progress_one = findViewById(R.id.progress_one);
        progress_two = findViewById(R.id.progress_two);
        progress_three = findViewById(R.id.progress_three);
        progress_four = findViewById(R.id.progress_four);
        progress_five = findViewById(R.id.progress_five);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    protected void initData() {
    }

    public void setData(List<Double> list) {
        if (list != null) {
            BigDecimal totalBd = new BigDecimal(0);
            for (int i = 0; i < list.size(); i++) {
                totalBd = totalBd.add(new BigDecimal(list.get(i)));
            }
            if (list.size() > 0 && list.get(0).doubleValue() > 0) {
                tv_one.setText(String.valueOf(list.get(0)));
                BigDecimal bigDecimal = new BigDecimal(list.get(0))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_one.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 1 && list.get(1).doubleValue() > 0) {
                tv_two.setText(String.valueOf(list.get(1)));
                BigDecimal bigDecimal = new BigDecimal(list.get(1))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_two.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 2 && list.get(2).doubleValue() > 0) {
                tv_three.setText(String.valueOf(list.get(2)));
                BigDecimal bigDecimal = new BigDecimal(list.get(2))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_three.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 3 && list.get(3).doubleValue() > 0) {
                tv_four.setText(String.valueOf(list.get(3)));
                BigDecimal bigDecimal = new BigDecimal(list.get(3))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_four.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 4 && list.get(4).doubleValue() > 0) {
                tv_five.setText(String.valueOf(list.get(4)));
                BigDecimal bigDecimal = new BigDecimal(list.get(4))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_five.setProgress(bigDecimal.intValue());
            }
        }
    }
}
