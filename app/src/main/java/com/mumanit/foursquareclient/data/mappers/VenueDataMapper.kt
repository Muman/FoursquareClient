package com.mumanit.foursquareclient.data.mappers

import com.mumanit.foursquareclient.data.api.rro.FoursquareJSON
import com.mumanit.foursquareclient.data.api.rro.FoursquareVenue
import com.mumanit.foursquareclient.domain.model.VenueDomainModel

interface VenueDataMapper {
    fun map(foursquareVenue: FoursquareVenue): VenueDomainModel
    fun map(foursquareVenueList: List<FoursquareVenue>): List<VenueDomainModel>
    fun map(foursquareJSON: FoursquareJSON): List<VenueDomainModel>
}
