package jp.ceed.kart.settings.ui.practice.viewModel

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
import jp.ceed.kart.settings.ui.util.UiUtil
import kotlinx.coroutines.launch
import java.util.*

class PracticeListFragmentViewModel(val context: Context, private val viewModelStoreOwner: ViewModelStoreOwner) : ViewModel() {

    class Factory(private val context: Context, private val viewModelStoreOwner: ViewModelStoreOwner): ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PracticeListFragmentViewModel(context, viewModelStoreOwner) as T
        }
    }


    private val practiceRepository = PracticeRepository(context)

    private val trackRepository = TrackRepository(context)

    val practiceViewModelList: MutableLiveData<List<PracticeListItemViewModel>> = MutableLiveData()

    var editPracticeLayoutVisibility: MutableLiveData<Int> = MutableLiveData(View.GONE)

    var trackList: MutableLiveData<List<Track>> = MutableLiveData()

    var labelList: MutableLiveData<List<String>> = MutableLiveData()

    var selectedItemPosition: MutableLiveData<Int> = MutableLiveData(0)

    private var calendar = Calendar.getInstance()

    var year: MutableLiveData<Int> = MutableLiveData(calendar.get(Calendar.YEAR))

    var month: MutableLiveData<Int> = MutableLiveData(calendar.get(Calendar.MONTH))

    var day: MutableLiveData<Int> = MutableLiveData(calendar.get(Calendar.DAY_OF_MONTH))

    var practiceId: Int = 0

    var description: MutableLiveData<String> = MutableLiveData()

    private val uiUtil = UiUtil(context)

    var event: MutableLiveData<Event<PracticeListItemViewModel>> = MutableLiveData()


    fun initEditLayout(){
        viewModelScope.launch {
            val trackList = trackRepository.getTrackList()
            labelList.value = createTrackLabels(trackList)
            this@PracticeListFragmentViewModel.trackList.value = trackList
        }
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
            val key = Random().nextInt().toString() // 同じキーにならないように乱数をキーにする（他に良いやり方検討したい）
            list.add(ViewModelProvider(viewModelStoreOwner, factory).get(key, PracticeListItemViewModel::class.java))
        }
        return list
    }

    private fun onClick(viewId: Int, practiceListItemViewModel: PracticeListItemViewModel){
        when(viewId){
            R.id.editButton -> {
                applyPracticeDataToEditLayout(practiceListItemViewModel)
                toggleEditLayoutVisibility()
            }
            R.id.practiceListItemLayout -> {
                event.value = Event(practiceListItemViewModel)
            }
        }
    }

    private fun applyPracticeDataToEditLayout(itemViewModel: PracticeListItemViewModel){
        practiceId = itemViewModel.id
        calendar = uiUtil.fromYmdString(itemViewModel.startDate)
        year.value = calendar.get(Calendar.YEAR)
        month.value = calendar.get(Calendar.MONTH)
        day.value = calendar.get(Calendar.DAY_OF_MONTH)
        description.value = itemViewModel.description ?: ""

    }

    fun onClickFab(){
        resetEditPracticeLayout()
        toggleEditLayoutVisibility()
    }

    private fun resetEditPracticeLayout(){
        practiceId = 0
        calendar =  Calendar.getInstance()
        year.value = calendar.get(Calendar.YEAR)
        month.value = calendar.get(Calendar.MONTH)
        day.value = calendar.get(Calendar.DAY_OF_MONTH)
        description = MutableLiveData()
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
            practiceRepository.save(Practice(practiceId, track.id, startDate, description.value))
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