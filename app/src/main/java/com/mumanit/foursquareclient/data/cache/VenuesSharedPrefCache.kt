package com.mumanit.foursquareclient.data.cache

import com.mumanit.foursquareclient.domain.model.VenueData
import com.orhanobut.hawk.Hawk

import rx.Observable

/**
 * Created by pmuciek on 7/9/17.
 */

class VenuesSharedPrefCache : VenuesCache {

    private val CACHE_TAG = "com.mumanit.bontestapp.data.cache.VenuesCache"

    /*    @Override
    public Observable<Boolean> save(List<VenueData> venueDataList) {
        Log.d("CACHE", "save venues to cache");
        return Hawk.putObservable(CACHE_TAG, venueDataList);
    }*/

    fun save(venueDataList: List<VenueData>) {
        Hawk.put(CACHE_TAG, venueDataList)
    }

    override fun loadVenues(): Observable<List<VenueData>> {
        return Hawk.getObservable(CACHE_TAG)
    }
}
