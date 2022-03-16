package jp.ceed.kart.settings.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val practiceId: Int,
    val startTime: String,
    val trackCondition: String,
    val humidity: Int,
    val temperature: Float,
    val pressure: Int,
    val trackTemperature: Float,
    val engine: String,
    val btdc: Float,
    val carburetor: String,
    val lowNeedle: String,
    val highNeedle: String,
    val finalRatio: String,
    val exJoint: String,
    val tirePressureF: Float,
    val tirePressureR: Float,
    val treadF: String,
    val treadR: String,
    val stabilizerF: String,
    val axleShaft: String,
    val axleBearing: String,
    val hubStopper: String,
    val maxRev: Int,
    val maxSpeed: Float,
    val bestTime: Float
)