package jp.ceed.kart.settings.ui.practice.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.ceed.kart.settings.ui.common.RowControlListener

class PracticeControlItemViewModel(
    val sessionId: Int,
    private val rowControlListener: RowControlListener,
): ViewModel() {

    var isEditable: Boolean = false

    fun onClickEdit(){
        rowControlListener.onClickControl(RowControlListener.RowControlCommand.EDIT, sessionId)
    }

    fun onClickSave(){
        rowControlListener.onClickControl(RowControlListener.RowControlCommand.SAVE, sessionId)
    }

    fun onClickDelete(){
        rowControlListener.onClickControl(RowControlListener.RowControlCommand.DELETE, sessionId)
    }

    class Factory(private val sessionId: Int, val rowControlListener: RowControlListener): ViewModelProvider.KeyedFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(key:String, modelClass: Class<T>): T {
            return PracticeControlItemViewModel(sessionId, rowControlListener) as T
        }

    }
}