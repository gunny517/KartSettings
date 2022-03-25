package jp.ceed.kart.settings.ui.practice.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.PracticeRepository
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Practice
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.util.UiUtil
import kotlinx.coroutines.launch
import java.util.*

class PracticeListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val practiceRepository = PracticeRepository(application.applicationContext)

    private val trackRepository = TrackRepository(application.applicationContext)

    val practiceList: MutableLiveData<List<PracticeTrack>> = MutableLiveData()

    var editPracticeLayoutVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    var trackList: MutableLiveData<List<Track>> = MutableLiveData()

    var labelList: MutableLiveData<List<String>> = MutableLiveData()

    var selectedItemPosition: MutableLiveData<Int> = MutableLiveData(0)

    val calendar = Calendar.getInstance()

    var year: MutableLiveData<Int> = MutableLiveData(calendar.get(Calendar.YEAR))

    var month: MutableLiveData<Int> = MutableLiveData(calendar.get(Calendar.MONTH))

    var day: MutableLiveData<Int> = MutableLiveData(calendar.get(Calendar.DAY_OF_MONTH))

    val uiUtil = UiUtil(application.applicationContext)


    fun initEditLayout(){
        viewModelScope.launch {
            val trackList = trackRepository.getTrackList()
            labelList.value = createTrackLabels(trackList)
            this@PracticeListFragmentViewModel.trackList.value = trackList
        }
    }

    fun loadPracticeList(){
        viewModelScope.launch {
            practiceList.value = practiceRepository.findAll()
        }
    }

    fun onClickFab(){
        toggleEditLayoutVisibility()
    }

    private fun savePractice(track: Track){
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
            practiceRepository.insert(Practice(0, track.id, startDate))
            loadPracticeList()
        }
    }

    private fun createTrackLabels(trackList: List<Track>): List<String> {
        val list: ArrayList<String> = ArrayList()
        for(entry in trackList){
            list.add(entry.name)
        }
        return list
    }

    fun onClickDialogBackground(){
        toggleEditLayoutVisibility()
    }

    fun onClickOk(){
        val track: Track = trackList.value?.get(selectedItemPosition.value ?: 0) as Track
        savePractice(track)
        toggleEditLayoutVisibility()
    }

    fun onClickCancel(){
        toggleEditLayoutVisibility()
    }

    private fun toggleEditLayoutVisibility(){
        editPracticeLayoutVisibility.value = if(editPracticeLayoutVisibility.value == View.VISIBLE){
            View.GONE
        }else{
            View.VISIBLE
        }
    }
}