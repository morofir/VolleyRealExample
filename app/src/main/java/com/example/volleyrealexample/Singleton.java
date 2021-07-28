package com.example.volleyrealexample;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton {
    RequestQueue requestQueue;
    public static Singleton instance;

    public static synchronized Singleton getInstance(Context context) {
        if(instance==null)
            instance = new Singleton(context);
        return instance;
    }

    public Singleton(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

public RequestQueue getRequestQueue(){
        return requestQueue;
}
}
