package com.mumanit.foursquareclient.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mumanit.foursquareclient.data.db.entity.VenueEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class VenueEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = FIELD_NAME)
    val name: String,

    @ColumnInfo(name = FIELD_IMAGE_PATH)
    val imgPath: String?,

    @ColumnInfo(name = FIELD_POSITION_LAT)
    val lat: Double,

    @ColumnInfo(name = FIELD_POSITION_LANG)
    val lang: Double
) {
    companion object {
        const val TABLE_NAME = "venues"
        const val FIELD_NAME = "name"
        const val FIELD_IMAGE_PATH = "image"
        const val FIELD_POSITION_LANG = "lang"
        const val FIELD_POSITION_LAT = "lat"
    }
}
