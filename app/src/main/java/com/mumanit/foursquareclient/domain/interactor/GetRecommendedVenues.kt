package com.mumanit.foursquareclient.domain.interactor

import com.mumanit.foursquareclient.domain.model.VenueDomainModel
import kotlinx.coroutines.flow.Flow

interface GetRecommendedVenues {
    suspend fun execute(): List<VenueDomainModel>
    fun executeWithFlow(): Flow<List<VenueDomainModel>>
}
