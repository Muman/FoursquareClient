package com.mumanit.foursquareclient.dagger.modules

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.mumanit.foursquareclient.dagger.qualifiers.ClientId
import com.mumanit.foursquareclient.dagger.qualifiers.ClientSecretKey
import com.mumanit.foursquareclient.data.VenuesDataManager
import com.mumanit.foursquareclient.data.VenuesDataManagerImpl
import com.mumanit.foursquareclient.data.api.FoursquareApi
import com.mumanit.foursquareclient.data.cache.VenuesCache
import com.mumanit.foursquareclient.data.cache.VenuesSharedPrefCache
import com.mumanit.foursquareclient.data.mappers.VenueDataMapper
import com.mumanit.foursquareclient.data.mappers.VenueDataMapperImpl
import com.mumanit.foursquareclient.domain.GetFirstRecommendedVenueWithMenuInteractor
import com.mumanit.foursquareclient.domain.GetFirstRecommendedVenueWithMenuImpl
import com.mumanit.foursquareclient.domain.GetVenuesInteractor
import com.mumanit.foursquareclient.domain.GetVenuesInteractorImpl
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, NetworkingModule::class])
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    internal fun provideVenuesDataManager(foursquareApi: FoursquareApi,
                                          venueDataMapper: VenueDataMapper,
                                          venuesCache: VenuesCache,
                                          fusedLocationProvider: FusedLocationProviderClient,
                                          @ClientId clientId: String,
                                          @ClientSecretKey clientSecret: String): VenuesDataManager {

        return VenuesDataManagerImpl(foursquareApi, venueDataMapper, venuesCache, fusedLocationProvider, clientId, clientSecret)
    }

    @Singleton
    @Provides
    internal fun provideGetVenuesInteractor(venuesDataManager: VenuesDataManager): GetVenuesInteractor {
        return GetVenuesInteractorImpl(venuesDataManager)
    }

    @Singleton
    @Provides
    internal fun provideGetMostRecommendedVenueInteractor(venuesDataManager: VenuesDataManager): GetFirstRecommendedVenueWithMenuInteractor {
        return GetFirstRecommendedVenueWithMenuImpl(venuesDataManager)
    }

    @Provides
    @Singleton
    internal fun provideVenueDataMapper(): VenueDataMapper {
        return VenueDataMapperImpl()
    }

    @Provides
    @Singleton
    internal fun provideFusedLocationProvider(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
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
