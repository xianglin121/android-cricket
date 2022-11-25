package com.longya.live.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WuXiaolong on 2016/3/24.
 * github:https://github.com/WuXiaolong/
 * 微信公众号：吴小龙同学
 * 个人博客：http://wuxiaolong.me/
 */
public class ApiClient {
    public static Retrofit mRetrofit;
    /**
     * 设置连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 60;
    /**
     * 设置读取超时时间
     */
    public final static int READ_TIMEOUT = 100;
    /**
     * 设置写的超时时间
     */
    public final static int WRITE_TIMEOUT = 60;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

//            if (BuildConfig.DEBUG) {
                // Log信息拦截器
//                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(new LogInterceptor());
//            }
            OkHttpClient okHttpClient = builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                    .build();
            //将请求接口里为空字符串的参数去掉
//            TypeAdapter<String> stringTypeAdapter = new TypeAdapter<String>() {
//                @Override
//                public String read(JsonReader in) throws IOException {
//                    JsonToken peek = in.peek();
//                    if (peek == JsonToken.NULL) {
//                        in.nextNull();
//                        return null;
//                    }
//                    /* coerce booleans to strings for backwards compatibility */
//                    if (peek == JsonToken.BOOLEAN) {
//                        return Boolean.toString(in.nextBoolean());
//                    }
//                    return in.nextString();
//                }
//                @Override
//                public void write(JsonWriter out, String value) throws IOException {
//                    // 下面这个if是关键代码，指定序列化时，遇到空串则直接输出null值。
//                    // 由于Gson默认是不序列化null的，所以这里就相当于在序列化时排除了空串的字段
//                    if ("".equals(value)) {
//                        out.nullValue();
//                        return;
//                    }
//                    out.value(value);
//                }
//            };
//
//            Gson gson = new GsonBuilder().registerTypeAdapter(String.class, stringTypeAdapter).create();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

}
