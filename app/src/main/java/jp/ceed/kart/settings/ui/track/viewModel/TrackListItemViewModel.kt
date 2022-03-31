package jp.ceed.kart.settings.ui.track.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TrackListItemViewModel(val id: Int, val name: String?, val onClick: (Int, TrackListFragmentViewModel.EventState) -> Unit): ViewModel() {

    class Factory(val id: Int, val name: String?, val onClick: (Int, TrackListFragmentViewModel.EventState) -> Unit): ViewModelProvider.KeyedFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>): T {
            return TrackListItemViewModel(id, name, onClick) as T
        }
    }

    val trackName: MutableLiveData<String> = MutableLiveData(name)

    fun onClickEdit(){
        onClick(id, TrackListFragmentViewModel.EventState.CREATE)
    }

    fun onClickDelete(){
        onClick(id, TrackListFragmentViewModel.EventState.DELETE)
    }
}