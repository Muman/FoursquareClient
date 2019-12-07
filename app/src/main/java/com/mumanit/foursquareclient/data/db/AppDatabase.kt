package com.mumanit.foursquareclient.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mumanit.foursquareclient.data.db.dao.VenuesDao
import com.mumanit.foursquareclient.data.db.entity.VenueEntity

@Database(entities = [VenueEntity::class], exportSchema = true, version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun venuesDao(): VenuesDao

    companion object {
        const val NAME = "VenuesDb"
    }
}