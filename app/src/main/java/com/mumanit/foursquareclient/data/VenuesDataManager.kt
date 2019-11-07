package com.mumanit.foursquareclient.data

import com.mumanit.foursquareclient.domain.model.VenueData
import rx.Observable

/**
 * Created by pmuciek on 7/8/17.
 */

interface VenuesDataManager {
    fun getVenues(): Observable<List<VenueData>>

    suspend fun getVenuesSuspend() : List<VenueData>
}
