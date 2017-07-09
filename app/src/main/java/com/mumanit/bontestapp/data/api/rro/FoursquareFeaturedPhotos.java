package com.mumanit.bontestapp.data.api.rro;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by pmuciek on 7/8/17.
 */

public class FoursquareFeaturedPhotos {

    @Expose
    public List<FoursquareVenuePhoto> items;
}
