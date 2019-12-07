package com.mumanit.foursquareclient.presentation.venues

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mumanit.foursquareclient.domain.interactor.GetFirstRecommendedVenueWithMenu
import com.mumanit.foursquareclient.domain.interactor.GetRecommendedVenues
import com.mumanit.foursquareclient.domain.model.VenueDomainModel
import com.mumanit.foursquareclient.domain.model.VenueWithMenuDomainModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class VenuesViewModel @Inject constructor(
        private val getVenuesInteractor: GetRecommendedVenues,
        private val getFirstRecommendedVenueWithMenu: GetFirstRecommendedVenueWithMenu
) : ViewModel() {

    private val _venues = MutableLiveData<List<VenueDomainModel>>()
    private val _mostRecommendedVenueWithMenu = MutableLiveData<VenueWithMenuDomainModel>()

    val venues : LiveData<List<VenueDomainModel>>
    get() = _venues

    val mostRecommendedVenueWithMenu: LiveData<VenueWithMenuDomainModel>
    get() = _mostRecommendedVenueWithMenu

    private fun loadVenues() {
        viewModelScope.launch {
            getVenuesInteractor.executeWithFlow()
                    .catch { error ->
                        Log.e("error", error.message)
                    }
                    .collect {
                _venues.postValue(it)
            }
        }
    }

    private fun loadMostRecommendedVenueWithMenu() {
        viewModelScope.launch {
            getFirstRecommendedVenueWithMenu.execute()
                    .catch { error ->
                        Log.e("error", error.message)
                    }
                    .collect { venueWithMenu -> _mostRecommendedVenueWithMenu.postValue(venueWithMenu) }
        }
    }

    fun loadData() {
        loadVenues()
        loadMostRecommendedVenueWithMenu()
    }

}