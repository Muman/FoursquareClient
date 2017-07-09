package com.mumanit.bontestapp.app;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.mumanit.bontestapp.dagger.AppComponent;
import com.mumanit.bontestapp.dagger.DaggerAppComponent;
import com.mumanit.bontestapp.dagger.modules.AppModule;

/**
 * Created by pmuciek on 7/8/17.
 */

public class App extends Application{

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        Stetho.initializeWithDefaults(this);
    }

    public static AppComponent getAppComponent(Context context){
        return ((App) context.getApplicationContext()).component;
    }
}
