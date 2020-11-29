package com.android.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.android.daggerpractice.models.User;
import com.android.daggerpractice.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private final AuthApi authapi;

    private static final String TAG = "AuthViewModel";

    @Inject
    public AuthViewModel(AuthApi authapi) {
        this.authapi = authapi;
        Log.d(TAG, "AuthViewModel: viewmodel is qworking...");
      authapi.getUser(1)
              .toObservable()
              .subscribeOn(Schedulers.io())
              .subscribe(new Observer<User>() {
                  @Override
                  public void onSubscribe(Disposable d) {

                  }

                  @Override
                  public void onNext(User user) {
                      Log.d(TAG, "onNext: " + user.getEmail());
                  }

                  @Override
                  public void onError(Throwable e) {
                      Log.e(TAG, "onError: ", e  );
                  }

                  @Override
                  public void onComplete() {

                  }
              });
    }


}
