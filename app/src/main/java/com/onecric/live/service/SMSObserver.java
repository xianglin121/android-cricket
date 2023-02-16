package com.onecric.live.service;

import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSObserver extends ContentObserver {
    private Context mContext;
    private Handler mHandler;
    private static int id=0;
    public SMSObserver(Context context, Handler handler) {
        super(handler);
        this.mContext = context;
        this.mHandler = handler;
    }

    //低于API level 16
/*    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange,null);
    }*/

    @Override
    public void onChange(boolean selfChange, @Nullable Uri uri) {
        super.onChange(selfChange, uri);
        if (uri.toString().contains("content://sms/raw")) {
            return;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor cursor = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String _id = cursor.getString(cursor.getColumnIndex("_id"));
                //比较id 解决重复问题
                if (id < Integer.parseInt(_id)) {
                    id = Integer.parseInt(_id);//将获取到的当前id记录，防止重复
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String time1 = dateFormat.format(new Date(Long.parseLong(date)));
                    //写自己的处理逻辑，我这里是复制验证码到剪切板

//                  if(body.contains("短信中包含某字符才匹配复制到剪切板")) {}
                    // 正则表达式截取短信中的6位验证码
                    String regEx = "(?<![0-9])([0-9]{" + 6 + "})(?![0-9])";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        String code = matcher.group(0);
//                            ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
//                            cmb.setText(code);
//                            Toast.makeText(mContext.getApplicationContext(), "复制成功" + code, Toast.LENGTH_SHORT).show();
                        mHandler.obtainMessage(1, code).sendToTarget();
                    }

                }
            }
            cursor.close();
        }

    }


}
