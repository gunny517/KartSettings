package jp.ceed.kart.settings.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.model.SettingLabel
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import kotlinx.parcelize.Parcelize
import java.util.HashMap

@Entity
@Suppress("all")
@Parcelize
data class Session(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val practiceId: Int,

    @SettingLabel(label = R.string.setting_label_start_time, index = 0)
    val startTime: String = "09:00",

    @SettingLabel(label = R.string.setting_label_track_condition, index = 1)
    val trackCondition: String = "",

    @SettingLabel(label = R.string.setting_label_temperature, index = 2)
    val temperature: String = "20.0",

    @SettingLabel(label = R.string.setting_label_humidity, index = 3)
    val humidity: String = "50",

    @SettingLabel(label = R.string.setting_label_pressure, index = 4)
    val pressure: String = "980",

    @SettingLabel(label = R.string.setting_label_track_temperature, index = 5)
    val trackTemperature: String = "20.0",

    @SettingLabel(label = R.string.setting_label_engine, index = 6)
    val engine: String = "#00",

    @SettingLabel(label = R.string.setting_label_btdc, index = 7)
    val btdc: String = "3.0",

    @SettingLabel(label = R.string.setting_label_carburetor, index = 8)
    val carburetor: String = "#1",

    @SettingLabel(label = R.string.setting_label_low_needle, index = 9)
    val lowNeedle: String = "2:00",

    @SettingLabel(label = R.string.setting_label_high_needle, index = 10)
    val highNeedle: String = "15",

    @SettingLabel(label = R.string.setting_label_final_ratio, index = 11)
    val finalRatio: String = "12x84",

    @SettingLabel(label = R.string.setting_label_ex_joint, index = 12)
    val exJoint: String = "50",

    @SettingLabel(label = R.string.setting_label_tire_pressure_f, index = 13)
    val tirePressureF: String = "0.6",

    @SettingLabel(label = R.string.setting_label_tire_pressure_r, index = 14)
    val tirePressureR: String = "0.6",

    @SettingLabel(label = R.string.setting_label_tread_f, index = 15)
    val treadF: String = "15",

    @SettingLabel(label = R.string.setting_label_tread_r, index = 16)
    val treadR: String = "1400",

    @SettingLabel(label = R.string.setting_label_stabilizer, index = 17)
    val stabilizerF: String = "F",

    @SettingLabel(label = R.string.setting_label_axle_shaft, index = 18)
    val axleShaft: String = "N",

    @SettingLabel(label = R.string.setting_label_axle_bearing, index = 19)
    val axleBearing: String = "2",

    @SettingLabel(label = R.string.setting_label_hub_stopper, index = 20)
    val hubStopper: String = "",

    @SettingLabel(label = R.string.setting_label_max_rev, index = 21)
    val maxRev: String = "13000",

    @SettingLabel(label = R.string.setting_label_max_speed, index = 22)
    val maxSpeed: String = "100.0",

    @SettingLabel(label = R.string.setting_label_best_time, index = 23)
    val bestTime: String = "42.0"
): Parcelable {

    companion object {

        fun fromPracticeRowItemList(rowItemList: List<PracticeDetailAdapterItem>, sessionId: Int, practiceId: Int): Session{
            val map = HashMap<String,String>()
            for(row in rowItemList){
                when(row){
                    is PracticeDetailAdapterItem.PracticeRowItem -> {
                        for(settingItem in row.values){
                            map[settingItem.fieldName] = settingItem.value
                        }
                    } else -> {}
                }
            }
            val session = Session(sessionId, practiceId)
            val fields = Session::class.java.declaredFields
            for(field in fields){
                val value = map[field.name]
                value?.let {
                    field.isAccessible = true
                    field.set(session, value)
                }
            }
            return session
        }

        fun createCopyAsZeroId(session: Session, practiceId: Int): Session {
            val copy = session.copy()
            val cls = copy.javaClass
            val idField = cls.getDeclaredField("id")
            idField.isAccessible = true
            idField.set(copy, 0)
            val practiceIdField =  cls.getDeclaredField("practiceId")
            practiceIdField.isAccessible = true
            practiceIdField.set(copy, practiceId)
            return copy
        }
    }
}