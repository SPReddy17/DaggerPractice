package com.android.daggerpractice.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.daggerpractice.R;
import com.android.daggerpractice.models.User;
import com.android.daggerpractice.viewmodels.ViewModelProviderFactory;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener{


    private static final String TAG = "AuthActivity";


    private AuthViewModel viewModel;

    private EditText userId;


    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    userId = findViewById(R.id.user_id_input);
    findViewById(R.id.login_button).setOnClickListener(this);
        viewModel = ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
        setLogo();

        subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.observeUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    Log.d(TAG, "onChanged: " + user.getEmail());
                }
            }
        });
    }

    private void setLogo(){
        requestManager
                .load(logo)
                .into((ImageView) findViewById(R.id.login_logo));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:{
                attemptLogin();
                break;
            }
        }
    }

    private void attemptLogin(){
        if(TextUtils.isEmpty(userId.getText().toString())){
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }
}