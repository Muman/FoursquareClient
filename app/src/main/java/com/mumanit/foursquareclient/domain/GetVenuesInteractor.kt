package com.mumanit.foursquareclient.domain

import com.mumanit.foursquareclient.domain.model.VenueData

interface GetVenuesInteractor {
    suspend fun getVenues(): List<VenueData>
}
