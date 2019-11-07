package com.mumanit.foursquareclient.domain;

import com.mumanit.foursquareclient.domain.model.VenueData;
import java.util.List;
import rx.Observable;

/**
 * Created by pmuciek on 7/8/17.
 */

public interface GetVenuesInteractor {
    Observable<List<VenueData>> getVenues();
}
