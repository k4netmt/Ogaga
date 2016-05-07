package com.ogaga.flash.acitivies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.client.Firebase;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

/**
 * Created by IceStone on 4/21/2016.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FirebaseClient.getUserLogin(getApplicationContext(), new FirebaseClient.LoginUser() {
            @Override
            public void onLoginSuccess(User user) {
                Intent intent = new Intent(SplashActivity.this, CatalogiesActivity.class);
                intent.putExtra("user", Parcels.wrap(user));
                startActivity(intent);
            }
        });

    }
}