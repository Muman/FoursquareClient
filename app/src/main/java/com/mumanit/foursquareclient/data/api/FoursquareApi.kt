package com.mumanit.foursquareclient.data.api

import com.mumanit.foursquareclient.data.api.rro.FoursquareJSON
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by pmuciek on 7/8/17.
 */

interface FoursquareApi {

    @GET("venues/explore?v=20161101&venuePhotos=1")
    fun exploreVenues(@Query("client_id") clientID: String, @Query("client_secret") clientSecret: String, @Query("ll") ll: String): Observable<FoursquareJSON>

    @GET("venues/explore?v=20161101&venuePhotos=1")
    suspend fun exploreVenuesSuspend(@Query("client_id") clientID: String, @Query("client_secret") clientSecret: String, @Query("ll") ll: String): FoursquareJSON

}
