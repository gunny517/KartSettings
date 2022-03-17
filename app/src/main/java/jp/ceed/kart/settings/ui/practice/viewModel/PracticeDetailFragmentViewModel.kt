package jp.ceed.kart.settings.ui.practice.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.SessionRepository
import jp.ceed.kart.settings.model.dto.PracticeRowItem
import kotlinx.coroutines.launch

class PracticeDetailFragmentViewModel(context: Context, private val practiceId: Int): ViewModel() {

    class Factory(val context: Context, private val practiceId: Int): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeDetailFragmentViewModel(context, practiceId) as T
        }
    }

    private val sessionRepository = SessionRepository(context)

    var rowList: MutableLiveData<List<PracticeRowItem>> = MutableLiveData()

    init {
        loadPracticeRowList()
    }

    private fun loadPracticeRowList(){
        viewModelScope.launch {
            rowList.value = sessionRepository.getPracticeRowList(practiceId)
        }
    }
}