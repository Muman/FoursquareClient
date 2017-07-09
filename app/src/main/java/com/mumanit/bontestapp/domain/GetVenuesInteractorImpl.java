package com.mumanit.bontestapp.domain;

import com.mumanit.bontestapp.domain.model.VenueData;
import com.mumanit.bontestapp.data.VenuesDataManager;

import java.util.List;

import rx.Observable;

/**
 * Created by pmuciek on 7/8/17.
 */

public class GetVenuesInteractorImpl implements GetVenuesInteractor {

    private final VenuesDataManager venuesDataManager;

    public GetVenuesInteractorImpl(VenuesDataManager venuesDataManager) {
        this.venuesDataManager = venuesDataManager;
    }

    @Override
    public Observable<List<VenueData>> getVenues() {
        return venuesDataManager.getVenues();
    }
}
