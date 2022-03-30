package jp.ceed.kart.settings.ui.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.ceed.kart.settings.domain.repository.FinalRatioRepository
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.track.viewModel.TrackListFragmentViewModel

class FinalRatioViewModel(application: Application) : AndroidViewModel(application) {

    enum class EventState { CALCULATE }

    private val finalRatioRepository = FinalRatioRepository(application.applicationContext)

    val finalRatioList: MutableLiveData<List<String>> = MutableLiveData()

    var driveMin: MutableLiveData<String> = MutableLiveData()

    var driveMax: MutableLiveData<String> = MutableLiveData()

    var drivenMin: MutableLiveData<String> = MutableLiveData()

    var drivenMax: MutableLiveData<String> = MutableLiveData()

    var colSize: MutableLiveData<Int> = MutableLiveData()

    var event: MutableLiveData<Event<EventState>> = MutableLiveData()


    init {
        loadInitialValues()
        loadFinalRatio()
    }

    private fun loadInitialValues(){
        val dto = finalRatioRepository.getSavedValue()
        driveMin.value = dto.driveMin.toString()
        driveMax.value = dto.driveMax.toString()
        drivenMin.value = dto.drivenMin.toString()
        drivenMax.value = dto.drivenMax.toString()
    }

    fun loadFinalRatio(){
        val result = finalRatioRepository.getFinalRatioData(
            driveMin.value,
            driveMax.value,
            drivenMin.value,
            drivenMax.value
        )
        colSize.value = result.second ?: 0
        finalRatioList.value = result.first ?: mutableListOf()
        event.value = Event(EventState.CALCULATE)
    }
}