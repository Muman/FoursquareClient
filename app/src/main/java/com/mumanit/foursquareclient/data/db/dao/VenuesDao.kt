package com.mumanit.foursquareclient.data.db.dao

import androidx.room.*
import com.mumanit.foursquareclient.data.db.entity.VenueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VenuesDao {

    @Query("SELECT * FROM ${VenueEntity.TABLE_NAME}")
    suspend fun getAll(): List<VenueEntity>

    @Query("SELECT * FROM ${VenueEntity.TABLE_NAME}")
    fun getAllFlow(): Flow<List<VenueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(venues: List<VenueEntity>)

    @Transaction
    suspend fun replaceAllWith(venues: List<VenueEntity>) {
        deleteAll()
        insertAll(venues)
    }

    @Delete
    suspend fun delete(venue: VenueEntity)

    @Query("DELETE FROM ${VenueEntity.TABLE_NAME}")
    suspend fun deleteAll()
}