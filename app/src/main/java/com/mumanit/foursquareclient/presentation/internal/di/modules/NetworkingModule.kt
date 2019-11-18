package com.mumanit.foursquareclient.presentation.internal.di.modules

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mumanit.foursquareclient.presentation.internal.di.qualifiers.ClientId
import com.mumanit.foursquareclient.presentation.internal.di.qualifiers.ClientSecretKey
import com.mumanit.foursquareclient.presentation.internal.di.qualifiers.Foursquare
import com.mumanit.foursquareclient.data.api.FoursquareApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkingModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor(StethoInterceptor())
                .cache(Cache(context.cacheDir, 250000000))
                .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    @Singleton
    @Provides
    internal fun provideFoursquareApi(gson: Gson, okHttpClient: OkHttpClient, @Foursquare url: String): FoursquareApi {

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        return retrofit.create(FoursquareApi::class.java)
    }

    @Provides
    @Foursquare
    internal fun provideFoursquareBaseUrl(): String {
        return "https://api.foursquare.com/v2/"
    }

    @Provides
    @ClientSecretKey
    internal fun provideClientSecretKey(): String {
        return "JCBDNKFN3W1JXAIPSCL4J5Y2HF2ZJJU0NS2N1YJ5CVHBABFW"
    }

    @Provides
    @ClientId
    internal fun provideClientId(): String {
        return "XKKJLP3WXETHVZ3SCALGJGPL3HFNDVQ5IQBTVHD0JGQEHJMI"
    }



}
