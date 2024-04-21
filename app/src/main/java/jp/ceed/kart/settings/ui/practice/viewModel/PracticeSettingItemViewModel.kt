package jp.ceed.kart.settings.ui.practice.viewModel

import android.content.Context
import android.os.Parcelable
import android.text.InputType
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.ceed.kart.settings.R
import kotlinx.parcelize.Parcelize


@Parcelize
class PracticeSettingItemViewModel(): ViewModel(), Parcelable {

    companion object {
        const val START_TIME_FORMAT = "%02d:%02d"
    }

    constructor(
        sessionId: Int,
        fieldName: String,
        value: String?,
        inputType: Int,
        isEditable: Boolean = false,
        isChanged: Boolean,
        onTimeFieldFocus: (String, Int) -> Unit,
    ): this() {
        this.sessionId = sessionId
        this.fieldName = fieldName
        this.value = value
        this.inputType = inputType
        this.isChanged = isChanged
        this.isEditable = isEditable
        this.onTimeFieldFocus = onTimeFieldFocus
    }

    var sessionId: Int = 0
    var fieldName: String = ""
    var value: String? = null
    var inputType: Int = 0
    var isChanged: Boolean = false
    var isEditable: Boolean = false
    var onTimeFieldFocus: (String, Int) -> Unit = { _, _ -> {} }

    class Factory(
        private val sessionId: Int,
        private val fieldName: String,
        var value: String?,
        private val inputType: Int,
        private val isEditable: Boolean,
        private val isChanged: Boolean,
        private val onTimeFieldFocus: (String, Int) -> Unit,
    ): ViewModelProvider.NewInstanceFactory() {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeSettingItemViewModel(sessionId, fieldName, value, inputType, isEditable, isChanged, onTimeFieldFocus) as T
        }
    }

    fun setValueAStartTime(hour: Int, minutes: Int) {
        value = START_TIME_FORMAT.format(hour, minutes)
    }

    fun onFocus(tag: String) {
        if (InputType.TYPE_DATETIME_VARIATION_TIME == inputType) {
            onTimeFieldFocus(tag, sessionId)
        }
    }

    fun textColor(context: Context): Int{
        return when {
            isEditable -> {
                ContextCompat.getColor(context, R.color.text_edit_text_enabled)
            }
            isChanged -> {
                ContextCompat.getColor(context, R.color.text_setting_value_changed)
            }
            else -> {
                ContextCompat.getColor(context, R.color.text_default)
            }
        }
    }
}