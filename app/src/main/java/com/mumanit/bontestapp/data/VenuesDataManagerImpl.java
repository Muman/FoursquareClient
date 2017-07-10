package com.mumanit.bontestapp.data;

import com.mumanit.bontestapp.data.api.FoursquareApi;
import com.mumanit.bontestapp.data.api.rro.FoursquareJSON;
import com.mumanit.bontestapp.data.cache.VenuesCache;
import com.mumanit.bontestapp.data.mappers.VenueDataMapper;
import com.mumanit.bontestapp.domain.model.VenueData;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pmuciek on 7/8/17.
 */

public class VenuesDataManagerImpl implements VenuesDataManager {

    private final FoursquareApi foursquareApi;
    private final VenueDataMapper venueDataMapper;
    private final VenuesCache venuesCache;
    private final String clientId;
    private final String clientSecret;

    public VenuesDataManagerImpl(FoursquareApi foursquareApi, VenueDataMapper venueDataMapper, VenuesCache venuesCache, String clientId, String clientSecret) {
        this.foursquareApi = foursquareApi;
        this.venueDataMapper = venueDataMapper;
        this.venuesCache = venuesCache;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public Observable<List<VenueData>> getVenues() {

        Observable<List<VenueData>> cached = venuesCache.loadVenues();
        cached.subscribeOn(Schedulers.computation());

        Observable<List<VenueData>> api = foursquareApi.exploreVenues(clientId, clientSecret).map(new Func1<FoursquareJSON, List<VenueData>>() {
            @Override
            public List<VenueData> call(FoursquareJSON foursquareJSON) {
                return venueDataMapper.map(foursquareJSON);
            }

        }).doOnNext(new Action1<List<VenueData>>() {
            @Override
            public void call(List<VenueData> venueDatas) {
                venuesCache.save(venueDatas);
            }
        }).subscribeOn(Schedulers.io());

        return Observable.concat(cached, api).observeOn(AndroidSchedulers.mainThread(), true);
    }
}
