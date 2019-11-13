package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.data.VenuesDataManager
import com.mumanit.foursquareclient.domain.model.VenueData
import kotlinx.coroutines.flow.Flow

class GetVenuesInteractorImpl(private val venuesDataManager: VenuesDataManager) : GetVenuesInteractor {
    override fun getRecommendedVenuesWithFlow(): Flow<List<VenueData>> = venuesDataManager.getRecommendedVenuesWithFlow()
    override suspend fun getRecommendedVenues(): List<VenueData> = venuesDataManager.getRecommendedVenues()
}
