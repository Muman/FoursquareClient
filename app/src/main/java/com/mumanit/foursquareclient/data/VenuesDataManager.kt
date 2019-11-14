package com.mumanit.foursquareclient.data

import com.mumanit.foursquareclient.domain.model.VenueData
import com.mumanit.foursquareclient.domain.model.VenueMenu
import com.mumanit.foursquareclient.domain.model.VenueWithMenu
import kotlinx.coroutines.flow.Flow

interface VenuesDataManager {
    suspend fun getRecommendedVenues() : List<VenueData>
    suspend fun getVenueMenu(id: String) : VenueMenu?
    suspend fun getRecommendedVenueWithMenu(): VenueWithMenu
    fun getRecommendedVenueWithMenuWithFLow(): Flow<VenueWithMenu>
    fun getRecommendedVenuesWithFlow() : Flow<List<VenueData>>
}
