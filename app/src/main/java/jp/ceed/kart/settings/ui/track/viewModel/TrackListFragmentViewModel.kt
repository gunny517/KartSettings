package jp.ceed.kart.settings.ui.track.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackListFragmentViewModel @Inject constructor(
    private var trackRepository: TrackRepository
) : ViewModel() {

    var trackList: MutableLiveData<List<TrackListItemViewModel>> = MutableLiveData()

    val editEvent: MutableLiveData<Event<Int>> = MutableLiveData()

    val deleteEvent: MutableLiveData<Event<Int>> = MutableLiveData()

    lateinit var viewModelStoreOwner: ViewModelStoreOwner

    fun loadTrackList(){
        viewModelScope.launch {
            trackList.value = createViewModels(trackRepository.getTrackList())
        }
    }

    private fun createViewModels(trackList: List<Track>): List<TrackListItemViewModel> {
        val list: ArrayList<TrackListItemViewModel> = ArrayList()
        for(entry in trackList){
            val factory = TrackListItemViewModel.Factory(entry.id, entry.name){
                id: Int, view: View -> onClickItemButton(id, view.id)
            }
            val key = entry.id.toString() + System.currentTimeMillis().toString()
            list.add(ViewModelProvider(viewModelStoreOwner, factory)
                .get(key, TrackListItemViewModel::class.java))
        }
        return list
    }

    private fun onClickItemButton(trackId: Int, viewId: Int){
        when(viewId){
            R.id.editButton -> {
                editEvent.value = Event(trackId)
            }
            R.id.deleteButton -> {
                deleteEvent.value = Event(trackId)
            }
        }
    }

    fun onClickFab(){
        editEvent.value = Event(0)
    }

    fun deleteById(trackId: Int){
        viewModelScope.launch {
            trackRepository.deleteById(trackId)
            loadTrackList()
        }
    }
}