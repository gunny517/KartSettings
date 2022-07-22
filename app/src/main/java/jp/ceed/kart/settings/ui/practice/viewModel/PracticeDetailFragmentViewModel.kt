package jp.ceed.kart.settings.ui.practice.viewModel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ceed.kart.settings.AbsEventContent
import jp.ceed.kart.settings.domain.repository.ResourceRepository
import jp.ceed.kart.settings.domain.repository.SessionRepository
import jp.ceed.kart.settings.model.SettingElement
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.model.entity.Session
import jp.ceed.kart.settings.ui.Event
import jp.ceed.kart.settings.ui.common.RowControlListener
import jp.ceed.kart.settings.ui.util.CommonUtil
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PracticeDetailFragmentViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val sessionRepository: SessionRepository,
    private val resourceRepository: ResourceRepository,
): ViewModel(), RowControlListener {

    enum class EventType{
        CLICK_DELETE_DIALOG,
        CLICK_CREATE_DIALOG,
        EDIT_MODE_COMPLETED,
    }

    class EventContent(eventType: EventType, value: Int): AbsEventContent<EventType>(eventType, value)

    var practiceRowList: MutableLiveData<List<PracticeDetailAdapterItem>> = MutableLiveData()

    var event: MutableLiveData<Event<EventContent>> = MutableLiveData()

    private val practiceId: Int = savedStateHandle.get<Int>("practiceId")
        ?: throw IllegalArgumentException("Should have practice id.")

    private val viewModelStore = ViewModelStore()

    init {
        loadPracticeRowList()
    }

    private fun loadPracticeRowList(){
        viewModelScope.launch {
            val list: ArrayList<PracticeDetailAdapterItem> = ArrayList()
            val sessionList = sessionRepository.getSessionList(practiceId)
            list.add(createControlItem(sessionList))
            list.addAll(createPracticeRowList(sessionList))
            practiceRowList.value = list
        }
    }

    fun deleteSession(sessionId: Int){
        viewModelScope.launch {
            sessionRepository.deleteBySessionId(sessionId)
            loadPracticeRowList()
        }
    }

    private fun createControlItem(sessionList: List<Session>): PracticeDetailAdapterItem{
        val list: ArrayList<PracticeControlItemViewModel> = ArrayList()
        for(session in sessionList){
            val factory = PracticeControlItemViewModel.Factory(session.id, this)
            val viewModel = ViewModelProvider(viewModelStore, factory)
                .get(session.id.toString(), PracticeControlItemViewModel::class.java)
            list.add(viewModel)
        }
        return PracticeDetailAdapterItem.PracticeControlItem(list)
    }

    private fun createPracticeRowList(sessionList: List<Session>): List<PracticeDetailAdapterItem.PracticeRowItem>{
        val cls = Session::class.java
        val fields = cls.declaredFields
        val resultList: ArrayList<PracticeDetailAdapterItem.PracticeRowItem> = ArrayList()
        for(field in fields){
            field.isAccessible = true
            val annotation = field.getAnnotation(SettingElement::class.java) ?: continue
            val label = resourceRepository.getString(annotation.label)
            val index = annotation.index
            val name = field.name
            val inputType = annotation.inputType
            val ignoreValueChange = annotation.ignoreValueChange
            val list: ArrayList<PracticeSettingItemViewModel> = ArrayList()
            var lastValue: String? = null
            for(session in sessionList){
                val value = field.get(session) as String?
                val isChanged = !ignoreValueChange && lastValue != null && !lastValue.equals(value)
                lastValue = value
                val factory = PracticeSettingItemViewModel.Factory(session.id, name, value, inputType, isChanged)
                val key = CommonUtil().createRandomKey()
                val viewModel = ViewModelProvider(viewModelStore, factory)
                    .get(key, PracticeSettingItemViewModel::class.java)
                list.add(viewModel)
            }
            resultList.add(PracticeDetailAdapterItem.PracticeRowItem(index, label, list))
        }
        Collections.sort(resultList, SessionRepository.PracticeRowComparator())
        return resultList
    }


    fun onClickFab() {
        event.value = Event(EventContent(EventType.CLICK_CREATE_DIALOG, 0))
    }

    fun createSessionAndReload(){
        viewModelScope.launch {
            val newEntity = sessionRepository.getNewEntityForInsert(practiceId)
            sessionRepository.insert(newEntity)
            loadPracticeRowList()
        }
    }

    override fun onClickControl(command: RowControlListener.RowControlCommand, sessionId: Int){
        when(command){
            RowControlListener.RowControlCommand.SAVE -> {
                practiceRowList.value?.let {
                    viewModelScope.launch {
                        sessionRepository.saveSession(it, sessionId, practiceId)
                        loadPracticeRowList()
                        changeEditState(sessionId)
                    }
                }
            }
            RowControlListener.RowControlCommand.DELETE -> {
                event.value = Event(EventContent(EventType.CLICK_DELETE_DIALOG, sessionId))
            }
            RowControlListener.RowControlCommand.EDIT -> {
                changeEditState(sessionId)
            }
        }
    }

    private fun changeEditState(sessionId: Int){
        practiceRowList.value?.let {
            var isEditMode: Boolean = true
            val copyList = it.toList()
            for(entry in copyList){
                when(entry){
                    is PracticeDetailAdapterItem.PracticeRowItem -> {
                        for(session in entry.values){
                            if(sessionId == session.sessionId){
                                session.isEditable = session.isEditable.not()
                            }
                        }
                    }
                    is PracticeDetailAdapterItem.PracticeControlItem -> {
                        for(controlItem in entry.controlItems){
                            if(sessionId == controlItem.sessionId){
                                controlItem.isEditable = controlItem.isEditable.not()
                                isEditMode = controlItem.isEditable
                            }
                        }
                    }
                }
            }
            practiceRowList.value = copyList
            if(!isEditMode){
                event.value = Event(EventContent((EventType.EDIT_MODE_COMPLETED), 0))
            }
        }
    }

}