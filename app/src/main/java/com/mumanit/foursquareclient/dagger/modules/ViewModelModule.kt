package com.mumanit.foursquareclient.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mumanit.foursquareclient.dagger.ViewModelKey
import com.mumanit.foursquareclient.ui.venues.VenuesViewModel
import com.mumanit.foursquareclient.ui.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(VenuesViewModel::class)
    internal abstract fun bindTracksViewModel(venuesViewModel: VenuesViewModel): ViewModel
}