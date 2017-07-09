package com.mumanit.bontestapp.ui.venues;

/**
 * Created by pmuciek on 7/8/17.
 */

public class VenuesContract {

    public interface VenuesListView {
        void showVenuesList();
        void showLoading();
        void hideLoading();
        void showError();
    }

    public interface VenuesActionsListener {
        void loadVenues();
    }
}
