package com.julioalfaro.loginapplication;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class App extends Application {
    private static App instance;
    RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        this.queue = Volley.newRequestQueue(this);
        this.instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    public RequestQueue getQueue() {
        return queue;
    }
}
