package jp.ceed.kart.settings.ui.practice.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.SessionRepository
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.model.entity.Session
import jp.ceed.kart.settings.ui.common.RowControlListener
import kotlinx.coroutines.launch

class PracticeDetailFragmentViewModel(context: Context, private val practiceId: Int): ViewModel() {

    class Factory(val context: Context, private val practiceId: Int): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeDetailFragmentViewModel(context, practiceId) as T
        }
    }

    private val sessionRepository = SessionRepository(context)

    var practiceRowList: MutableLiveData<List<PracticeDetailAdapterItem>> = MutableLiveData()


    init {
        loadPracticeRowList()
    }

    private fun loadPracticeRowList(){
        viewModelScope.launch {
            practiceRowList.value = sessionRepository.getPracticeRowList(practiceId)
        }
    }

    fun onClickFab() {
        viewModelScope.launch {
            val newEntity = sessionRepository.getNewEntityForInsert(practiceId)
            sessionRepository.insert(newEntity)
            practiceRowList.value = sessionRepository.getPracticeRowList(practiceId)
        }
    }

    fun onClickControl(controlCommand: RowControlListener.RowControlCommand, sessionId: Int){
        if(RowControlListener.RowControlCommand.SAVE == controlCommand){
            practiceRowList.value?.let {
                viewModelScope.launch {
                    sessionRepository.saveSession(it, sessionId, practiceId)
                }
            }
        }
        changeEditState(sessionId)
    }

    private fun changeEditState(sessionId: Int){
        practiceRowList.value?.let {
            val copyList = it.toList()
            for(entry in copyList){
                when(entry){
                    is PracticeDetailAdapterItem.PracticeRowItem -> {
                        for(session in entry.values){
                            if(sessionId == session.sessionId){
                                session.isEditable = session.isEditable.not()
                            }
                        }
                    }else -> {}
                }
            }
            practiceRowList.value = copyList
        }
    }
}