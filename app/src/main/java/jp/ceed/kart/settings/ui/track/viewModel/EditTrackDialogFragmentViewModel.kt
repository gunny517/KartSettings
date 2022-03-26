package jp.ceed.kart.settings.ui.track.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import kotlinx.coroutines.launch

class EditTrackDialogFragmentViewModel(context: Context, private val trackId: Int): ViewModel() {

    private val trackRepository = TrackRepository(context)

    var trackName: MutableLiveData<String> = MutableLiveData()

    var track: Track = Track(0)

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
        }
    }

    class Factory(private val context: Context, private val trackId: Int): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditTrackDialogFragmentViewModel(context, trackId) as T
        }

    }
}