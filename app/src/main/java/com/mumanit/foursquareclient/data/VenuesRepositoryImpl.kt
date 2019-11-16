package com.mumanit.foursquareclient.data

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.mumanit.foursquareclient.data.api.FoursquareApi
import com.mumanit.foursquareclient.data.cache.VenuesCache
import com.mumanit.foursquareclient.data.mappers.VenueDataMapper
import com.mumanit.foursquareclient.domain.model.VenueData
import com.mumanit.foursquareclient.domain.model.VenueMenu
import com.mumanit.foursquareclient.domain.model.VenueWithMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class VenuesRepositoryImpl(
        private val foursquareApi: FoursquareApi,
        private val venueDataMapper: VenueDataMapper,
        private val venuesCache: VenuesCache,
        private val coroutineLocationProvider: FusedLocationProviderClient,
        private val clientId: String,
        private val clientSecret: String) : VenuesRepository {

    override suspend fun getVenueMenu(id: String): VenueMenu? {
        val menu = withContext(Dispatchers.IO) {
            foursquareApi.menuDetails(id, clientId, clientSecret)
        }

        return VenueMenu(menu.menuId, menu.description, menu.name)
    }

    override suspend fun geNearbyRecommendedVenues(): List<VenueData> {
        val location = getUserLocation()

        val response = withContext(Dispatchers.IO) {
            foursquareApi.exploreVenues(clientId, clientSecret,location.latitude.toString() + "," + location.longitude.toString())
        }

        val newData = venueDataMapper.map(response)
        venuesCache.save(newData)

        return newData
    }

    override suspend fun getNearbyRecommendedVenueWithMenu(): VenueWithMenu {
        val firstRecommendedVenue = geNearbyRecommendedVenues().firstOrNull()

        if (null != firstRecommendedVenue) {
            val menuForRecommendedVenue = getVenueMenu(firstRecommendedVenue.id)

            return VenueWithMenu(menuForRecommendedVenue, firstRecommendedVenue)

        } else throw RuntimeException("no recommended venue found")

    }

    private suspend fun getUserLocation() : Location {
        return suspendCoroutine {continuation ->
            coroutineLocationProvider.lastLocation.addOnSuccessListener {
                continuation.resume(it)
            }

            coroutineLocationProvider.lastLocation.addOnFailureListener {
                continuation.resumeWithException(it)
            }
        }
    }

    private fun getUserLocationWithFlow(): Flow<Location> {
        return flow {
            emit(getUserLocation())
        }
    }

    @ExperimentalCoroutinesApi
    override fun getRecommendedVenueWithMenuWithFLow(): Flow<VenueWithMenu> {
        return getUserLocationWithFlow()
                .map { location -> foursquareApi.exploreVenues(clientId, clientSecret, location.latitude.toString() + "," + location.longitude.toString()) }
                .flowOn(Dispatchers.IO)
                .map {response -> venueDataMapper.map(response) }
                .flowOn(Dispatchers.Default)
                .map {
                    val loadedVenue = it.firstOrNull()
                    val menu = getVenueMenu(loadedVenue!!.id)
                    VenueWithMenu(menu!!, loadedVenue)
                }.flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    override fun getRecommendedVenuesWithFlow(): Flow<List<VenueData>> {
        return flow {
            val location = getUserLocation()
            val response = foursquareApi.exploreVenues(clientId, clientSecret, location.latitude.toString() + "," + location.longitude.toString())
            val newData = venueDataMapper.map(response)
            venuesCache.save(newData)
            emit(newData)
        }.flowOn(Dispatchers.IO)

    }
}
