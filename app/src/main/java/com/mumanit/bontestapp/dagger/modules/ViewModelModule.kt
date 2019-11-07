package com.mumanit.bontestapp.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mumanit.bontestapp.dagger.ViewModelKey
import com.mumanit.bontestapp.ui.venues.VenuesViewModel
import com.mumanit.bontestapp.ui.viewmodel.ViewModelFactory
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