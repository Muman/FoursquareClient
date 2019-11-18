package com.mumanit.foursquareclient.domain.repository

import com.mumanit.foursquareclient.domain.model.VenueDomainModel
import com.mumanit.foursquareclient.domain.model.VenueMenuDomainModel
import com.mumanit.foursquareclient.domain.model.VenueWithMenuDomainModel
import kotlinx.coroutines.flow.Flow

interface VenuesRepository {
    suspend fun geNearbyRecommendedVenues() : List<VenueDomainModel>
    suspend fun getVenueMenu(id: String) : VenueMenuDomainModel?
    suspend fun getNearbyRecommendedVenueWithMenu(): VenueWithMenuDomainModel
    fun getRecommendedVenueWithMenuWithFLow(): Flow<VenueWithMenuDomainModel>
    fun getRecommendedVenuesWithFlow() : Flow<List<VenueDomainModel>>
}
