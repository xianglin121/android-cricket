package com.longya.live.custom.gift;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longya.live.R;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Copyright (C) 2018
 * 版权所有
 *
 * 作者：东莞市梦幻科技有限公司
 * 创建时间：2018/10/29
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class LPGiftView extends RelativeLayout {

    private AnimMessage mAnimMessage;

    public LPGiftView(Context context, AnimMessage message) {
        super(context);
        mAnimMessage = message;
        init();
    }

    public LPGiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LPGiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //礼物飞进的动画
        TranslateAnimation mGiftLayoutInAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.lp_gift_in);

        // 外层是线性布局
        LayoutInflater.from(getContext()).inflate(R.layout.lp_item_gift_animal, this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);

        //获取view
        final MagicTextView giftNumView = (MagicTextView) findViewById(R.id.giftNum);
        TextView nick_tv = findViewById(R.id.nick_tv);
        TextView gift_tv = findViewById(R.id.gift_tv);
        ImageView gift_iv = findViewById(R.id.gift_iv);
        ImageView head_iv = findViewById(R.id.head_iv);
        final RelativeLayout giftTextContainerLayout = findViewById(R.id.parent_rl);

        //加载数据
        //用户昵称
        nick_tv.setText(mAnimMessage.userName);
        //礼物图片`
        if (mAnimMessage.giftType.equals("1")) {//礼物 0-金币 1-礼物
            if (!TextUtils.isEmpty(mAnimMessage.giftImgUrl)) {
                Glide.with(getContext()).load(mAnimMessage.giftImgUrl).into(gift_iv);
//                SVGAParser parser = new SVGAParser(getContext());
//                try {
//                    URL url = new URL(mAnimMessage.giftSvgaUrl);
//                    parser.parse(url, new SVGAParser.ParseCompletion() {
//                        @Override
//                        public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
//                            SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
//                            gift_iv.setImageDrawable(drawable);
//                            gift_iv.startAnimation();
//                        }
//
//                        @Override
//                        public void onError() {
//
//                        }
//                    });
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
                gift_iv.setVisibility(VISIBLE);
            }
            if (!TextUtils.isEmpty(mAnimMessage.giftName)) {
                gift_tv.setText("送" + mAnimMessage.giftName);
            }
        }

        //用户头像
        if (!TextUtils.isEmpty(mAnimMessage.headUrl)) {
            Glide.with(getContext()).load(mAnimMessage.headUrl).transform(new GlideCircleTransform(getContext())).into(head_iv);
        } else {
            head_iv.setImageResource(R.mipmap.bg_avatar_default);
        }

        giftNumView.setTag(mAnimMessage.giftNum);/*给数量控件设置标记*/
        //giftNumView.setTag(1);/*给数量控件设置标记*/
        mAnimMessage.updateTime = System.currentTimeMillis();/*设置时间标记*/
        setTag(mAnimMessage);/*设置view标识*/

        giftTextContainerLayout.startAnimation(mGiftLayoutInAnim);//开始执行显示礼物的动画
        mGiftLayoutInAnim.setAnimationListener(new Animation.AnimationListener() {/*显示动画的监听*/
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String content = "x" + giftNumView.getTag();
                giftNumView.setText(content);
                startComboAnim(giftNumView);// 设置一开始的连击事件
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * 连击动画
     */
    public void startComboAnim(final View giftNumView) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(giftNumView, "scaleX", 1.8f, 1.0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(giftNumView, "scaleY", 1.8f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(300);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(anim1, anim2);
        animSet.start();
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((AnimMessage) getTag()).updateTime = System.currentTimeMillis();//设置时间标记
                giftNumView.setTag((Integer) giftNumView.getTag() + 1);
                //这里用((GiftMessage)giftView.getTag()) 来实时的获取GiftMessage  便于礼物的追加
                if ((Integer) giftNumView.getTag() <= ((AnimMessage) getTag()).giftNum) {
                    String content = "x" + giftNumView.getTag();
                    ((MagicTextView) giftNumView).setText(content);
                    startComboAnim(giftNumView);
                } else {
                    ((AnimMessage) getTag()).isComboAnimationOver = true;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
