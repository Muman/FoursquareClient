package com.mumanit.foursquareclient.data.cache

import com.mumanit.foursquareclient.domain.model.VenueDomainModel

import rx.Observable

interface VenuesCache {
    fun save(venueDataList: List<VenueDomainModel>)
    //Observable<Boolean> save(List<VenueDomainModel> venueDataList);
    fun loadVenues(): Observable<List<VenueDomainModel>>
    suspend fun loadVenuesSuspend() : List<VenueDomainModel>
}
