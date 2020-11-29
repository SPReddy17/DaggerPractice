package com.android.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.daggerpractice.models.User;
import com.android.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private final AuthApi authapi;

    private static final String TAG = "AuthViewModel";

    private MediatorLiveData<User> authUser = new MediatorLiveData<>();
    @Inject
    public AuthViewModel(AuthApi authapi) {
        this.authapi = authapi;
        Log.d(TAG, "AuthViewModel: viewmodel is working...");

    }

    public void authenticateWithId(int userId){

        final LiveData<User> source = LiveDataReactiveStreams.fromPublisher(
                authapi.getUser(userId)
                .subscribeOn(Schedulers.io())
        );
        authUser.addSource(source, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                authUser.setValue(user);
                authUser.removeSource(source);
            }
        });
    }
    public LiveData<User> observeUser(){
        return authUser;
    }


}
