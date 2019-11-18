package com.mumanit.foursquareclient.domain.interactor

import com.mumanit.foursquareclient.domain.repository.VenuesRepository
import com.mumanit.foursquareclient.domain.model.VenueWithMenuDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirstRecommendedVenueWithMenuImpl @Inject constructor(private val venuesRepository: VenuesRepository) : GetFirstRecommendedVenueWithMenu {
    override suspend fun execute(): Flow<VenueWithMenuDomainModel?> {
        return venuesRepository.getRecommendedVenueWithMenuWithFLow()
    }
}