package jp.ceed.kart.settings.ui.practice.viewModel

import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.PracticeRepository
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Practice
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.model.entity.Track
import kotlinx.coroutines.launch
import java.util.*

class PracticeListFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val practiceRepository = PracticeRepository(application.applicationContext)

    private val trackRepository = TrackRepository(application.applicationContext)

    val practiceList: MutableLiveData<List<PracticeTrack>> = MutableLiveData()

    var editPracticeLayoutVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    var trackList: MutableLiveData<List<Track>> = MutableLiveData()

    var labelList: MutableLiveData<List<String>> = MutableLiveData()

    var year: MutableLiveData<String> = MutableLiveData()

    var month: MutableLiveData<String> = MutableLiveData()

    var date: MutableLiveData<String> = MutableLiveData()

    var selectedItemPosition: MutableLiveData<Int> = MutableLiveData(0)

    init {
        loadPracticeList()
        initEditLayout()
    }

    private fun loadPracticeList(){
        viewModelScope.launch {
            practiceList.value = practiceRepository.findAll()
        }
    }

    fun onClickFab(){
        toggleEditLayoutVisibility()
    }

    private fun savePractice(track: Track, year: String?, month: String?, date: String?){
        if(TextUtils.isEmpty(year) || TextUtils.isEmpty(month) || TextUtils.isEmpty(date)){
            return
        }
        viewModelScope.launch {
            practiceRepository.insert(Practice(
                0,
                track.id,
                "$year-$month-$date"
            ))
            loadPracticeList()
        }
    }

    private fun initEditLayout(){
        viewModelScope.launch {
            val trackList = trackRepository.getTrackList()
            labelList.value = createTrackLabels(trackList)
            this@PracticeListFragmentViewModel.trackList.value = trackList
        }
        val cal = Calendar.getInstance()
        year.value = cal.get(Calendar.YEAR).toString()
        month.value = (cal.get(Calendar.MONTH) + 1).toString()
        date.value = cal.get(Calendar.DAY_OF_MONTH).toString()
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
        savePractice(track, year.value, month.value, date.value)
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