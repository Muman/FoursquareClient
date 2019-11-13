package com.mumanit.foursquareclient.data.api.rro;


import com.google.gson.annotations.SerializedName;

public class FoursquareVenueMenuResponse {

    @SerializedName("description")
    public String description;

    @SerializedName("menuId")
    public Long menuId;

    @SerializedName("name")
    public String name;

}
