package jp.ceed.kart.settings.ui.track.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import kotlinx.coroutines.launch

class EditTrackDialogFragmentViewModel(application: Application, private val trackId: Int): ViewModel() {

    class Factory(private val application: Application, private val trackId: Int): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditTrackDialogFragmentViewModel(application, trackId) as T
        }

    }

    private val trackRepository = TrackRepository(application.applicationContext)

    var trackName: MutableLiveData<String> = MutableLiveData()

    var track: Track = Track(0)

    var savedEvent: MutableLiveData<Event<Int>> = MutableLiveData()

    init{
        if(trackId != 0){
            loadTrack()
        }
    }

    private fun loadTrack(){
        viewModelScope.launch {
            track = trackRepository.findById(trackId)
            trackName.value = track.name ?: ""
        }
    }

    fun saveTrack(){
        track.name = trackName.value
        viewModelScope.launch {
            trackRepository.save(track)
            savedEvent.value = Event(0)
        }
    }
}