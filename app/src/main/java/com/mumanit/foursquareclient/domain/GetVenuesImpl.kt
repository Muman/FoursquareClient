package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.data.VenuesRepository
import com.mumanit.foursquareclient.domain.model.VenueData
import kotlinx.coroutines.flow.Flow

class GetVenuesImpl(private val venuesDataManager: VenuesRepository) : GetVenues {
    override fun getRecommendedVenuesWithFlow(): Flow<List<VenueData>> = venuesDataManager.getRecommendedVenuesWithFlow()
    override suspend fun getRecommendedVenues(): List<VenueData> = venuesDataManager.geNearbyRecommendedVenues()
}
