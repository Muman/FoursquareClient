package com.mumanit.foursquareclient.data.api;

import com.mumanit.foursquareclient.data.api.rro.FoursquareJSON;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pmuciek on 7/8/17.
 */

public interface FoursquareApi {

    @GET("venues/explore?v=20161101&venuePhotos=1")
    Observable<FoursquareJSON> exploreVenues(@Query("client_id") String clientID, @Query("client_secret") String clientSecret, @Query("ll") String ll);
}
