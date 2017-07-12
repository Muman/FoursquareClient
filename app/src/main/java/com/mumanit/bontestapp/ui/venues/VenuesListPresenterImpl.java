package com.mumanit.bontestapp.ui.venues;

import com.mumanit.bontestapp.domain.model.VenueData;
import com.mumanit.bontestapp.domain.GetVenuesInteractor;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by pmuciek on 7/8/17.
 */

public class VenuesListPresenterImpl implements VenuesListPresenter {

    private VenuesContract.VenuesListView view;
    private final GetVenuesInteractor venuesInteractor;

    public VenuesListPresenterImpl(GetVenuesInteractor venuesInteractor) {
        this.venuesInteractor = venuesInteractor;
    }

    @Override
    public void attach(VenuesContract.VenuesListView venuesListView) {
        view = venuesListView;
    }

    @Override
    public void detach() {
        view = null;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void loadVenues() {

        if (isViewAttached()){
            view.showLoading();
        }

        venuesInteractor.getVenues()
                .subscribe(new Action1<List<VenueData>>() {
                    @Override
                    public void call(List<VenueData> venueDatas) {
                        if (isViewAttached()){
                            view.hideLoading();
                            view.showVenuesList(venueDatas);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (isViewAttached()) {
                            view.hideLoading();
                            view.showError();
                        }
                    }
                });
    }
}
