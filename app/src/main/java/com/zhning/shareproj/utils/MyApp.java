package com.zhning.shareproj.utils;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by baidu on 16/5/14.
 */
public class MyApp extends Application {
    public static final String TAG = MyApp.class.getSimpleName();

    private long userId;
    private ModelUtil modelUtil;
    private static final String ipv4 = "http://172.21.234.53:8080/together";
    private RequestQueue mRequestQueue;
    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        modelUtil = new ModelUtil(this);
        userId = Constants.USER_1;
    }

    public static synchronized MyApp getInstance() {
        return mInstance;
    }

    public static String getIpv4() {
        return ipv4;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public long getCurrentUser(){
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
