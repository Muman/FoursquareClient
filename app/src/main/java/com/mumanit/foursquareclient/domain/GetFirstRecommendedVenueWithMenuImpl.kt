package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.data.VenuesRepository
import com.mumanit.foursquareclient.domain.model.VenueWithMenu
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirstRecommendedVenueWithMenuImpl @Inject constructor(private val venuesDataManager: VenuesRepository) : GetFirstRecommendedVenueWithMenu {
    override suspend fun getFirstVenueWithMenu(): Flow<VenueWithMenu?> {
        return venuesDataManager.getRecommendedVenueWithMenuWithFLow()
    }
}