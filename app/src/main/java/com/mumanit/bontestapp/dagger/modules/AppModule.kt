package com.mumanit.bontestapp.dagger.modules

import android.content.Context

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.android.gms.location.LocationRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mumanit.bontestapp.dagger.qualifiers.ClientId
import com.mumanit.bontestapp.dagger.qualifiers.ClientSecretKey
import com.mumanit.bontestapp.dagger.qualifiers.Foursquare
import com.mumanit.bontestapp.data.cache.VenuesCache
import com.mumanit.bontestapp.data.VenuesDataManager
import com.mumanit.bontestapp.data.VenuesDataManagerImpl
import com.mumanit.bontestapp.data.cache.VenuesSharedPrefCache
import com.mumanit.bontestapp.data.mappers.VenueDataMapper
import com.mumanit.bontestapp.data.mappers.VenueDataMapperImpl
import com.mumanit.bontestapp.domain.GetVenuesInteractor
import com.mumanit.bontestapp.domain.GetVenuesInteractorImpl
import com.mumanit.bontestapp.ui.venues.VenuesListPresenter
import com.mumanit.bontestapp.ui.venues.VenuesListPresenterImpl
import com.mumanit.bontestapp.data.api.FoursquareApi
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [ViewModelModule::class])
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
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

    @Singleton
    @Provides
    internal fun provideVenuesDataManager(foursquareApi: FoursquareApi,
                                          venueDataMapper: VenueDataMapper,
                                          venuesCache: VenuesCache,
                                          locationProvider: ReactiveLocationProvider,
                                          @ClientId clientId: String,
                                          @ClientSecretKey clientSecret: String): VenuesDataManager {

        return VenuesDataManagerImpl(foursquareApi, venueDataMapper, venuesCache, locationProvider, clientId, clientSecret)
    }

    @Singleton
    @Provides
    internal fun provideGetVenuesInteractor(venuesDataManager: VenuesDataManager): GetVenuesInteractor {
        return GetVenuesInteractorImpl(venuesDataManager)
    }

    @Provides
    internal fun provideVenuesListPresenter(getVenuesInteractor: GetVenuesInteractor): VenuesListPresenter {
        return VenuesListPresenterImpl(getVenuesInteractor)
    }

    @Provides
    @Singleton
    internal fun provideVenueDataMapper(): VenueDataMapper {
        return VenueDataMapperImpl()
    }

    @Provides
    @Singleton
    internal fun provideVenuesCache(): VenuesCache {
        return VenuesSharedPrefCache()
    }

    @Provides
    @Singleton
    internal fun provideLocationProvider(context: Context): ReactiveLocationProvider {
        return ReactiveLocationProvider(context)
    }

    @Provides
    @Singleton
    internal fun provideLocationRequest(): LocationRequest {
        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setNumUpdates(5)
                .setInterval(30000)
    }
}
