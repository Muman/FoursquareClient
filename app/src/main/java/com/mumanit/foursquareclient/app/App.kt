package com.mumanit.foursquareclient.app

import android.app.Application
import android.content.Context

import com.facebook.stetho.Stetho
import com.jakewharton.picasso.OkHttp3Downloader
import com.mumanit.foursquareclient.dagger.AppComponent
import com.mumanit.foursquareclient.dagger.DaggerAppComponent
import com.mumanit.foursquareclient.dagger.modules.AppModule
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso

/**
 * Created by pmuciek on 7/8/17.
 */

class App : Application() {

    private var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()

        Picasso.setSingletonInstance(Picasso.Builder(this)
                .downloader(OkHttp3Downloader(component!!.okHttpClient()))
                .memoryCache(LruCache(this))
                .indicatorsEnabled(true)
                .build())

        Stetho.initializeWithDefaults(this)

        Hawk.init(this)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                .setStorage(HawkBuilder.newSqliteStorage(this))
                .setLogLevel(LogLevel.FULL)
                .build()
    }

    companion object {
        fun getAppComponent(context: Context): AppComponent? {
            return (context.applicationContext as App).component
        }
    }
}
