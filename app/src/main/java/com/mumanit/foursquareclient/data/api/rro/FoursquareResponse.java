/**
 * Filename: FoursquareResponse.java
 * Author: Matthew Huie
 *
 * FoursquareResponse describes a response object from the Foursquare API.
 */

package com.mumanit.foursquareclient.data.api.rro;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class FoursquareResponse {
    @Expose
    public List<FoursquareGroup> groups = new ArrayList<>();
}