package com.mumanit.bontestapp.data.api;

import com.mumanit.bontestapp.data.api.rro.FoursquareJSON;
import com.mumanit.bontestapp.domain.model.VenueData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pmuciek on 7/8/17.
 */

public interface FoursquareApi {

    @GET("explore?v=20161101&ll=50.062351,19.937967&venuePhotos=1")
    Observable<FoursquareJSON> exploreVenues(@Query("client_id") String clientID, @Query("client_secret") String clientSecret);

    @GET("search?v=20161101&ll=50.062351,19.937967&venuePhotos=1")
    Observable<List<VenueData>> searchVenues(@Query("client_id") String clientID, @Query("client_secret") String clientSecret);
}
