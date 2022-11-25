package com.longya.live.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.longya.live.R;
import com.longya.live.view.BaseActivity;
import com.tencent.qcloud.tuikit.tuichat.component.face.Emoji;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceManager;
import com.tencent.qcloud.tuikit.tuichat.ui.view.input.face.FaceFragment;
import com.tencent.qcloud.tuikit.tuichat.util.ChatMessageInfoUtil;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatLog;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2021/6/12
 */
public class InputChatMsgDialog extends Dialog implements View.OnClickListener {
    private static final int STATE_NONE_INPUT = 0;
    private static final int STATE_SOFT_INPUT = 1;
    private static final int STATE_FACE_INPUT = 2;

    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mCurrentState;
    private boolean isSelectImg;
    private String name;
    private InputMethodManager imm;
    private int mLastDiff = 0;

    private EditText et_input;
    private DoCommentCallback mCallback;
    private RelativeLayout more_groups;
    private FaceFragment mFaceFragment;//表情界面

    public InputChatMsgDialog(Context context, int theme, DoCommentCallback callback) {
        super(context, theme);
        this.mContext = context;
        mCallback = callback;
        init();
        setLayout();
    }

    private void init() {
        setContentView(R.layout.dialog_chat_text_input);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        et_input = findViewById(R.id.et_input);
        more_groups = findViewById(R.id.more_groups);
        findViewById(R.id.iv_emoji).setOnClickListener(this);
        findViewById(R.id.tv_send).setOnClickListener(this);
        et_input.requestFocus();
        mCurrentState = STATE_SOFT_INPUT;
        findViewById(R.id.content).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                Rect r = new Rect();
                //获取当前界面可视部分
                InputChatMsgDialog.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = InputChatMsgDialog.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;

                if (heightDifference <= 0 && mLastDiff > 0) {
                    InputChatMsgDialog.this.dismiss();
                }
                mLastDiff = heightDifference;
            }
        });

        et_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                Log.d("My test", "onKey " + keyEvent.getCharacters());
                return false;
            }
        });

        setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                    InputChatMsgDialog.this.dismiss();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_emoji:
                if (mCurrentState == STATE_FACE_INPUT) {
                    mCurrentState = STATE_NONE_INPUT;
                    more_groups.setVisibility(View.GONE);
                }else {
                    mCurrentState = STATE_FACE_INPUT;
                    showFaceViewGroup();
                }
                break;
            case R.id.tv_send:
//                if (TextUtils.isEmpty(et_input.getText().toString())) {
//                    T.showShort(getContext(), WordUtil.getString(getContext(), R.string.circle_send_content_empty));
//                    return;
//                }
//                if (mCallback != null) {
//                    mCallback.onComment(et_input.getText().toString());
//                }
                break;
        }
    }

    public void hideSoftInput() {
        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
        et_input.clearFocus();
        more_groups.setVisibility(View.GONE);
    }

    private void showFaceViewGroup() {
        if (mFragmentManager == null) {
            mFragmentManager = scanForActivity(getContext()).getSupportFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        hideSoftInput();
        more_groups.setVisibility(View.VISIBLE);
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
        mFragmentManager.beginTransaction().replace(R.id.more_groups, mFaceFragment).commitAllowingStateLoss();
//        if (mChatInputHandler != null) {
//            et_input.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mChatInputHandler.onInputAreaClick();
//                }
//            }, 100);
    }

    private static AppCompatActivity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof AppCompatActivity)
            return (AppCompatActivity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }

    private void setLayout() {
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(p);
        setCancelable(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //dismiss之前重置mLastDiff值避免下次无法打开
        mLastDiff = 0;
    }

    public interface DoCommentCallback {
        void onComment(String content);

        void onImg();
    }
}
