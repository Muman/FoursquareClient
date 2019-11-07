package com.mumanit.foursquareclient.data

import com.mumanit.foursquareclient.data.api.FoursquareApi
import com.mumanit.foursquareclient.data.cache.VenuesCache
import com.mumanit.foursquareclient.data.mappers.VenueDataMapper
import com.mumanit.foursquareclient.domain.model.VenueData
import pl.charmas.android.reactivelocation.ReactiveLocationProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class VenuesDataManagerImpl(private val foursquareApi: FoursquareApi, private val venueDataMapper: VenueDataMapper, private val venuesCache: VenuesCache, private val locationProvider: ReactiveLocationProvider, private val clientId: String, private val clientSecret: String) : VenuesDataManager {

    override fun getVenues(): Observable<List<VenueData>> {

        val cached = venuesCache.loadVenues()
        cached.subscribeOn(Schedulers.computation())

        val api = locationProvider
                .lastKnownLocation
                .observeOn(Schedulers.io())
                .concatMap { location -> foursquareApi.exploreVenues(clientId, clientSecret, location.latitude.toString() + "," + location.longitude.toString()) }.map { foursquareJSON -> venueDataMapper.map(foursquareJSON) }.doOnNext { venueDatas -> venuesCache.save(venueDatas) }.subscribeOn(Schedulers.io())

        return Observable.concat(cached, api).observeOn(AndroidSchedulers.mainThread(), true)
    }

    override suspend fun getVenuesSuspend(): List<VenueData> {
        val cached = venuesCache.loadVenuesSuspend();
        return cached
    }
}
