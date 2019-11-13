package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.domain.model.VenueWithMenu

interface GetFirstRecommendedVenueWithMenuInteractor {
    suspend fun getFirstVenueWithMenu(): VenueWithMenu?
}
