package com.mumanit.foursquareclient.data;

import com.mumanit.foursquareclient.domain.model.VenueData;
import java.util.List;
import rx.Observable;

/**
 * Created by pmuciek on 7/8/17.
 */

public interface VenuesDataManager {
    Observable<List<VenueData>> getVenues();
}
