package com.mumanit.bontestapp.dagger.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.android.gms.location.LocationRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mumanit.bontestapp.dagger.qualifiers.ClientId;
import com.mumanit.bontestapp.dagger.qualifiers.ClientSecretKey;
import com.mumanit.bontestapp.dagger.qualifiers.Foursquare;
import com.mumanit.bontestapp.data.cache.VenuesCache;
import com.mumanit.bontestapp.data.VenuesDataManager;
import com.mumanit.bontestapp.data.VenuesDataManagerImpl;
import com.mumanit.bontestapp.data.cache.VenuesSharedPrefCache;
import com.mumanit.bontestapp.data.mappers.VenueDataMapper;
import com.mumanit.bontestapp.data.mappers.VenueDataMapperImpl;
import com.mumanit.bontestapp.domain.GetVenuesInteractor;
import com.mumanit.bontestapp.domain.GetVenuesInteractorImpl;
import com.mumanit.bontestapp.ui.venues.VenuesListPresenter;
import com.mumanit.bontestapp.ui.venues.VenuesListPresenterImpl;
import com.mumanit.bontestapp.data.api.FoursquareApi;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pmuciek on 7/8/17.
 */

@Module
public class AppModule {

    private final Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .cache(new Cache(context.getCacheDir(), 250000000))
                .build();
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Singleton
    @Provides
    FoursquareApi provideFoursquareApi(Gson gson, OkHttpClient okHttpClient, @Foursquare String url) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(FoursquareApi.class);
    }

    @Provides
    @Foursquare
    String provideFoursquareBaseUrl(){
        return "https://api.foursquare.com/v2/";
    }

    @Provides
    @ClientSecretKey
    String provideClientSecretKey(){
        return "JCBDNKFN3W1JXAIPSCL4J5Y2HF2ZJJU0NS2N1YJ5CVHBABFW";
    }

    @Provides
    @ClientId
    String provideClientId(){
        return "XKKJLP3WXETHVZ3SCALGJGPL3HFNDVQ5IQBTVHD0JGQEHJMI";
    }

    @Singleton
    @Provides
    VenuesDataManager provideVenuesDataManager(FoursquareApi foursquareApi,
                                               VenueDataMapper venueDataMapper,
                                               VenuesCache venuesCache,
                                               ReactiveLocationProvider locationProvider,
                                               @ClientId String clientId,
                                               @ClientSecretKey String clientSecret) {

        return new VenuesDataManagerImpl(foursquareApi, venueDataMapper, venuesCache, locationProvider, clientId, clientSecret);
    }

    @Singleton
    @Provides
    GetVenuesInteractor provideGetVenuesInteractor(VenuesDataManager venuesDataManager){
        return new GetVenuesInteractorImpl(venuesDataManager);
    }

    @Provides
    VenuesListPresenter provideVenuesListPresenter(GetVenuesInteractor getVenuesInteractor) {
        return new VenuesListPresenterImpl(getVenuesInteractor);
    }

    @Provides
    @Singleton
    VenueDataMapper provideVenueDataMapper() {
        return new VenueDataMapperImpl();
    }

    @Provides
    @Singleton
    VenuesCache provideVenuesCache() {
        return new VenuesSharedPrefCache();
    }

    @Provides
    @Singleton
    ReactiveLocationProvider provideLocationProvider(Context context) {
        return new ReactiveLocationProvider(context);
    }

    @Provides
    @Singleton
    LocationRequest provideLocationRequest() {
        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setNumUpdates(5)
                .setInterval(30000);
    }
}
