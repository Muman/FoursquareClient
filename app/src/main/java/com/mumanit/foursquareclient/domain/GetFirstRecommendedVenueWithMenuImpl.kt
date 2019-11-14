package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.data.VenuesDataManager
import com.mumanit.foursquareclient.domain.model.VenueWithMenu
import javax.inject.Inject

class GetFirstRecommendedVenueWithMenuImpl @Inject constructor(private val venuesDataManager: VenuesDataManager) : GetFirstRecommendedVenueWithMenuInteractor {
    override suspend fun getFirstVenueWithMenu(): VenueWithMenu? {
        return venuesDataManager.getRecommendedVenueWithMenu()
    }
}