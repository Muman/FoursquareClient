package com.mumanit.bontestapp.data.mappers;

import com.mumanit.bontestapp.data.api.rro.FoursquareJSON;
import com.mumanit.bontestapp.data.api.rro.FoursquareVenue;
import com.mumanit.bontestapp.domain.model.VenueData;

import java.util.List;

/**
 * Created by pmuciek on 7/8/17.
 */

public interface VenueDataMapper {
    VenueData map(FoursquareVenue foursquareVenue);
    List<VenueData> map(List<FoursquareVenue> foursquareVenueList);
    List<VenueData> map(FoursquareJSON foursquareJSON);
}
