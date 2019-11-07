/**
 * Filename: FoursquareJSON.java
 * Author: Matthew Huie
 *
 * FoursquareJSON describes a JSON response from the Foursquare API.
 */

package com.mumanit.foursquareclient.data.api.rro;

import com.google.gson.annotations.Expose;

public class FoursquareJSON {

    @Expose
    public FoursquareResponse response;

}
