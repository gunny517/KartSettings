package jp.ceed.kart.settings.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.model.SettingLabel
import kotlinx.parcelize.Parcelize

@Entity
@Suppress("all")
@Parcelize
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val practiceId: Int,
    @SettingLabel(R.string.setting_label_start_time) val startTime: String,
    @SettingLabel(R.string.setting_label_track_condition) val trackCondition: String,
    @SettingLabel(R.string.setting_label_humidity) val humidity: Int,
    @SettingLabel(R.string.setting_label_temperature) val temperature: Float,
    @SettingLabel(R.string.setting_label_pressure) val pressure: Int,
    @SettingLabel(R.string.setting_label_track_temperature) val trackTemperature: Float,
    @SettingLabel(R.string.setting_label_engine) val engine: String,
    @SettingLabel(R.string.setting_label_btdc) val btdc: Float,
    @SettingLabel(R.string.setting_label_carburetor) val carburetor: String,
    @SettingLabel(R.string.setting_label_low_needle) val lowNeedle: String,
    @SettingLabel(R.string.setting_label_high_needle) val highNeedle: String,
    @SettingLabel(R.string.setting_label_final_ratio) val finalRatio: String,
    @SettingLabel(R.string.setting_label_ex_joint) val exJoint: String,
    @SettingLabel(R.string.setting_label_tire_pressure_f) val tirePressureF: Float,
    @SettingLabel(R.string.setting_label_tire_pressure_r) val tirePressureR: Float,
    @SettingLabel(R.string.setting_label_tread_f) val treadF: String,
    @SettingLabel(R.string.setting_label_tread_r) val treadR: String,
    @SettingLabel(R.string.setting_label_stabilizer) val stabilizerF: String,
    @SettingLabel(R.string.setting_label_axle_shaft) val axleShaft: String,
    @SettingLabel(R.string.setting_label_axle_bearing) val axleBearing: String,
    @SettingLabel(R.string.setting_label_hub_stopper) val hubStopper: String,
    @SettingLabel(R.string.setting_label_max_rev) val maxRev: Int,
    @SettingLabel(R.string.setting_label_max_speed) val maxSpeed: Float,
    @SettingLabel(R.string.setting_label_best_time) val bestTime: Float
): Parcelable