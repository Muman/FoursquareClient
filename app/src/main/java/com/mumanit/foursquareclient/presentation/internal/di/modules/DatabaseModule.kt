package com.mumanit.foursquareclient.presentation.internal.di.modules

import android.content.Context
import androidx.room.Room
import com.mumanit.foursquareclient.data.db.AppDatabase
import com.mumanit.foursquareclient.data.db.dao.VenuesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java, AppDatabase.NAME).build()
    }

    @Singleton
    @Provides
    fun provideVenuesDao(appDatabase: AppDatabase): VenuesDao {
        return appDatabase.venuesDao()
    }

}