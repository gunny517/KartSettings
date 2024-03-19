package jp.ceed.kart.settings.ui.track.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TrackListItemViewModel(): ViewModel() {

    var id: Int = 0
    var name: String? = null
    var onClick: ((Int, View) -> Unit) = { _, _ -> }
    constructor(
        id: Int,
        name: String?,
        onClick: (Int, View) -> Unit
    ): this() {
        this.id = id
        this.name = name
        this.onClick = onClick
        this.trackName.value = name
    }

    class Factory(
        val id: Int,
        val name: String?,
        val onClick: (Int, View) -> Unit
    ): ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackListItemViewModel(id, name, onClick) as T
        }
    }

    val trackName: MutableLiveData<String> = MutableLiveData(name)

    fun onClickEdit(view: View){
        onClick(id, view)
    }

    fun onClickDelete(view: View){
        onClick(id, view)
    }
}