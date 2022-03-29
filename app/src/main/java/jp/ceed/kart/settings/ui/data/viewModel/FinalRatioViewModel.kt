package jp.ceed.kart.settings.ui.data.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.ceed.kart.settings.domain.repository.FinalRatioRepository

class FinalRatioViewModel: ViewModel() {

    private val finalRatioRepository = FinalRatioRepository()

    val finalRatioList: MutableLiveData<List<String>> = MutableLiveData()

    init {
        loadFinalRatio()
    }

    private fun loadFinalRatio(){
        finalRatioList.value = finalRatioRepository.getFinalRatioData()
    }
}