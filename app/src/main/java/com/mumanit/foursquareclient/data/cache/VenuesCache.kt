package com.mumanit.foursquareclient.data.cache

import com.mumanit.foursquareclient.domain.model.VenueData

import rx.Observable

/**
 * Created by pmuciek on 7/9/17.
 */

interface VenuesCache {
    fun save(venueDataList: List<VenueData>)
    //Observable<Boolean> save(List<VenueData> venueDataList);
    fun loadVenues(): Observable<List<VenueData>>
    suspend fun loadVenuesSuspend() : List<VenueData>
}
