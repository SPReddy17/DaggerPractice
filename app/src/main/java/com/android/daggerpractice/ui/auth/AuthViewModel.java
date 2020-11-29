package com.android.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.android.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private final AuthApi authapi;

    private static final String TAG = "AuthViewModel";
    @Inject
    public AuthViewModel(AuthApi authapi) {
        this.authapi = authapi;
        Log.d(TAG, "AuthViewModel: viewmodel is qworking...");
        if(this.authapi == null){
            Log.d(TAG, "AuthViewModel:  auth api is null");
        }else{
            Log.d(TAG, "AuthViewModel: auth api is not null");
        }
    }


}
