package com.mumanit.bontestapp.data.cache;

import android.util.Log;
import com.mumanit.bontestapp.domain.model.VenueData;
import com.orhanobut.hawk.Hawk;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * Created by pmuciek on 7/9/17.
 */

public class VenuesSharedPrefCache implements VenuesCache {

    private final String CACHE_TAG = "com.mumanit.bontestapp.data.cache.VenuesCache";

    @Override
    public void save(List<VenueData> venueDataList) {
        Hawk.put(CACHE_TAG, venueDataList);
        Log.d("CACHE", "save venues to cache");
    }

    @Override
    public Observable<List<VenueData>> loadVenues() {
        return Observable.fromCallable(new Callable<List<VenueData>>() {
            @Override
            public List<VenueData> call() throws Exception {
                Log.d("CACHE", "get venues from cache");
                return Hawk.get(CACHE_TAG);
            }
        });
    }
}
