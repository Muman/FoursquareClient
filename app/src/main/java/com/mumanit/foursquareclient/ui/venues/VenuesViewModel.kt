package com.mumanit.foursquareclient.ui.venues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mumanit.foursquareclient.data.VenuesDataManager
import com.mumanit.foursquareclient.domain.model.VenueData
import javax.inject.Inject

class VenuesViewModel @Inject constructor(
        private val venuesDataManager: VenuesDataManager
) : ViewModel() {

    private val _venues = MutableLiveData<List<VenueData>>()

    val venues : LiveData<List<VenueData>>
    get() = _venues

    fun loadVenues() {

    }
}