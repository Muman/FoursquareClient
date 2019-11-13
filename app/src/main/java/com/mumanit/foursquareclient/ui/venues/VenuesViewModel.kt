package com.mumanit.foursquareclient.ui.venues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumanit.foursquareclient.domain.GetFirstRecommendedVenueWithMenuInteractor
import com.mumanit.foursquareclient.domain.GetVenuesInteractor
import com.mumanit.foursquareclient.domain.model.VenueData
import com.mumanit.foursquareclient.domain.model.VenueWithMenu
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenuesViewModel @Inject constructor(
        private val getVenuesInteractor: GetVenuesInteractor,
        private val getFirstRecommendedVenueWithMenu: GetFirstRecommendedVenueWithMenuInteractor
) : ViewModel() {

    private val _venues = MutableLiveData<List<VenueData>>()
    private val _mostRecommendedVenueWithMenu = MutableLiveData<VenueWithMenu>()

    val venues : LiveData<List<VenueData>>
    get() = _venues

    val mostRecommendedVenueWithMenu: LiveData<VenueWithMenu>
    get() = _mostRecommendedVenueWithMenu

    private fun loadVenues() {
        viewModelScope.launch {
            getVenuesInteractor.getRecommendedVenuesWithFlow().collect {
                _venues.postValue(it)
            }
        }

        /* suspended function

        viewModelScope.launch {
            val venues = getVenuesInteractor.getRecommendedVenues()
            _venues.postValue(venues)
        }

        */
    }

    private fun loadMostRecommendedVenueWithMenu() {
        viewModelScope.launch {
            val mostRecommendedVenueWithMenu = getFirstRecommendedVenueWithMenu.getFirstVenueWithMenu()
            _mostRecommendedVenueWithMenu.postValue(mostRecommendedVenueWithMenu)
        }
    }

    fun loadData() {
        loadVenues()
        loadMostRecommendedVenueWithMenu()
    }

}