package com.mumanit.foursquareclient.dagger

import com.mumanit.foursquareclient.dagger.modules.AppModule
import com.mumanit.foursquareclient.ui.MainActivity

import javax.inject.Singleton

import dagger.Component
import okhttp3.OkHttpClient

/**
 * Created by pmuciek on 7/8/17.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun okHttpClient(): OkHttpClient
    fun inject(mainActivity: MainActivity)
}
