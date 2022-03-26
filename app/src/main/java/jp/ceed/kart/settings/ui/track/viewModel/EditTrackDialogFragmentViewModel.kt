package jp.ceed.kart.settings.ui.track.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import kotlinx.coroutines.launch

class EditTrackDialogFragmentViewModel(context: Context, private val track: Track): ViewModel() {

    private val trackRepository = TrackRepository(context)

    val trackName: MutableLiveData<String> = MutableLiveData(track.name)

    fun saveTrack(track: Track){
        track.name = trackName.value
        viewModelScope.launch {
            trackRepository.save(track)
        }
    }

    class Factory(private val context: Context, private val track: Track): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditTrackDialogFragmentViewModel(context, track) as T
        }

    }
}