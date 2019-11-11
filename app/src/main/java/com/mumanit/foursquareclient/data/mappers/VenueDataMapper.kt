package com.mumanit.foursquareclient.data.mappers

import com.mumanit.foursquareclient.data.api.rro.FoursquareJSON
import com.mumanit.foursquareclient.data.api.rro.FoursquareVenue
import com.mumanit.foursquareclient.domain.model.VenueData

interface VenueDataMapper {
    fun map(foursquareVenue: FoursquareVenue): VenueData
    fun map(foursquareVenueList: List<FoursquareVenue>): List<VenueData>
    fun map(foursquareJSON: FoursquareJSON): List<VenueData>
}
