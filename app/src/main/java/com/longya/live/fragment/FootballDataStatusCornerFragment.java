package com.longya.live.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.longya.live.R;
import com.longya.live.custom.VerticalProgress;
import com.longya.live.view.BaseFragment;

import java.math.BigDecimal;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2022/1/10
 */
public class FootballDataStatusCornerFragment extends BaseFragment implements View.OnClickListener {
    public static FootballDataStatusCornerFragment newInstance() {
        FootballDataStatusCornerFragment fragment = new FootballDataStatusCornerFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView tv_one, tv_two, tv_three, tv_four, tv_five, tv_six;
    private VerticalProgress progress_one, progress_two, progress_three, progress_four, progress_five, progress_six;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_football_data_status_corner;
    }

    @Override
    protected void initUI() {
        tv_one = findViewById(R.id.tv_one);
        tv_two = findViewById(R.id.tv_two);
        tv_three = findViewById(R.id.tv_three);
        tv_four = findViewById(R.id.tv_four);
        tv_five = findViewById(R.id.tv_five);
        tv_six = findViewById(R.id.tv_six);
        progress_one = findViewById(R.id.progress_one);
        progress_two = findViewById(R.id.progress_two);
        progress_three = findViewById(R.id.progress_three);
        progress_four = findViewById(R.id.progress_four);
        progress_five = findViewById(R.id.progress_five);
        progress_six = findViewById(R.id.progress_six);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    protected void initData() {
    }

    public void setData(List<Integer> list) {
        if (list != null) {
            BigDecimal totalBd = new BigDecimal(0);
            for (int i = 0; i < list.size(); i++) {
                totalBd = totalBd.add(new BigDecimal(list.get(i)));
            }
            if (list.size() > 0 && list.get(0).doubleValue() > 0) {
                tv_one.setText(String.valueOf(list.get(0)) + "场");
                BigDecimal bigDecimal = new BigDecimal(list.get(0))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_one.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 1 && list.get(1).doubleValue() > 0) {
                tv_two.setText(String.valueOf(list.get(1)) + "场");
                BigDecimal bigDecimal = new BigDecimal(list.get(1))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_two.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 2 && list.get(2).doubleValue() > 0) {
                tv_three.setText(String.valueOf(list.get(2)) + "场");
                BigDecimal bigDecimal = new BigDecimal(list.get(2))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_three.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 3 && list.get(3).doubleValue() > 0) {
                tv_four.setText(String.valueOf(list.get(3)) + "场");
                BigDecimal bigDecimal = new BigDecimal(list.get(3))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_four.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 4 && list.get(4).doubleValue() > 0) {
                tv_five.setText(String.valueOf(list.get(4)) + "场");
                BigDecimal bigDecimal = new BigDecimal(list.get(4))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_five.setProgress(bigDecimal.intValue());
            }
            if (list.size() > 5 && list.get(5).doubleValue() > 0) {
                tv_six.setText(String.valueOf(list.get(5)) + "场");
                BigDecimal bigDecimal = new BigDecimal(list.get(5))
                        .divide(totalBd, 2, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
                progress_six.setProgress(bigDecimal.intValue());
            }
        }
    }
}
