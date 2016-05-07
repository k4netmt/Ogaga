package com.ogaga.flash.acitivies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.auth.core.AuthProviderType;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.extra.Constant;
import com.ogaga.flash.extra.ValiDate;
import com.ogaga.flash.helpers.AuthorHelper;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.etPhonenumber)EditText etPhonenumber;
    @Bind(R.id.etPassword)EditText etPassword;
    @Bind(R.id.btnLogin)Button btnLogin;
    @Bind(R.id.tvRegistry)TextView tvRegistry;
    @Bind(R.id.tvErrorAccount)TextView tvErrorAccount;
    @Bind(R.id.toolbarHeader)Toolbar toolbar;
    @Bind(R.id.tilPhonenumber)TextInputLayout tilPhonenumber;
    @Bind(R.id.tilPassword)TextInputLayout tilPassword;
    private Firebase mFirebaseRef;
    private User mUser;
    ValiDate mValiDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mValiDate=new ValiDate(this);
        Firebase.setAndroidContext(this);
        mFirebaseRef= FirebaseClient.getRoot();
    }

    @OnClick(R.id.btnLogin)
    protected void LoginFirebase(){
        if (mValiDate.validatePhonenumber(tilPhonenumber,etPhonenumber)==false)
            return;
        if (mValiDate.validatePassword(tilPassword,etPassword)==false)
            return;
        showProgressBar();
        tvErrorAccount.setVisibility(View.INVISIBLE);
        final User user = new User();
        user.setPhonenumber(etPhonenumber.getText().toString());
        mFirebaseRef.authWithPassword(user.getEmail(), etPassword.getText().toString(),
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authentication just completed successfully :)
                        AuthorHelper.writeString(getApplicationContext(), "uid", authData.getUid().toString());
                        FirebaseClient.getUserLogin(getApplicationContext(), new FirebaseClient.LoginUser() {
                            @Override
                            public void onLoginSuccess(User user) {Intent intent = new Intent();intent.putExtra("user", Parcels.wrap(user));
                                setResult(Constant.LOGIN_SUCCESS_CODE, intent);
                                Toast.makeText(getApplicationContext(), getResources().getText(R.string.login_success), Toast.LENGTH_LONG);
                                finish();//finishing activity
                            }
                        });
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        tvErrorAccount.setText(getString(R.string.error_account_notexists));
                        tvErrorAccount.setVisibility(View.VISIBLE);
                        hideProgressBar();
                    }
                });
    }

    @OnClick(R.id.tvRegistry)
    public void onRegistry(){
        Intent intent=new Intent(this,UserRegistryActivity.class);
        startActivityForResult(intent, Constant.REGISTRY_SUCCESS_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REGISTRY_SUCCESS_CODE) {
                if (data != null) {
                    mUser=Parcels.unwrap(data.getParcelableExtra("user"));
                    Intent intent=new Intent();
                    intent.putExtra("user", Parcels.wrap(mUser));
                    setResult(Constant.LOGIN_SUCCESS_CODE, intent);
                    finish();//finishing activity
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    MenuItem miActionProgressItem;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sell_activity, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
        btnLogin.setEnabled(false);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
        btnLogin.setEnabled(true);
    }

}
