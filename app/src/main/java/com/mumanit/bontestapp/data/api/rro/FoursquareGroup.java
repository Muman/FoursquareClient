/**
 * Filename: FoursquareGroup.java
 * Author: Matthew Huie
 *
 * FoursquareGroup describes a group object from the Foursquare API.
 */

package com.mumanit.bontestapp.data.api.rro;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class FoursquareGroup {

    @Expose
    public List<FoursquareResults> items = new ArrayList<FoursquareResults>();

}
