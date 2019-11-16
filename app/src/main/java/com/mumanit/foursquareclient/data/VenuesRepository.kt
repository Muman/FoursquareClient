package com.mumanit.foursquareclient.data

import com.mumanit.foursquareclient.domain.model.VenueData
import com.mumanit.foursquareclient.domain.model.VenueMenu
import com.mumanit.foursquareclient.domain.model.VenueWithMenu
import kotlinx.coroutines.flow.Flow

interface VenuesRepository {
    suspend fun geNearbyRecommendedVenues() : List<VenueData>
    suspend fun getVenueMenu(id: String) : VenueMenu?
    suspend fun getNearbyRecommendedVenueWithMenu(): VenueWithMenu
    fun getRecommendedVenueWithMenuWithFLow(): Flow<VenueWithMenu>
    fun getRecommendedVenuesWithFlow() : Flow<List<VenueData>>
}
