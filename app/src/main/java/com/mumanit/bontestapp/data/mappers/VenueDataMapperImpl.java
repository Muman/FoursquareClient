package com.mumanit.bontestapp.data.mappers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mumanit.bontestapp.data.api.rro.FoursquareFeaturedPhotos;
import com.mumanit.bontestapp.data.api.rro.FoursquareGroup;
import com.mumanit.bontestapp.data.api.rro.FoursquareJSON;
import com.mumanit.bontestapp.data.api.rro.FoursquareResults;
import com.mumanit.bontestapp.data.api.rro.FoursquareVenue;
import com.mumanit.bontestapp.data.api.rro.FoursquareVenuePhoto;
import com.mumanit.bontestapp.domain.model.VenueData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pmuciek on 7/8/17.
 */

public class VenueDataMapperImpl implements VenueDataMapper {

    @Override
    public VenueData map(@NonNull FoursquareVenue foursquareVenue) {

        String photoUrl = getPhotoUrl(foursquareVenue);
        String name = foursquareVenue.name;
        int checkinsCount = foursquareVenue.stats.checkinsCount;

        return new VenueData(checkinsCount, name, photoUrl);
    }

    @Override
    public List<VenueData> map(List<FoursquareVenue> foursquareVenueList) {

        List<VenueData> venueDataList = new ArrayList<>();

        for (FoursquareVenue foursquareVenue : foursquareVenueList) {
            venueDataList.add(map(foursquareVenue));
        }

        return venueDataList;
    }

    @Override
    public List<VenueData> map(FoursquareJSON foursquareJSON) {

        List<VenueData> result = new ArrayList<>();
        List<FoursquareGroup> foursquareGroups = foursquareJSON.response.groups;

        for (FoursquareGroup foursquareGroup : foursquareGroups) {
             for (FoursquareResults foursquareResults : foursquareGroup.items) {
                 result.add(map(foursquareResults.venue));
             }
        }

        return result;
    }

    private @Nullable String getPhotoUrl(@NonNull FoursquareVenue venue) {
        String venuePhotoUrl = null;

        FoursquareFeaturedPhotos photos = venue.featuredPhotos;

        if (null != photos && null != photos.items) {

            if (!photos.items.isEmpty()){
                FoursquareVenuePhoto photo = photos.items.get(0);
                venuePhotoUrl = photo.prefix + photo.suffix;
            }
        }

        return venuePhotoUrl;
    }
}
