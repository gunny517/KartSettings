package jp.ceed.kart.settings.ui.practice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.kart.settings.domain.repository.PracticeRepository
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Practice
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.util.UiUtil
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditPracticeDialogFragmentViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val practiceRepository: PracticeRepository,
    private val trackRepository: TrackRepository,
    private val uiUtil: UiUtil,
): ViewModel() {

    val practiceId: Int = savedStateHandle.get<Int>("practiceId")
        ?: throw IllegalStateException("Should have practice id.")

    private var practiceTrack: PracticeTrack? = null

    private var calendar = Calendar.getInstance()

    var year: MutableLiveData<Int> = MutableLiveData()

    var month: MutableLiveData<Int> = MutableLiveData()

    var day: MutableLiveData<Int> = MutableLiveData()

    var description: MutableLiveData<String> = MutableLiveData()

    var selectedItemPosition: MutableLiveData<Int> = MutableLiveData(0)

    var labelList: MutableLiveData<List<String?>> = MutableLiveData()

    var trackList: List<Track> = mutableListOf()

    var savedEvent: MutableLiveData<Event<Int>> = MutableLiveData()


    init {
        init()
    }

    private fun init(){
        loadTrack()
        if(practiceId != 0){
            viewModelScope.launch {
                practiceTrack = practiceRepository.findPracticeTrackByPracticeId(practiceId)
                initForEdit()
            }
        }else{
            initForCreate()
        }
    }

    private fun loadTrack(){
        viewModelScope.launch {
            trackList = trackRepository.getTrackList()
            labelList.value = createTrackLabels(trackList)
        }
    }

    private fun createTrackLabels(trackList: List<Track>): List<String?> {
        val list: ArrayList<String?> = ArrayList()
        for(entry in trackList){
            list.add(entry.name)
        }
        return list
    }


    private fun initForCreate(){
        calendar =  Calendar.getInstance()
        year.value = calendar.get(Calendar.YEAR)
        month.value = calendar.get(Calendar.MONTH)
        day.value = calendar.get(Calendar.DAY_OF_MONTH)
        description = MutableLiveData()
    }

    private fun initForEdit(){
        practiceTrack?.let { item ->
            calendar = uiUtil.fromYmdString(item.startDate)
            year.value = calendar.get(Calendar.YEAR)
            month.value = calendar.get(Calendar.MONTH)
            day.value = calendar.get(Calendar.DAY_OF_MONTH)
            description.value = item.description ?: ""
        }
    }

    fun savePractice(){
        val track: Track = trackList[selectedItemPosition.value ?: 0]
        year.value?.let {
            calendar.set(Calendar.YEAR, it)
        }
        month.value?.let {
            calendar.set(Calendar.MONTH, it)
        }
        day.value?.let {
            calendar.set(Calendar.DAY_OF_MONTH, it)
        }
        val startDate = uiUtil.toYmdString(calendar.time)
        viewModelScope.launch {
            practiceRepository.save(Practice(practiceId, track.id, startDate, description.value))
            savedEvent.value = Event(0)
        }
    }

}