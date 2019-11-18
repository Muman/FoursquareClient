package com.mumanit.foursquareclient.domain.interactor

import com.mumanit.foursquareclient.domain.repository.VenuesRepository
import com.mumanit.foursquareclient.domain.model.VenueDomainModel
import kotlinx.coroutines.flow.Flow

class GetRecommendedVenuesImpl(private val venuesRepository: VenuesRepository) : GetRecommendedVenues {
    override fun executeWithFlow(): Flow<List<VenueDomainModel>> = venuesRepository.getRecommendedVenuesWithFlow()
    override suspend fun execute(): List<VenueDomainModel> = venuesRepository.geNearbyRecommendedVenues()
}
