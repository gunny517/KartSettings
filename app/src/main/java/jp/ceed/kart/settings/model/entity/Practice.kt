package jp.ceed.kart.settings.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Practice(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val trackId: Int,
    val startDate: String,
)