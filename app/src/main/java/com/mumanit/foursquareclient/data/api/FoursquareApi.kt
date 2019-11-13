package com.mumanit.foursquareclient.data.api

import com.mumanit.foursquareclient.data.api.rro.FoursquareJSON
import com.mumanit.foursquareclient.data.api.rro.FoursquareVenueMenuResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoursquareApi {

    @GET("venues/explore?v=20161101&venuePhotos=1")
    suspend fun exploreVenues(@Query("client_id") clientID: String, @Query("client_secret") clientSecret: String, @Query("ll") ll: String): FoursquareJSON

    @GET("venues/{venueId}/menu?v=20161101&venuePhotos=1")
    suspend fun menuDetails(@Path("venueId") venueId : String, @Query("client_id") clientID: String, @Query("client_secret") clientSecret: String): FoursquareVenueMenuResponse
}
