package com.mumanit.foursquareclient.ui.venues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumanit.foursquareclient.domain.GetVenuesInteractor
import com.mumanit.foursquareclient.domain.model.VenueData
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenuesViewModel @Inject constructor(
        private val venuesInteractor: GetVenuesInteractor
) : ViewModel() {

    private val _venues = MutableLiveData<List<VenueData>>()

    val venues : LiveData<List<VenueData>>
    get() = _venues

    fun loadVenues() {
        viewModelScope.launch {
            val venues = venuesInteractor.getVenues()
            _venues.postValue(venues)
        }
    }
}