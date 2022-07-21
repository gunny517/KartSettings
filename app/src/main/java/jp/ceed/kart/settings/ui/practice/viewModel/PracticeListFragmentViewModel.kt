package jp.ceed.kart.settings.ui.practice.viewModel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.domain.repository.PracticeRepository
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.util.CommonUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeListFragmentViewModel @Inject constructor (
    private val practiceRepository: PracticeRepository
) : ViewModel() {

    val practiceViewModelList: MutableLiveData<List<PracticeListItemViewModel>> = MutableLiveData()

    var itemClickEvent: MutableLiveData<Event<PracticeListItemViewModel>> = MutableLiveData()

    var deleteButtonEvent: MutableLiveData<Event<PracticeListItemViewModel>> = MutableLiveData()

    var editEvent: MutableLiveData<Event<Int>> = MutableLiveData()

    init {
        loadPracticeList()
    }

    fun loadPracticeList(){
        viewModelScope.launch {
            val list = practiceRepository.findAll()
            practiceViewModelList.value = createPracticeListItemViewModels(list)
        }
    }

    private fun createPracticeListItemViewModels(practiceList: List<PracticeTrack>): List<PracticeListItemViewModel> {
        val list: ArrayList<PracticeListItemViewModel> = ArrayList()
        for(entry in practiceList){
            val factory = PracticeListItemViewModel.Factory(entry, ::onClick)
            val key = CommonUtil().createRandomKey() // 同じキーにならないように乱数をキーにする（他に良いやり方検討したい）
            list.add(ViewModelProvider(ViewModelStore() , factory).get(key, PracticeListItemViewModel::class.java))
        }
        return list
    }

    private fun onClick(viewId: Int, practiceListItemViewModel: PracticeListItemViewModel){
        when(viewId){
            R.id.editButton -> {
                editEvent.value = Event(practiceListItemViewModel.id)
            }
            R.id.deleteButton -> {
                deleteButtonEvent.value = Event(practiceListItemViewModel)
            }
            R.id.practiceListItemLayout -> {
                itemClickEvent.value = Event(practiceListItemViewModel)
            }
        }
    }

    fun onClickFab(){
        editEvent.value = Event(0)
    }


    fun deletePractice(practiceId: Int){
        viewModelScope.launch {
            practiceRepository.deleteById(practiceId)
            loadPracticeList()
        }
    }
}