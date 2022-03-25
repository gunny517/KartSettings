package jp.ceed.kart.settings.ui.track.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.EventState
import kotlinx.coroutines.launch

class TrackListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val trackRepository = TrackRepository(application.applicationContext)

    var trackList: MutableLiveData<List<Track>> = MutableLiveData()

    var editTrackLayoutVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    val editTrackLayoutEvent: MutableLiveData<Event<EventState>> = MutableLiveData()

    val track: MutableLiveData<Track> = MutableLiveData()


    init {
        loadTrackList()
    }

    private fun loadTrackList(){
        viewModelScope.launch {
            trackList.value = trackRepository.getTrackList()
        }
    }

    fun onClickFab(){
        track.value = Track(0)
        toggleEditLayoutVisibility()
    }

    fun onClickOk(){
        saveTrack()
        toggleEditLayoutVisibility()
    }

    fun onClickCancel(){
        toggleEditLayoutVisibility()
    }

    fun onClickDialogBackground(){
        toggleEditLayoutVisibility()
    }

    private fun saveTrack(){
        track.value?.let {
            viewModelScope.launch {
                trackRepository.save(it)
                loadTrackList()
            }
        }
    }

    fun onClickEdit(_track: Track){
        track.value = _track
        toggleEditLayoutVisibility()
    }

    private fun toggleEditLayoutVisibility(){
        editTrackLayoutVisibility.value = if(editTrackLayoutVisibility.value == View.VISIBLE){
            View.GONE
        }else{
            View.VISIBLE
        }
        editTrackLayoutEvent.value = Event(EventState.CLICKED)
    }
}