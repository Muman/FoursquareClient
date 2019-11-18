package com.mumanit.foursquareclient.domain.model;

public class VenueMenuDomainModel {
    Long id;
    String description;
    String name;

    public VenueMenuDomainModel(Long id, String description, String name) {
        this.id = id;
        this.description = description;
        this.name = name;
    }
}
