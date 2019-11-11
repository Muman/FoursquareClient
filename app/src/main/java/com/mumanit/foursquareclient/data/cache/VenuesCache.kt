package com.mumanit.foursquareclient.data.cache

import com.mumanit.foursquareclient.domain.model.VenueData

import rx.Observable

interface VenuesCache {
    fun save(venueDataList: List<VenueData>)
    //Observable<Boolean> save(List<VenueData> venueDataList);
    fun loadVenues(): Observable<List<VenueData>>
    suspend fun loadVenuesSuspend() : List<VenueData>
}
