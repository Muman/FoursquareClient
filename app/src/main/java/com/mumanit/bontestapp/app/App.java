package com.mumanit.bontestapp.app;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.mumanit.bontestapp.dagger.AppComponent;
import com.mumanit.bontestapp.dagger.DaggerAppComponent;
import com.mumanit.bontestapp.dagger.modules.AppModule;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

/**
 * Created by pmuciek on 7/8/17.
 */

public class App extends Application{

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

        Picasso.setSingletonInstance(new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(component.okHttpClient()))
                .memoryCache(new LruCache(this))
                .indicatorsEnabled(true)
                .build());

        Stetho.initializeWithDefaults(this);

        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build();
    }

    public static AppComponent getAppComponent(Context context){
        return ((App) context.getApplicationContext()).component;
    }
}
