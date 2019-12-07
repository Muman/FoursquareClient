package com.mumanit.foursquareclient.data.repository

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.mumanit.foursquareclient.data.api.FoursquareApi
import com.mumanit.foursquareclient.data.db.dao.VenuesDao
import com.mumanit.foursquareclient.data.db.entity.VenueEntity
import com.mumanit.foursquareclient.data.mappers.VenueDataMapper
import com.mumanit.foursquareclient.domain.model.VenueDomainModel
import com.mumanit.foursquareclient.domain.model.VenueMenuDomainModel
import com.mumanit.foursquareclient.domain.model.VenueWithMenuDomainModel
import com.mumanit.foursquareclient.domain.repository.VenuesRepository
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
        private val venuesDao: VenuesDao,
        private val coroutineLocationProvider: FusedLocationProviderClient,
        private val clientId: String,
        private val clientSecret: String) : VenuesRepository {

    override suspend fun getVenueMenu(id: String): VenueMenuDomainModel? {
        val menu = withContext(Dispatchers.IO) {
            foursquareApi.menuDetails(id, clientId, clientSecret)
        }

        return VenueMenuDomainModel(menu.menuId, menu.description, menu.name)
    }

    override suspend fun geNearbyRecommendedVenues(): List<VenueDomainModel> {
        val location = getUserLocation()

        val response = withContext(Dispatchers.IO) {
            foursquareApi.exploreVenues(clientId, clientSecret,location.latitude.toString() + "," + location.longitude.toString())
        }

        val newData = venueDataMapper.map(response)

        val entities = newData.map {
            VenueEntity(it.id, it.name,"", location.latitude, location.longitude)
        }

        venuesDao.replaceAllWith(entities)

        return newData
    }

    override suspend fun getNearbyRecommendedVenueWithMenu(): VenueWithMenuDomainModel {
        val firstRecommendedVenue = geNearbyRecommendedVenues().firstOrNull()

        if (null != firstRecommendedVenue) {
            val menuForRecommendedVenue = getVenueMenu(firstRecommendedVenue.id)

            return VenueWithMenuDomainModel(menuForRecommendedVenue, firstRecommendedVenue)

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
    override fun getRecommendedVenueWithMenuWithFLow(): Flow<VenueWithMenuDomainModel> {
        return getUserLocationWithFlow()
                .map { location -> foursquareApi.exploreVenues(clientId, clientSecret, location.latitude.toString() + "," + location.longitude.toString()) }
                .flowOn(Dispatchers.IO)
                .map {response -> venueDataMapper.map(response) }
                .flowOn(Dispatchers.Default)
                .map {
                    val loadedVenue = it.firstOrNull()
                    val menu = getVenueMenu(loadedVenue!!.id)
                    VenueWithMenuDomainModel(menu!!, loadedVenue)
                }.flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    override fun getRecommendedVenuesWithFlow(): Flow<List<VenueDomainModel>> {
        return flow {
            val location = getUserLocation()
            val response = foursquareApi.exploreVenues(clientId, clientSecret, location.latitude.toString() + "," + location.longitude.toString())
            val newData = venueDataMapper.map(response)

            val entities = newData.map {
                VenueEntity(it.id, it.name,"", location.latitude, location.longitude)
            }

            venuesDao.replaceAllWith(entities)

            emit(newData)
        }.flowOn(Dispatchers.IO)
    }
}
