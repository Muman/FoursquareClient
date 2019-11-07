package com.mumanit.foursquareclient.data.mappers;

import com.mumanit.foursquareclient.data.api.rro.FoursquareJSON;
import com.mumanit.foursquareclient.data.api.rro.FoursquareVenue;
import com.mumanit.foursquareclient.domain.model.VenueData;

import java.util.List;

/**
 * Created by pmuciek on 7/8/17.
 */

public interface VenueDataMapper {
    VenueData map(FoursquareVenue foursquareVenue);
    List<VenueData> map(List<FoursquareVenue> foursquareVenueList);
    List<VenueData> map(FoursquareJSON foursquareJSON);
}
