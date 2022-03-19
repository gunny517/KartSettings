package jp.ceed.kart.settings.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SettingItem(
    val sessionId: Int,
    val fieldName: String,
    var value: String,
    var isEditable: Boolean = false
): Parcelable{

    constructor(_practiceId: Int, _fieldName: String, _value: Int): this(
        _practiceId,
        _fieldName,
        _value.toString()
    )

    constructor(_practiceId: Int, _fieldName: String, _value: Float): this(
        _practiceId,
        _fieldName,
        _value.toString()
    )
}