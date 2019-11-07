package com.mumanit.foursquareclient.ui.venues;

/**
 * Created by pmuciek on 7/8/17.
 */

public interface VenuesListPresenter extends VenuesContract.VenuesActionsListener {
    void attach(VenuesContract.VenuesListView venuesListView);
    void detach();
    boolean isViewAttached();
}
