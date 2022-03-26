package jp.ceed.kart.settings.ui.track.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import kotlinx.coroutines.launch

class TrackListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val trackRepository = TrackRepository(application.applicationContext)

    var trackList: MutableLiveData<List<Track>> = MutableLiveData()

    val editEvent: MutableLiveData<Event<EventState>> = MutableLiveData()

    enum class EventState { EDIT, CREATE }

    fun loadTrackList(){
        viewModelScope.launch {
            trackList.value = trackRepository.getTrackList()
        }
    }

    fun onClickFab(){
        editEvent.value = Event(EventState.CREATE)
    }
}