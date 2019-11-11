package com.mumanit.foursquareclient.data.mappers

import com.mumanit.foursquareclient.data.api.rro.FoursquareJSON
import com.mumanit.foursquareclient.data.api.rro.FoursquareVenue
import com.mumanit.foursquareclient.domain.model.VenueData
import java.util.*

class VenueDataMapperImpl : VenueDataMapper {

    override fun map(foursquareVenue: FoursquareVenue): VenueData {
        val photoUrl = getPhotoUrl(foursquareVenue)
        val name = foursquareVenue.name
        val checkinsCount = foursquareVenue.stats.checkinsCount

        return VenueData(checkinsCount, name, photoUrl)
    }

    override fun map(foursquareVenueList: List<FoursquareVenue>): List<VenueData> {
        val venueDataList = ArrayList<VenueData>()

        for (foursquareVenue in foursquareVenueList) {
            venueDataList.add(map(foursquareVenue))
        }

        return venueDataList
    }

    override fun map(foursquareJSON: FoursquareJSON): List<VenueData> {
        val result = ArrayList<VenueData>()
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
