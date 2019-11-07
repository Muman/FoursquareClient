package com.mumanit.foursquareclient.data.api.rro;

import com.google.gson.annotations.Expose;

/**
 * Created by pmuciek on 7/8/17.
 */

public class FoursquareVenuePhoto {

    @Expose
    public String prefix;

    @Expose
    public String suffix;

    @Expose
    int height;

    @Expose
    int width;
}
