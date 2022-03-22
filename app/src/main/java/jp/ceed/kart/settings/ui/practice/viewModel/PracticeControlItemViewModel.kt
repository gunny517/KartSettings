package jp.ceed.kart.settings.ui.practice.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.ceed.kart.settings.ui.common.RowControlListener

class PracticeControlItemViewModel(
    val sessionId: Int,
    val rowControlListener: RowControlListener?,
    var isEditable: Boolean = false): ViewModel() {

    fun onClickEdit(){
        rowControlListener?.onClickControl(RowControlListener.RowControlCommand.EDIT, sessionId)
    }

    fun onClickSave(){
        rowControlListener?.onClickControl(RowControlListener.RowControlCommand.SAVE, sessionId)
    }

    fun onClickDelete(){
        rowControlListener?.onClickControl(RowControlListener.RowControlCommand.DELETE, sessionId)
    }

    class Factory(private val sessionId: Int, val rowControlListener: RowControlListener?): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeControlItemViewModel(sessionId, rowControlListener) as T
        }

    }
}