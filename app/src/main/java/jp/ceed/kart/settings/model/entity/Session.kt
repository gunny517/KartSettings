package jp.ceed.kart.settings.model.entity

import android.os.Parcelable
import android.text.InputType
import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.model.SettingElement
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.ui.util.CommonUtil
import kotlinx.parcelize.Parcelize

@Entity
@Suppress("all")
@Parcelize
data class Session(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val practiceId: Int,

    @SettingElement(label = R.string.setting_label_start_time, index = 0, inputType = TYPE_TEXT)
    val startTime: String = DEFAULT_START_TIME,

    @SettingElement(label = R.string.setting_label_track_condition, index = 1, inputType = TYPE_TEXT)
    val trackCondition: String = "",

    @SettingElement(label = R.string.setting_label_temperature, index = 2, inputType = TYPE_NUMBER)
    val temperature: String = "20.0",

    @SettingElement(label = R.string.setting_label_humidity, index = 3, inputType = TYPE_NUMBER)
    val humidity: String = "50",

    @SettingElement(label = R.string.setting_label_pressure, index = 4, inputType = TYPE_NUMBER)
    val pressure: String = "980",

    @SettingElement(label = R.string.setting_label_track_temperature, index = 5, inputType = TYPE_TEXT)
    val trackTemperature: String = "20.0",

    @SettingElement(label = R.string.setting_label_engine, index = 6, inputType = TYPE_TEXT)
    val engine: String = "#00",

    @SettingElement(label = R.string.setting_label_btdc, index = 7, inputType = TYPE_NUMBER)
    val btdc: String = "3.0",

    @SettingElement(label = R.string.setting_label_carburetor, index = 8, inputType = TYPE_TEXT)
    val carburetor: String = "#1",

    @SettingElement(label = R.string.setting_label_low_needle, index = 9, inputType = TYPE_NUMBER)
    val lowNeedle: String = "2:00",

    @SettingElement(label = R.string.setting_label_high_needle, index = 10, inputType = TYPE_NUMBER)
    val highNeedle: String = "15",

    @SettingElement(label = R.string.setting_label_final_ratio, index = 11, inputType = TYPE_TEXT)
    val finalRatio: String = "12x84",

    @SettingElement(label = R.string.setting_label_ex_joint, index = 12, inputType = TYPE_NUMBER)
    val exJoint: String = "50",

    @SettingElement(label = R.string.setting_label_tire_pressure_f, index = 13, inputType = TYPE_NUMBER)
    val tirePressureF: String = "0.6",

    @SettingElement(label = R.string.setting_label_tire_pressure_r, index = 14, inputType = TYPE_NUMBER)
    val tirePressureR: String = "0.6",

    @SettingElement(label = R.string.setting_label_tread_f, index = 15, inputType = TYPE_NUMBER)
    val treadF: String = "15",

    @SettingElement(label = R.string.setting_label_tread_r, index = 16, inputType = TYPE_NUMBER)
    val treadR: String = "1400",

    @SettingElement(label = R.string.setting_label_stabilizer, index = 17, inputType = TYPE_TEXT)
    val stabilizerF: String = "F",

    @SettingElement(label = R.string.setting_label_axle_shaft, index = 18, inputType = TYPE_TEXT)
    val axleShaft: String = "N",

    @SettingElement(label = R.string.setting_label_axle_bearing, index = 19, inputType = TYPE_TEXT)
    val axleBearing: String = "2",

    @SettingElement(label = R.string.setting_label_hub_stopper, index = 20, inputType = TYPE_TEXT)
    val hubStopper: String = "",

    @SettingElement(label = R.string.setting_label_max_rev, index = 21, inputType = TYPE_NUMBER)
    val maxRev: String = "13000",

    @SettingElement(label = R.string.setting_label_max_speed, index = 22, inputType = TYPE_NUMBER)
    val maxSpeed: String = "100.0",

    @SettingElement(label = R.string.setting_label_best_time, index = 23, inputType = TYPE_NUMBER)
    val bestTime: String = "42.0"
): Parcelable {

    companion object {

        const val DEFAULT_START_TIME = "09:00"

        const val TYPE_NUMBER: Int = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        const val TYPE_TEXT: Int = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL

        fun fromPracticeRowItemList(rowItemList: List<PracticeDetailAdapterItem>, sessionId: Int, practiceId: Int): Session{
            val map = HashMap<String,String>()
            for(row in rowItemList){
                when(row){
                    is PracticeDetailAdapterItem.PracticeRowItem -> {
                        for(settingItem in row.values){
                            if(sessionId == settingItem.sessionId){
                                map[settingItem.fieldName] = settingItem.value
                            }
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

        fun createCopyAsZeroId(session: Session, practiceId: Int, isSamePractice: Boolean): Session {
            val copy = session.copy()
            val cls = copy.javaClass
            val idField = cls.getDeclaredField("id")
            idField.isAccessible = true
            idField.set(copy, 0)
            val practiceIdField =  cls.getDeclaredField("practiceId")
            practiceIdField.isAccessible = true
            practiceIdField.set(copy, practiceId)
            val startTime = if(isSamePractice){
                CommonUtil().getOneHourPastTime(copy.startTime)
            }else{
                DEFAULT_START_TIME
            }
            val startTimeField = cls.getDeclaredField("startTime")
            startTimeField.isAccessible = true
            startTimeField.set(copy, startTime)
            return copy
        }
    }
}