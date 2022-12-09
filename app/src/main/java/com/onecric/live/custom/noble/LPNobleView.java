package com.onecric.live.custom.noble;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.onecric.live.R;
import com.onecric.live.custom.gift.AnimMessage;
import com.onecric.live.util.DpUtil;
//import com.opensource.svgaplayer.SVGACallback;
//import com.opensource.svgaplayer.SVGADrawable;
//import com.opensource.svgaplayer.SVGAImageView;
//import com.opensource.svgaplayer.SVGAParser;
//import com.opensource.svgaplayer.SVGAVideoEntity;


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

public class LPNobleView extends RelativeLayout {

    private AnimMessage mAnimMessage;

    public LPNobleView(Context context, AnimMessage message) {
        super(context);
        mAnimMessage = message;

        init();
    }

    public LPNobleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LPNobleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //礼物飞进的动画
        TranslateAnimation mGiftLayoutInAnim = (TranslateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.lp_gift_in);

        // 外层是线性布局
        LayoutInflater.from(getContext()).inflate(R.layout.lp_item_noble_animal, this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);

        //获取view
        TextView nick_tv = findViewById(R.id.nick_tv);
        TextView noble_tv = findViewById(R.id.noble_tv);
//        SVGAImageView svga_noble = findViewById(R.id.svga_noble);
        final RelativeLayout giftTextContainerLayout = findViewById(R.id.parent_rl);

        if ("雄狮".equals(mAnimMessage.giftName)) {
            RelativeLayout.LayoutParams layoutParamsOne = (LayoutParams) nick_tv.getLayoutParams();
            layoutParamsOne.topMargin = DpUtil.dp2px(142);
            nick_tv.setLayoutParams(layoutParamsOne);
            RelativeLayout.LayoutParams layoutParamsTwo = (LayoutParams) noble_tv.getLayoutParams();
            layoutParamsTwo.topMargin = DpUtil.dp2px(144);
            noble_tv.setLayoutParams(layoutParamsTwo);
        }else {
            RelativeLayout.LayoutParams layoutParamsOne = (LayoutParams) nick_tv.getLayoutParams();
            layoutParamsOne.topMargin = DpUtil.dp2px(135);
            nick_tv.setLayoutParams(layoutParamsOne);
            RelativeLayout.LayoutParams layoutParamsTwo = (LayoutParams) noble_tv.getLayoutParams();
            layoutParamsTwo.topMargin = DpUtil.dp2px(137);
            noble_tv.setLayoutParams(layoutParamsTwo);
        }
        //加载数据
        //用户昵称
        nick_tv.setText(mAnimMessage.userName);
        //礼物图片`
//        SVGAParser parser = new SVGAParser(getContext());
//        svga_noble.setCallback(new SVGACallback() {
//            @Override
//            public void onPause() {
//
//            }
//
//            @Override
//            public void onFinished() {
//                if (getContext() instanceof LiveDetailActivity) {
//                    ((LiveDetailActivity)getContext()).removeNobleAnim(LPNobleView.this);
//                }
//            }
//
//            @Override
//            public void onRepeat() {
//
//            }
//
//            @Override
//            public void onStep(int i, double v) {
//
//            }
//        });
//        try {
//            URL url = new URL(mAnimMessage.giftSvgaUrl);
//            parser.parse(url, new SVGAParser.ParseCompletion() {
//                @Override
//                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
//                    SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
//                    svga_noble.setImageDrawable(drawable);
//                    svga_noble.startAnimation();
//                    giftTextContainerLayout.setVisibility(View.VISIBLE);
//                    giftTextContainerLayout.startAnimation(mGiftLayoutInAnim);//开始执行显示礼物的动画
//                    mGiftLayoutInAnim.setAnimationListener(new Animation.AnimationListener() {/*显示动画的监听*/
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//                        }
//                    });
//                }
//
//                @Override
//                public void onError() {
//
//                }
//            });
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        if (!TextUtils.isEmpty(mAnimMessage.giftName)) {
//            noble_tv.setText("骑着 " + mAnimMessage.giftName + " 驾临");
//        }
//
//        //giftNumView.setTag(1);/*给数量控件设置标记*/
//        mAnimMessage.updateTime = System.currentTimeMillis();/*设置时间标记*/
//        setTag(mAnimMessage);/*设置view标识*/
    }
}
