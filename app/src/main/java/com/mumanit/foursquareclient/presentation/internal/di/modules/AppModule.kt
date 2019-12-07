package com.mumanit.foursquareclient.presentation.internal.di.modules

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.mumanit.foursquareclient.presentation.internal.di.qualifiers.ClientId
import com.mumanit.foursquareclient.presentation.internal.di.qualifiers.ClientSecretKey
import com.mumanit.foursquareclient.domain.repository.VenuesRepository
import com.mumanit.foursquareclient.data.repository.VenuesRepositoryImpl
import com.mumanit.foursquareclient.data.api.FoursquareApi
import com.mumanit.foursquareclient.data.cache.VenuesCache
import com.mumanit.foursquareclient.data.cache.VenuesSharedPrefCache
import com.mumanit.foursquareclient.data.db.dao.VenuesDao
import com.mumanit.foursquareclient.data.mappers.VenueDataMapper
import com.mumanit.foursquareclient.data.mappers.VenueDataMapperImpl
import com.mumanit.foursquareclient.domain.interactor.GetFirstRecommendedVenueWithMenu
import com.mumanit.foursquareclient.domain.interactor.GetFirstRecommendedVenueWithMenuImpl
import com.mumanit.foursquareclient.domain.interactor.GetRecommendedVenues
import com.mumanit.foursquareclient.domain.interactor.GetRecommendedVenuesImpl
import dagger.Module
import dagger.Provides
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, NetworkingModule::class, DatabaseModule::class])
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
                                          venuesDao: VenuesDao,
                                          fusedLocationProvider: FusedLocationProviderClient,
                                          @ClientId clientId: String,
                                          @ClientSecretKey clientSecret: String): VenuesRepository {

        return VenuesRepositoryImpl(foursquareApi, venueDataMapper, venuesDao, fusedLocationProvider, clientId, clientSecret)
    }

    @Singleton
    @Provides
    internal fun provideGetVenuesInteractor(venuesDataManager: VenuesRepository): GetRecommendedVenues {
        return GetRecommendedVenuesImpl(venuesDataManager)
    }

    @Singleton
    @Provides
    internal fun provideGetMostRecommendedVenueInteractor(venuesDataManager: VenuesRepository): GetFirstRecommendedVenueWithMenu {
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
