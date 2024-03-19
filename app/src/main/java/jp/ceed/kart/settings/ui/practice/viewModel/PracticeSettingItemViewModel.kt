package jp.ceed.kart.settings.ui.practice.viewModel

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.ceed.kart.settings.R
import kotlinx.parcelize.Parcelize


@Parcelize
class PracticeSettingItemViewModel(): ViewModel(), Parcelable {

    constructor(
        sessionId: Int,
        fieldName: String,
        value: String?,
        inputType: Int,
        isChanged: Boolean,
        isEditable: Boolean = false
    ): this() {
        this.sessionId = sessionId
        this.fieldName = fieldName
        this.value = value
        this.inputType = inputType
        this.isChanged = isChanged
        this.isEditable = isEditable
    }

    var sessionId: Int = 0
    var fieldName: String = ""
    var value: String? = null
    var inputType: Int = 0
    var isChanged: Boolean = false
    var isEditable: Boolean = false

    class Factory(
        private val sessionId: Int,
        private val fieldName: String,
        var value: String?,
        private val inputType: Int,
        private val isChanged: Boolean
        ): ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeSettingItemViewModel(sessionId, fieldName, value, inputType, isChanged) as T
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