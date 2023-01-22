package com.goldcompany.koreabike.data.model.place

import com.google.gson.annotations.SerializedName

data class PlaceMarkerMetaData(
    @SerializedName("is_end") val isEnd: Boolean,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("total_count") val totalCount: Int
)