package jp.ceed.kart.settings.ui.track.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTrackDialogFragmentViewModel @Inject constructor(
    var trackRepository: TrackRepository,
): ViewModel() {

    var trackName: MutableLiveData<String> = MutableLiveData()

    var track: Track = Track(0)

    fun loadTrack(trackId: Int){
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
}