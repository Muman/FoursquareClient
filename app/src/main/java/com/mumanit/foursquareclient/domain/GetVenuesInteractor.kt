package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.domain.model.VenueData
import kotlinx.coroutines.flow.Flow

interface GetVenuesInteractor {
    suspend fun getRecommendedVenues(): List<VenueData>
    fun getRecommendedVenuesWithFlow(): Flow<List<VenueData>>
}
