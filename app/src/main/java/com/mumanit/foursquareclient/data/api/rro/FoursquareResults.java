/**
 * Filename: FoursquareResults.java
 * Author: Matthew Huie
 *
 * FoursquareResults describes a results object from the Foursquare API.
 */

package com.mumanit.foursquareclient.data.api.rro;

import com.google.gson.annotations.Expose;

public class FoursquareResults {

    @Expose
    public FoursquareVenue venue;

}
