package com.onecric.live.service;


import static com.tencent.qcloud.tim.uikit.TUIKit.getAppContext;
import static com.tencent.qcloud.tuikit.tuicontact.TUIContactService.TAG;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "New Message Received! " + remoteMessage.getNotification().getTitle(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    private void getFCMToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "getInstanceId failed" + task.getException());
                    return;
                }
                String token = task.getResult() != null ? task.getResult().getToken() : "Token is null";
                Toast.makeText(getAppContext(), token, Toast.LENGTH_SHORT).show();
            }
        });
    }

}


