package jp.ceed.kart.settings.ui.session.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import jp.ceed.kart.settings.domain.repository.SessionRepository

class SessionHeaderFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionRepository = SessionRepository(application.applicationContext)

    var labelList: MutableLiveData<List<String>> = MutableLiveData()

    init {
        loadSessionLabel()
    }

    private fun loadSessionLabel(){

    }
}