/**
 * Filename: FoursquareVenue.java
 * Author: Matthew Huie
 *
 * FoursquareVenue describes a venue object from the Foursquare API.
 */

package com.mumanit.bontestapp.data.api.rro;

import com.google.gson.annotations.Expose;

public class FoursquareVenue {

    @Expose
    public String id;

    @Expose
    public String name;

    @Expose
    public FoursquareVenueStats stats;

    @Expose
    public FoursquareFeaturedPhotos featuredPhotos;
}