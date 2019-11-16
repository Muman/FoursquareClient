package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.domain.model.VenueWithMenu
import kotlinx.coroutines.flow.Flow

interface GetFirstRecommendedVenueWithMenu {
    suspend fun getFirstVenueWithMenu(): Flow<VenueWithMenu?>
}
