package com.mumanit.foursquareclient.data

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.mumanit.foursquareclient.data.api.FoursquareApi
import com.mumanit.foursquareclient.data.cache.VenuesCache
import com.mumanit.foursquareclient.data.mappers.VenueDataMapper
import com.mumanit.foursquareclient.domain.model.VenueData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class VenuesDataManagerImpl(
        private val foursquareApi: FoursquareApi,
        private val venueDataMapper: VenueDataMapper,
        private val venuesCache: VenuesCache,
        private val coroutineLocationProvider: FusedLocationProviderClient,
        private val clientId: String,
        private val clientSecret: String) : VenuesDataManager {

    override suspend fun getVenues(): List<VenueData> {
        val location = getUserLocation()
        val response = withContext(Dispatchers.IO) {
           foursquareApi.exploreVenuesSuspend(clientId, clientSecret, location.latitude.toString() + "," + location.longitude.toString())
        }

        val newData = venueDataMapper.map(response)
        venuesCache.save(newData)

        return newData
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
}
