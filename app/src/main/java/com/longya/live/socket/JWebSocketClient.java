package com.longya.live.socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.longya.live.CommonAppConfig;
import com.longya.live.Constant;
import com.longya.live.model.MatchSocketBean;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 开发公司：东莞市梦幻科技有限公司
 * 时间：2020/11/24
 */

public class JWebSocketClient {
    public static final String TYPE_CONNECT = "register";
    public static final String TYPE_HEART_BEAT = "heartbeat";

    private WebSocketHandler mHandler;
    private WebSocketClient mWebSocketClient;
    private Timer mTimer;
    private boolean isConn = false;//是否连接成功

    public JWebSocketClient(URI serverUri, String type, WebSocketMsgListener listener) {
        mHandler = new WebSocketHandler(listener);
        mTimer = new Timer();
        mWebSocketClient = new WebSocketClient(serverUri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.e("JWebSocketClient", "onOpen()");
                if(type == TYPE_CONNECT) {
                    connectSocket();
                }
            }

            @Override
            public void onMessage(String message) {
                Log.e("JWebSocketClient", "onMessage()" + message);
                JSONObject jsonObject = JSON.parseObject(message);
                Integer code = jsonObject.getInteger("code");
                if(code != null) {
                    if (code == 101) {//建立连接成功
                        isConn = true;
                        startHeartBeat();
                    }else if (code == 100) {
                        MatchSocketBean data = JSONObject.parseObject(jsonObject.getString("data"), MatchSocketBean.class);
                        if (data != null) {
                            if (mHandler != null) {
                                Message msg = Message.obtain();
                                msg.what = Constant.WEB_SOCKET_WHAT_UPDATE_MSG;
                                msg.obj = data;
                                mHandler.sendMessage(msg);
                            }
                        }
                    }
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e("JWebSocketClient", "onClose()" + code);
                if (code != 1000) {
                    if (mHandler != null) {
                        Message msg = Message.obtain();
                        msg.what = Constant.WEB_SOCKET_WHAT_RECONNECTION;
                        mHandler.sendMessage(msg);
                    }
                }
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
                Log.e("JWebSocketClient", "onError()" + ex.getMessage());
            }
        };
    }

    public void conn() {
//        try {
            if (mWebSocketClient != null){
                mWebSocketClient.connect();
            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void connectSocket() {
        if (mWebSocketClient != null && mWebSocketClient.isOpen()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", TYPE_CONNECT);
//            jsonObject.put("token", CommonAppConfig.getInstance().getToken());
            mWebSocketClient.send(jsonObject.toString());
        }
    }

    private void startHeartBeat() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", TYPE_HEART_BEAT);
                if (mWebSocketClient.isOpen()) {
                    mWebSocketClient.send(jsonObject.toString());
                }
            }
        }, 0, 45000);
    }

    public void disconn() {
        try {
            if (mTimer != null) {
                mTimer.cancel();
            }
            if (mWebSocketClient != null) {
                mWebSocketClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWebSocketClient = null;
        }
    }

    public boolean isConn() {
        return isConn;
    }

    private static class WebSocketHandler extends Handler {
        private WebSocketMsgListener mListener;

        public WebSocketHandler(WebSocketMsgListener listener) {
            mListener = new WeakReference<>(listener).get();
        }

        @Override
        public void handleMessage(Message msg) {
            if (mListener == null) {
                return;
            }
            switch (msg.what) {
                case Constant.WEB_SOCKET_WHAT_RECONNECTION:
                    mListener.onReconnection();
                    break;
                case Constant.WEB_SOCKET_WHAT_UPDATE_MSG:
                    mListener.onMessage((MatchSocketBean) msg.obj);
                    break;
            }
        }
    }
}
