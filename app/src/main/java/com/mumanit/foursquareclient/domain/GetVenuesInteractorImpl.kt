package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.data.VenuesDataManager
import com.mumanit.foursquareclient.domain.model.VenueData

class GetVenuesInteractorImpl(private val venuesDataManager: VenuesDataManager) : GetVenuesInteractor {
    override suspend fun getVenues(): List<VenueData> = venuesDataManager.getVenues()
}
