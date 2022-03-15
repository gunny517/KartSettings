package jp.ceed.kart.settings.ui.practice.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Track
import kotlinx.coroutines.launch
import java.util.*

class EditPracticeDialogFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val trackRepository = TrackRepository(application.applicationContext)

    var trackList: MutableLiveData<List<Track>> = MutableLiveData()

    var labelList: MutableLiveData<List<String>> = MutableLiveData()

    var year: MutableLiveData<String> = MutableLiveData()

    var month: MutableLiveData<String> = MutableLiveData()

    var date: MutableLiveData<String> = MutableLiveData()

    init {
        loadPractice()
        init()
    }

    private fun loadPractice(){
        viewModelScope.launch {
            val _trackList = trackRepository.getTrackList()
            labelList.value = createStringList(_trackList)
            trackList.value = _trackList
        }
    }

    fun createStringList(trackList: List<Track>): List<String> {
        val list: ArrayList<String> = ArrayList()
        for(entry in trackList){
            list.add(entry.name)
        }
        return list
    }

    private fun init(){
        val cal = Calendar.getInstance()
        year.value = cal.get(Calendar.YEAR).toString()
        month.value = (cal.get(Calendar.MONTH) + 1).toString()
        date.value = cal.get(Calendar.DAY_OF_MONTH).toString()
    }

}