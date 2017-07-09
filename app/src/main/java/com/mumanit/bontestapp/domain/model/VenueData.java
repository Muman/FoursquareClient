package com.mumanit.bontestapp.domain.model;

/**
 * Created by pmuciek on 7/8/17.
 */

public class VenueData {

    int checkinsCount;
    String photoUrl;
    String name;

    public VenueData(int checkinsCount, String name, String photoUrl) {
        this.checkinsCount = checkinsCount;
        this.name = name;
        this.photoUrl = photoUrl;
    }
}
