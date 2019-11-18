package com.mumanit.foursquareclient.data.cache

import com.mumanit.foursquareclient.domain.model.VenueDomainModel
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import rx.Observable

class VenuesSharedPrefCache : VenuesCache {

    private val CACHE_TAG = "com.mumanit.bontestapp.data.cache.VenuesCache"

    /*    @Override
    public Observable<Boolean> save(List<VenueDomainModel> venueDataList) {
        Log.d("CACHE", "save venues to cache");
        return Hawk.putObservable(CACHE_TAG, venueDataList);
    }*/

    override fun save(venueDataList: List<VenueDomainModel>) {
        Hawk.put(CACHE_TAG, venueDataList)
    }

    override fun loadVenues(): Observable<List<VenueDomainModel>> {
        return Hawk.getObservable(CACHE_TAG)
    }

    override suspend fun loadVenuesSuspend(): List<VenueDomainModel> {
        return withContext(Dispatchers.IO) {
            Hawk.get<List<VenueDomainModel>>(CACHE_TAG, emptyList())
        }
    }
}
