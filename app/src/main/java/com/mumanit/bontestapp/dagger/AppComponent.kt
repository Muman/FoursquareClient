package com.mumanit.bontestapp.dagger

import com.mumanit.bontestapp.dagger.modules.AppModule
import com.mumanit.bontestapp.ui.MainActivity

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
