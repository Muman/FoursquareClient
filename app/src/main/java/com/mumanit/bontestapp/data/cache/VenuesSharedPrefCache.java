package com.mumanit.bontestapp.data.cache;

import android.util.Log;
import com.mumanit.bontestapp.domain.model.VenueData;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import rx.Observable;

/**
 * Created by pmuciek on 7/9/17.
 */

public class VenuesSharedPrefCache implements VenuesCache {

    private final String CACHE_TAG = "com.mumanit.bontestapp.data.cache.VenuesCache";

/*    @Override
    public Observable<Boolean> save(List<VenueData> venueDataList) {
        Log.d("CACHE", "save venues to cache");
        return Hawk.putObservable(CACHE_TAG, venueDataList);
    }*/

    @Override
    public void save(List<VenueData> venueDataList) {
        Hawk.put(CACHE_TAG, venueDataList);
    }

    @Override
    public Observable<List<VenueData>> loadVenues() {
        return Hawk.getObservable(CACHE_TAG);
    }
}
