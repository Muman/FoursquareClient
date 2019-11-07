package com.mumanit.foursquareclient.data.cache

import com.mumanit.foursquareclient.domain.model.VenueData
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import rx.Observable

class VenuesSharedPrefCache : VenuesCache {

    private val CACHE_TAG = "com.mumanit.bontestapp.data.cache.VenuesCache"

    /*    @Override
    public Observable<Boolean> save(List<VenueData> venueDataList) {
        Log.d("CACHE", "save venues to cache");
        return Hawk.putObservable(CACHE_TAG, venueDataList);
    }*/

    override fun save(venueDataList: List<VenueData>) {
        Hawk.put(CACHE_TAG, venueDataList)
    }

    override fun loadVenues(): Observable<List<VenueData>> {
        return Hawk.getObservable(CACHE_TAG)
    }

    override suspend fun loadVenuesSuspend(): List<VenueData> {
        return withContext(Dispatchers.Default) {
            Hawk.get<List<VenueData>>(CACHE_TAG, emptyList())
        }
    }
}
