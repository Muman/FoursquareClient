package com.mumanit.foursquareclient.presentation.internal.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mumanit.foursquareclient.presentation.internal.di.ViewModelKey
import com.mumanit.foursquareclient.presentation.venues.VenuesViewModel
import com.mumanit.foursquareclient.presentation.viewmodel.ViewModelFactory
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
    internal abstract fun bindVenuesViewModel(venuesViewModel: VenuesViewModel): ViewModel
}