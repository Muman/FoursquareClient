package com.mumanit.foursquareclient.data.mappers

import com.mumanit.foursquareclient.data.api.rro.FoursquareJSON
import com.mumanit.foursquareclient.data.api.rro.FoursquareVenue
import com.mumanit.foursquareclient.domain.model.VenueDomainModel
import java.util.*

class VenueDataMapperImpl : VenueDataMapper {

    override fun map(foursquareVenue: FoursquareVenue): VenueDomainModel {
        val photoUrl = getPhotoUrl(foursquareVenue)
        val name = foursquareVenue.name
        val checkinsCount = foursquareVenue.stats.checkinsCount
        val id = foursquareVenue.id

        return VenueDomainModel(checkinsCount, name, photoUrl, id)
    }

    override fun map(foursquareVenueList: List<FoursquareVenue>): List<VenueDomainModel> {
        val venueDataList = ArrayList<VenueDomainModel>()

        for (foursquareVenue in foursquareVenueList) {
            venueDataList.add(map(foursquareVenue))
        }

        return venueDataList
    }

    override fun map(foursquareJSON: FoursquareJSON): List<VenueDomainModel> {
        val result = ArrayList<VenueDomainModel>()
        val foursquareGroups = foursquareJSON.response.groups

        for (foursquareGroup in foursquareGroups) {
            for (foursquareResults in foursquareGroup.items) {
                result.add(map(foursquareResults.venue))
            }
        }

        return result
    }

    private fun getPhotoUrl(venue: FoursquareVenue): String? {
        var venuePhotoUrl: String? = null
        val photos = venue.featuredPhotos

        if (photos?.items != null) {
            if (photos.items.isNotEmpty()) {
                val photo = photos.items[0]
                venuePhotoUrl = photo.prefix + "320x320" + photo.suffix
            }
        }

        return venuePhotoUrl
    }
}
