package com.mumanit.foursquareclient.presentation.internal.di

import com.mumanit.foursquareclient.presentation.internal.di.modules.AppModule
import com.mumanit.foursquareclient.presentation.MainActivity

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
