package jp.ceed.kart.settings.ui.practice.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.ceed.kart.settings.ui.common.RowControlCommand

class PracticeControlItemViewModel(): ViewModel() {

    constructor(
        sessionId: Int,
        onClickControl: (RowControlCommand, Int) -> Unit
    ): this() {
        this.sessionId = sessionId
        this.onClickControl = onClickControl
    }

    var sessionId: Int = 0

    var onClickControl: (RowControlCommand, Int) -> Unit = { _, _ -> }

    var isEditable: Boolean = false

    fun onClickEdit(){
        onClickControl(RowControlCommand.EDIT, sessionId)
    }

    fun onClickSave(){
        onClickControl(RowControlCommand.SAVE, sessionId)
    }

    fun onClickDelete(){
        onClickControl(RowControlCommand.DELETE, sessionId)
    }

    class Factory(
        private val sessionId: Int,
        private val onClickControl: (RowControlCommand, Int) -> Unit
    )
        : ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeControlItemViewModel(sessionId, onClickControl) as T
        }

    }
}