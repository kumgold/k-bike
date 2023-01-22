package com.goldcompany.koreabike.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user_address", primaryKeys = ["id"])
class AddressEntity(
    @ColumnInfo(defaultValue = "0") var selected: Boolean,
    @ColumnInfo val date: Long = System.currentTimeMillis(),
    @ColumnInfo val id: String,
    @ColumnInfo val latitude: Double,
    @ColumnInfo val longitude: Double,
    @ColumnInfo val address: String,
    @ColumnInfo val category: String,
    @ColumnInfo val roadAddress: String,
    @ColumnInfo val phone: String,
    @ColumnInfo val placeName: String,
    @ColumnInfo val placeUrl: String
)