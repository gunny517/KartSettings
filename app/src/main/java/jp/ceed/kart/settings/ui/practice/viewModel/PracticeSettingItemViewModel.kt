package jp.ceed.kart.settings.ui.practice.viewModel

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.parcelize.Parcelize

@Parcelize
data class PracticeSettingItemViewModel(
    val sessionId: Int,
    val fieldName: String,
    var value: String,
    var isEditable: Boolean = false
): ViewModel(), Parcelable {

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

    class Factory(
        val sessionId: Int,
        val fieldName: String,
        var value: String,
        var isEditable: Boolean = false
        ): ViewModelProvider.KeyedFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>): T {
            return PracticeSettingItemViewModel(sessionId, fieldName, value) as T
        }

    }
}