package jp.ceed.kart.settings.model.dto

import android.view.View

data class SettingViewItem(
    val value: String,
    var isEditable: Boolean = false
){
    constructor(_value: Int): this(
        _value.toString()
    )
    constructor(_value: Float): this(
        _value.toString()
    )
    fun editTextVisibility(): Int{
        return if(isEditable) View.VISIBLE else View.INVISIBLE
    }
}