package com.android.daggerpractice.di;

import com.android.daggerpractice.di.auth.AuthModule;
import com.android.daggerpractice.di.auth.AuthScope;
import com.android.daggerpractice.di.auth.AuthViewModelsModule;
import com.android.daggerpractice.di.main.MainFragmentBuildersModule;
import com.android.daggerpractice.di.main.MainModule;
import com.android.daggerpractice.di.main.MainScope;
import com.android.daggerpractice.di.main.MainViewModelsModule;
import com.android.daggerpractice.ui.auth.AuthActivity;
import com.android.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();



    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class,
                    MainViewModelsModule.class,
                    MainModule.class
            }
    )
    abstract MainActivity contributeMainActivity();


}
