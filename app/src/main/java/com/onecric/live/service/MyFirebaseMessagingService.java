package com.onecric.live.service;



import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.onecric.live.util.LogUtil;

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
        LogUtil.e("token::::" + s);
    }

}


