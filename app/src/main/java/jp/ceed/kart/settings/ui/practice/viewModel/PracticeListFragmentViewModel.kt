package jp.ceed.kart.settings.ui.practice.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.PracticeRepository
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.EventState
import kotlinx.coroutines.launch

class PracticeListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val practiceRepository = PracticeRepository(application.applicationContext)

    val practiceList: MutableLiveData<List<PracticeTrack>> = MutableLiveData()

    val fabClickEvent: MutableLiveData<Event<EventState>> = MutableLiveData()

    init {
        viewModelScope.launch {
            practiceList.value = practiceRepository.findAll()
        }
    }

    fun onClickFab(){
        fabClickEvent.value = Event(EventState.CLICKED)
    }
}