package com.mumanit.foursquareclient.domain.model;

public class VenueWithMenuDomainModel {
    public VenueMenuDomainModel menu;
    public VenueDomainModel data;

    public VenueWithMenuDomainModel(VenueMenuDomainModel menu, VenueDomainModel data) {
        this.menu = menu;
        this.data = data;
    }
}
