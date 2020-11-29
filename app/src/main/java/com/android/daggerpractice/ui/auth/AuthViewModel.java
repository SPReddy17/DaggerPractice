package com.android.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.android.daggerpractice.SessionManager;
import com.android.daggerpractice.models.User;
import com.android.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private final AuthApi authapi;

    private static final String TAG = "AuthViewModel";

    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authapi, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.authapi = authapi;
        Log.d(TAG, "AuthViewModel: viewmodel is working...");

    }

    public void authenticateWithId(int userId){

        Log.d(TAG, "authenticateWithId: attempting to login");
        sessionManager.authenticateWithId(queryUserId(userId));
    }


    private LiveData<AuthResource<User>> queryUserId(int userId){
       return LiveDataReactiveStreams.fromPublisher(
                authapi.getUser(userId)
                        // instead of calling onError (error happens)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if (user.getId() == -1) {
                                    return AuthResource.error("Could not authenticate",(User)null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                        .subscribeOn(Schedulers.io()));
    }
    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser();
    }


}
