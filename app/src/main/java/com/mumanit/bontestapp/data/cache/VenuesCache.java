package com.mumanit.bontestapp.data.cache;

import com.mumanit.bontestapp.domain.model.VenueData;
import java.util.List;

import rx.Observable;

/**
 * Created by pmuciek on 7/9/17.
 */

public interface VenuesCache {
    void save(List<VenueData> venueDataList);
    //Observable<Boolean> save(List<VenueData> venueDataList);
    Observable<List<VenueData>> loadVenues();
}
