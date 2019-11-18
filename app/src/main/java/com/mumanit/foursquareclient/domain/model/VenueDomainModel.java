package com.mumanit.foursquareclient.domain.model;

/**
 * Created by pmuciek on 7/8/17.
 */

public class VenueDomainModel {

    public int checkinsCount;
    public String photoUrl;
    public String name;
    public String id;

    public VenueDomainModel(int checkinsCount, String name, String photoUrl, String id) {
        this.checkinsCount = checkinsCount;
        this.name = name;
        this.photoUrl = photoUrl;
        this.id = id;
    }
}
