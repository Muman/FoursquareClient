package com.mumanit.foursquareclient.domain.interactor

import com.mumanit.foursquareclient.domain.model.VenueWithMenuDomainModel
import kotlinx.coroutines.flow.Flow

interface GetFirstRecommendedVenueWithMenu {
    suspend fun execute(): Flow<VenueWithMenuDomainModel?>
}
