package jp.ceed.kart.settings.ui.practice.viewModel

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.ceed.kart.settings.R
import kotlinx.parcelize.Parcelize


@Parcelize
data class PracticeSettingItemViewModel(
    val sessionId: Int,
    val fieldName: String,
    var value: String?,
    var inputType: Int,
    var isChanged: Boolean,
    var isEditable: Boolean = false
): ViewModel(), Parcelable {

    class Factory(
        private val sessionId: Int,
        private val fieldName: String,
        var value: String?,
        private val inputType: Int,
        private val isChanged: Boolean
        ): ViewModelProvider.KeyedFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>): T {
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