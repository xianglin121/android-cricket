package com.onecric.live.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.onecric.live.R;
import com.onecric.live.activity.CommunityCommentActivity;
import com.onecric.live.activity.HeadlineDetailActivity;
import com.onecric.live.activity.LiveMovingCommentActivity;
import com.onecric.live.activity.VideoPagerActivity;
import com.onecric.live.util.DpUtil;
import com.onecric.live.util.GlideUtil;
import com.onecric.live.util.ToastUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.qcloud.tuikit.tuichat.component.face.Emoji;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.tencent.qcloud.tuikit.tuichat.ui.view.input.face.FaceFragment;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/6/12
 */
public class InputVideoCommentMsgDialog extends DialogFragment implements View.OnClickListener {
    public static final int TYPE_COMMENT = 0;
    public static final int TYPE_REPLY = 1;

    private Context mContext;
    private int mCurrentType;
    private Integer mCid;
    private boolean isSelectImg;
    private FragmentManager mFragmentManager;
    private InputMethodManager imm;

    private EditText et_input;
    private ImageView iv_img;
    private ImageView iv_video;
    private RelativeLayout more_container;
    private FaceFragment mFaceFragment;//表情界面
    private LinearLayout ll_img;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mContext = getContext();
        Dialog dialog = new Dialog(getContext(), R.style.dialog2);
        dialog.setContentView(R.layout.dialog_video_comment_text_input);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        setLayout(dialog);
        initView(dialog);
        return dialog;
    }

    private void setLayout(Dialog dialog) {
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void initView(Dialog dialog) {
        mCurrentType = getArguments().getInt("type");
        mCid = getArguments().getInt("cid");
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        et_input = dialog.findViewById(R.id.et_input);
        iv_img = dialog.findViewById(R.id.iv_img);
        iv_video = dialog.findViewById(R.id.iv_video);
        more_container = dialog.findViewById(R.id.more_container);
        ll_img = dialog.findViewById(R.id.ll_img);
        dialog.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_input.getText().toString())) {
                    ToastUtil.show(getActivity().getString(R.string.tip_input_chat_msg_empty));
                    return;
                }
                if (mCurrentType == TYPE_REPLY) {
                    if (getActivity() instanceof VideoPagerActivity) {
                        ((VideoPagerActivity) getActivity()).doComment(et_input.getText().toString(), mCid);
                    }
                } else {
                    if (getActivity() instanceof VideoPagerActivity) {
                        ((VideoPagerActivity) getActivity()).doComment(et_input.getText().toString(), null);
                    }
                }
                et_input.setText("");
                ll_img.removeAllViews();
                dismiss();
            }
        });
        et_input.requestFocus();
        et_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    more_container.setVisibility(View.GONE);
                    et_input.requestFocus();
                    imm.showSoftInput(et_input, 0);
                }
                return false;
            }
        });
        et_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.d("My test", "onKey " + keyEvent.getCharacters());
                return false;
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                    dismiss();
                }
                return false;
            }
        });

        iv_img.setOnClickListener(this);
        dialog.findViewById(R.id.iv_emoji).setOnClickListener(this);
    }

    public void hideSoftInput() {
        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        et_input.clearFocus();
        more_container.setVisibility(View.GONE);
    }

    private void hideFaceViewGroup() {
        more_container.setVisibility(View.GONE);
        et_input.requestFocus();
        imm.showSoftInput(et_input, 0);
    }

    private void showFaceViewGroup() {
        if (mFragmentManager == null) {
            mFragmentManager = getChildFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        hideSoftInput();
        ll_img.setVisibility(View.GONE);
        more_container.setVisibility(View.VISIBLE);
        et_input.requestFocus();
        mFaceFragment.setListener(new FaceFragment.OnEmojiClickListener() {
            @Override
            public void onEmojiDelete() {
                int index = et_input.getSelectionStart();
                Editable editable = et_input.getText();
                boolean isFace = false;
                if (index <= 0) {
                    return;
                }
                if (editable.charAt(index - 1) == ']') {
                    for (int i = index - 2; i >= 0; i--) {
                        if (editable.charAt(i) == '[') {
                            String faceChar = editable.subSequence(i, index).toString();
                            if (FaceManager.isFaceChar(faceChar)) {
                                editable.delete(i, index);
                                isFace = true;
                            }
                            break;
                        }
                    }
                }
                if (!isFace) {
                    editable.delete(index - 1, index);
                }
            }

            @Override
            public void onEmojiClick(Emoji emoji) {
                int index = et_input.getSelectionStart();
                Editable editable = et_input.getText();
                editable.insert(index, emoji.getFilter());
                FaceManager.handlerEmojiText(et_input, editable.toString(), true);
            }

            @Override
            public void onCustomFaceClick(int groupIndex, Emoji emoji) {
//                mMessageHandler.sendMessage(ChatMessageInfoUtil.buildCustomFaceMessage(groupIndex, emoji.getFilter()));
            }
        });
        mFragmentManager.beginTransaction().replace(R.id.more_container, mFaceFragment).commitAllowingStateLoss();
//        if (mChatInputHandler != null) {
//            et_input.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mChatInputHandler.onInputAreaClick();
//                }
//            }, 100);
    }

    public void addImg(String path) {
        RoundedImageView imageView = new RoundedImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setCornerRadius(DpUtil.dp2px(6));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DpUtil.dp2px(90), DpUtil.dp2px(90));
        layoutParams.setMargins(0, 0, DpUtil.dp2px(10), DpUtil.dp2px(10));
        imageView.setLayoutParams(layoutParams);
        GlideUtil.loadImageDefault(getContext(), path, imageView);
        ll_img.addView(imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_emoji:
                if (more_container.getVisibility() == View.GONE) {
                    showFaceViewGroup();
                } else {
                    hideFaceViewGroup();
                }
                break;
            case R.id.iv_img:
                if (getActivity() instanceof HeadlineDetailActivity) {
                    ((HeadlineDetailActivity) getActivity()).openPicsSelect();
                    hideFaceViewGroup();
                    ll_img.setVisibility(View.VISIBLE);
                } else if (getActivity() instanceof LiveMovingCommentActivity) {
                    ((LiveMovingCommentActivity) getActivity()).openPicsSelect();
                    hideFaceViewGroup();
                    ll_img.setVisibility(View.VISIBLE);
                } else if (getActivity() instanceof CommunityCommentActivity) {
                    ((CommunityCommentActivity) getActivity()).openPicsSelect();
                    hideFaceViewGroup();
                    ll_img.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
