package com.mumanit.bontestapp.ui.venues;

import com.mumanit.bontestapp.domain.model.VenueData;

import java.util.List;

/**
 * Created by pmuciek on 7/8/17.
 */

public class VenuesContract {

    public interface VenuesListView {
        void showVenuesList(List<VenueData> venueDataList);
        void showLoading();
        void hideLoading();
        void showError();
    }

    public interface VenuesActionsListener {
        void loadVenues();
    }
}
