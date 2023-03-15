package com.onecric.live.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

public class AnimUtils {
    private final static int DURATION = 450;

    public static void zoom(View view, boolean isZoomAnim) {
        if (isZoomAnim) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.12f),
                    ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.12f)
            );
            set.setDuration(DURATION);
            set.start();
        }
    }

    public static void disZoom(View view, boolean isZoomAnim) {
        if (isZoomAnim) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(view, "scaleX", 1.12f, 1f),
                    ObjectAnimator.ofFloat(view, "scaleY", 1.12f, 1f)
            );
            set.setDuration(DURATION);
            set.start();
        }
    }

    /**
     * 箭头旋转动画
     *
     * @param arrow
     * @param flag
     */
    public static void rotateArrow(ImageView arrow, boolean flag) {
        float pivotX = arrow.getWidth() / 2f;
        float pivotY = arrow.getHeight() / 2f;
        // flag为true则向上
        float fromDegrees = flag ? 180f : 180f;
        float toDegrees = flag ? 360f : 360f;
        //旋转动画效果   参数值 旋转的开始角度  旋转的结束角度  pivotX x轴伸缩值
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees,
                pivotX, pivotY);
        //该方法用于设置动画的持续时间，以毫秒为单位
        animation.setDuration(350);
        //启动动画
        arrow.startAnimation(animation);
    }

    public static void expand(final View view) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int viewHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else {
                    view.getLayoutParams().height = (int) (viewHeight * interpolatedTime);
                }
                view.requestLayout();
            }
        };
        animation.setDuration(300);
        animation.setInterpolator(new FastOutLinearInInterpolator());
        view.startAnimation(animation);
    }

    public static void collapse(final View view) {
        //view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int viewHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = viewHeight - (int) (viewHeight * interpolatedTime);
                    view.requestLayout();
                }
            }
        };
        animation.setDuration(300);
        animation.setInterpolator(new FastOutLinearInInterpolator());
        view.startAnimation(animation);

    }

    public static void transAnim(View view) {

        // 创建 ObjectAnimator
        ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(
                view,
                "translationY", // 平移 translationY
                -view.getMeasuredHeight());
        mObjectAnimator.setDuration(1000); // 动画周期，为1s，默认0.3s
//        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE); // 重复次数，持续重复
//        mObjectAnimator.setRepeatMode(ValueAnimator.RESTART); // 重复模式，从头开始
        mObjectAnimator.setInterpolator(new LinearInterpolator()); // 插值器，匀速
        mObjectAnimator.start(); // 开始播放动画
        mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取当前动画上移的距离，即上方设置的 0-200 区间内的值
                float num = (float) animation.getAnimatedValue();
                Log.d("AAAAAAAAA", "num: " + num);
            }
        });
    }

    public static void transAnim(View view,float start,float end) {
        // 创建 ObjectAnimator
        ObjectAnimator mObjectAnimator = ObjectAnimator.ofFloat(view, "translationY",start,end);
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setInterpolator(new LinearInterpolator()); // 插值器，匀速
        mObjectAnimator.start(); // 开始播放动画
        mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获取当前动画上移的距离，即上方设置的 0-200 区间内的值
                float num = (float) animation.getAnimatedValue();
                Log.d("AAAAAAAAA", "num: " + num);
            }
        });
    }


}
