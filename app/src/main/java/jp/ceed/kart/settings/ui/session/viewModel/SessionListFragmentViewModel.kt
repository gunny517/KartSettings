package jp.ceed.kart.settings.ui.session.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.SessionRepository
import jp.ceed.kart.settings.model.dto.SessionListItem
import jp.ceed.kart.settings.model.entity.Session
import kotlinx.coroutines.launch

class SessionListFragmentViewModel(context: Context, val practiceId: Int) : ViewModel() {

    class SessionListFragmentViewModelFactory(val context: Context, val practiceId: Int): ViewModelProvider.Factory{

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SessionListFragmentViewModel(context, practiceId) as T
        }
    }

    private val sessionRepository = SessionRepository(context)

    var sessionList: MutableLiveData<List<SessionListItem>> = MutableLiveData()

    init {
        loadSessions()
    }

    fun onClickFab(){

    }

    fun loadSessions(){
        val list: ArrayList<SessionListItem> = ArrayList()
        list.add(sessionRepository.getSessionHeader())
        viewModelScope.launch {
            list.addAll(sessionRepository.getSessionContentByPracticeId(practiceId))
            sessionList.value = list
        }
    }

    fun isVisibleSettingList(): Int{
        return if(sessionList.value?.isEmpty() == false) View.VISIBLE else View.INVISIBLE
    }

}