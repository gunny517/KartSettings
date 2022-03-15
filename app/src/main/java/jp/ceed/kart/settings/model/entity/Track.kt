package jp.ceed.kart.settings.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Track(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)