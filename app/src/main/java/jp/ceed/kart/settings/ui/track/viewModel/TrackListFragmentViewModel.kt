package jp.ceed.kart.settings.ui.track.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.EventState
import kotlinx.coroutines.launch

class TrackListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val trackRepository = TrackRepository(application.applicationContext)

    var trackList: MutableLiveData<List<Track>> = MutableLiveData()

    var fabClickEvent: MutableLiveData<Event<EventState>> = MutableLiveData()

    init {
        loadTrackList()
    }

    fun loadTrackList(){
        viewModelScope.launch {
            trackList.value = trackRepository.getTrackList()
        }
    }

    fun onClickFab(){
        fabClickEvent.value = Event(EventState.CLICKED)
    }

    fun saveTrack(trackName: String){
        viewModelScope.launch {
            trackRepository.insertTrack(Track(0, trackName))
            loadTrackList()
        }
    }

}