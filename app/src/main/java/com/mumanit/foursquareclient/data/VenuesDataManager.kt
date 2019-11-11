package com.mumanit.foursquareclient.data

import com.mumanit.foursquareclient.domain.model.VenueData

interface VenuesDataManager {
    suspend fun getVenues() : List<VenueData>
}
