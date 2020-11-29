package com.android.daggerpractice.di;

import com.android.daggerpractice.di.auth.AuthModule;
import com.android.daggerpractice.di.auth.AuthViewModelsModule;
import com.android.daggerpractice.ui.auth.AuthActivity;
import com.android.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();


    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();


}
