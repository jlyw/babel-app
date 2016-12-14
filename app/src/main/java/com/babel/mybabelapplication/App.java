package com.babel.mybabelapplication;

import android.app.Application;

import io.realm.Realm;

public class App extends Application {
    //  Instance singleton
    private static App sharedInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        App.sharedInstance = this;
    }

    public static App getSharedInstance() {
        return sharedInstance;
    }
}
