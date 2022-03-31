package jp.ceed.kart.settings.ui.practice.viewModel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.*
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.domain.repository.PracticeRepository
import jp.ceed.kart.settings.domain.repository.TrackRepository
import jp.ceed.kart.settings.model.entity.Practice
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.practice.fragment.PracticeListFragmentDirections
import jp.ceed.kart.settings.ui.util.UiUtil
import kotlinx.coroutines.launch
import java.util.*

class PracticeListFragmentViewModel(val application: Application, private val viewModelStoreOwner: ViewModelStoreOwner) : ViewModel() {

    class Factory(private val application: Application, private val viewModelStoreOwner: ViewModelStoreOwner): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeListFragmentViewModel(application, viewModelStoreOwner) as T
        }
    }

    private val practiceRepository = PracticeRepository(application.applicationContext)

    val practiceViewModelList: MutableLiveData<List<PracticeListItemViewModel>> = MutableLiveData()

    var itemClickEvent: MutableLiveData<Event<PracticeListItemViewModel>> = MutableLiveData()

    var deleteButtonEvent: MutableLiveData<Event<PracticeListItemViewModel>> = MutableLiveData()

    var editEvent: MutableLiveData<Event<Int>> = MutableLiveData()


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
            val key = Random().nextInt().toString() // 同じキーにならないように乱数をキーにする（他に良いやり方検討したい）
            list.add(ViewModelProvider(viewModelStoreOwner, factory).get(key, PracticeListItemViewModel::class.java))
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