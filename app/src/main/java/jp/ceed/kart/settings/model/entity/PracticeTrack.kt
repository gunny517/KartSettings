package jp.ceed.kart.settings.model.entity

import androidx.room.ColumnInfo

data class PracticeTrack(
    val id: Int,
    @ColumnInfo(name = "name") val trackName: String,
    val startDate: String,
    var description: String? = null
)