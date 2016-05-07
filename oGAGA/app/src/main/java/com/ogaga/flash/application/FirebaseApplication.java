package com.ogaga.flash.application;

import android.app.Application;
import com.firebase.client.Firebase;
import com.ogaga.flash.clients.FirebaseClient;

/**
 * Created by Kanet on 4/18/2016.
 */
public class FirebaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
