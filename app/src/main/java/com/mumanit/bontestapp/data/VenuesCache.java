package com.mumanit.bontestapp.data;

import com.mumanit.bontestapp.domain.model.VenueData;

import java.util.List;

/**
 * Created by pmuciek on 7/9/17.
 */

public interface VenuesCache {
    void save(List<VenueData> venueDataList);
    List<VenueData> load();
}
