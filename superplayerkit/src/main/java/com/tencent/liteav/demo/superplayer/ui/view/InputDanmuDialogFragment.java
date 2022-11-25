package com.tencent.liteav.demo.superplayer.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.tencent.liteav.demo.superplayer.R;
import com.tencent.liteav.demo.superplayer.model.event.SendDanmuEvent;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.tuichat.component.face.Emoji;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.tencent.qcloud.tuikit.tuichat.ui.view.input.face.FaceFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/6/12
 */
public class InputDanmuDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final int STATE_NONE_INPUT = 0;
    public static final int STATE_SOFT_INPUT = 1;
    public static final int STATE_FACE_INPUT = 2;

    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mCurrentState;
    private InputMethodManager imm;

    private EditText et_input;
    private RelativeLayout more_groups;
    private FaceFragment mFaceFragment;//表情界面

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mContext = getContext();
        Dialog dialog  = new Dialog(getContext(), R.style.input_dialog);
        dialog.setContentView(R.layout.dialog_danmu_input);
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
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        et_input = dialog.findViewById(R.id.edit_input);
        more_groups = dialog.findViewById(R.id.more_groups);

        dialog.findViewById(R.id.iv_danmu_emoji).setOnClickListener(this);
        dialog.findViewById(R.id.tv_danmu_send).setOnClickListener(this);
        et_input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mCurrentState = STATE_SOFT_INPUT;
                    more_groups.setVisibility(View.GONE);
                    et_input.requestFocus();
                    imm.showSoftInput(et_input, 0);
                }
                return false;
            }
        });
        et_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
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

        mCurrentState = getArguments().getInt("state");
        if (mCurrentState == STATE_SOFT_INPUT) {
            et_input.requestFocus();
            imm.showSoftInputFromInputMethod(et_input.getWindowToken(), 0);
        }else if (mCurrentState == STATE_FACE_INPUT) {
            showFaceViewGroup();
        }
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_danmu_emoji) {
            if (mCurrentState == STATE_FACE_INPUT) {
                mCurrentState = STATE_NONE_INPUT;
                more_groups.setVisibility(View.GONE);
            }else {
                mCurrentState = STATE_FACE_INPUT;
                showFaceViewGroup();
            }
        }else if (v.getId() == R.id.tv_danmu_send) {
            if (TextUtils.isEmpty(et_input.getText().toString())) {
                ToastUtil.toastShortMessage("Please enter the message you want to send");
                return;
            }
//            ((LiveDetailActivity)getActivity()).sendMessage(et_input.getText().toString());
            EventBus.getDefault().post(new SendDanmuEvent(et_input.getText().toString()));
            et_input.setText("");
        }
    }

    public void hideSoftInput() {
        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        et_input.clearFocus();
        more_groups.setVisibility(View.GONE);
    }

    private void showFaceViewGroup() {
        if (mFragmentManager == null) {
            mFragmentManager = getChildFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        more_groups.setVisibility(View.VISIBLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//阻止键盘弹出
//            et_input.setShowSoftInputOnFocus(false);
//        }
        et_input.requestFocus();
        more_groups.post(new Runnable() {
            @Override
            public void run() {
                imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
            }
        });
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
        mFragmentManager.beginTransaction().replace(R.id.more_groups, mFaceFragment).commitAllowingStateLoss();
//        if (mChatInputHandler != null) {
//            et_input.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mChatInputHandler.onInputAreaClick();
//                }
//            }, 100);
    }
}
