package jp.ceed.kart.settings.ui.track.viewModel

import android.app.Application
import androidx.lifecycle.*
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import kotlinx.coroutines.launch

class TrackListFragmentViewModel(
    application: Application,
    private val viewModelStoreOwner: ViewModelStoreOwner,
    val onClick: (Int, EventState) -> Unit) : ViewModel() {

    class Factory(val application: Application,
                  private val viewModelStoreOwner: ViewModelStoreOwner,
                  val onClick: (Int, EventState) -> Unit): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackListFragmentViewModel(application, viewModelStoreOwner, onClick) as T
        }
    }

    private val trackRepository = TrackRepository(application.applicationContext)

    var trackList: MutableLiveData<List<TrackListItemViewModel>> = MutableLiveData()

    val editEvent: MutableLiveData<Event<EventState>> = MutableLiveData()

    enum class EventState { CREATE, DELETE }

    fun loadTrackList(){
        viewModelScope.launch {
            trackList.value = createViewModels(trackRepository.getTrackList())
        }
    }

    private fun createViewModels(trackList: List<Track>): List<TrackListItemViewModel> {
        val list: ArrayList<TrackListItemViewModel> = ArrayList()
        for(entry in trackList){
            val factory = TrackListItemViewModel.Factory(entry.id, entry.name){
                id: Int, type: EventState -> onClick(id, type)
            }
            list.add(ViewModelProvider(viewModelStoreOwner, factory)
                .get(entry.id.toString(), TrackListItemViewModel::class.java))
        }
        return list
    }

    fun onClickFab(){
        editEvent.value = Event(EventState.CREATE)
    }
}