package jp.ceed.kart.settings.ui.track.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.EventState
import jp.ceed.kart.settings.ui.util.UiUtil
import kotlinx.coroutines.launch

class TrackListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val trackRepository = TrackRepository(application.applicationContext)

    var trackList: MutableLiveData<List<Track>> = MutableLiveData()

    var editTrackLayoutVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    val trackName: MutableLiveData<String> = MutableLiveData()

    val editTrackLayoutEvent: MutableLiveData<Event<EventState>> = MutableLiveData()


    init {
        loadTrackList()
    }

    private fun loadTrackList(){
        viewModelScope.launch {
            trackList.value = trackRepository.getTrackList()
        }
    }

    fun onClickFab(){
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
        trackName.value?.let {
            viewModelScope.launch {
                trackRepository.insertTrack(Track(0, it))
                loadTrackList()
            }
        }
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