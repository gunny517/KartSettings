package jp.ceed.kart.settings.ui.practice.viewModel

import android.os.Parcelable
import android.text.InputType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.parcelize.Parcelize

@Parcelize
data class PracticeSettingItemViewModel(
    val sessionId: Int,
    val fieldName: String,
    var value: String,
    var inputType: Int,
    var isEditable: Boolean = false
): ViewModel(), Parcelable {

    class Factory(
        private val sessionId: Int,
        private val fieldName: String,
        var value: String,
        private val inputType: Int
        ): ViewModelProvider.KeyedFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>): T {
            return PracticeSettingItemViewModel(sessionId, fieldName, value, inputType) as T
        }

    }
}