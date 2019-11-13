package com.mumanit.foursquareclient.domain.model;

public class VenueWithMenu {
    public VenueMenu menu;
    public VenueData data;

    public VenueWithMenu(VenueMenu menu, VenueData data) {
        this.menu = menu;
        this.data = data;
    }
}
