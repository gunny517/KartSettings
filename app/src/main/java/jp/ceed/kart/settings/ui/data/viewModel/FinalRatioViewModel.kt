package jp.ceed.kart.settings.ui.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.ceed.kart.settings.domain.repository.FinalRatioRepository

class FinalRatioViewModel: ViewModel() {

    private val finalRatioRepository = FinalRatioRepository()

    val finalRatioList: MutableLiveData<List<String>> = MutableLiveData()

    var driveMin: MutableLiveData<String> = MutableLiveData(FinalRatioRepository.DRIVE_MIN.toString())

    var driveMax: MutableLiveData<String> = MutableLiveData(FinalRatioRepository.DRIVE_MAX.toString())

    var drivenMin: MutableLiveData<String> = MutableLiveData(FinalRatioRepository.DRIVEN_MIN.toString())

    var drivenMax: MutableLiveData<String> = MutableLiveData(FinalRatioRepository.DRIVEN_MAX.toString())

    var colSize: MutableLiveData<Int> = MutableLiveData()


    init {
        loadFinalRatio()
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

    }
}