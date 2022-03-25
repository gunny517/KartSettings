package jp.ceed.kart.settings.ui.practice.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.model.entity.Track

class PracticeListItemViewModel(
    val id: Int,
    var trackName: String,
    var startDate: String,
    var description: String?,
    var onClickListener: (Int, PracticeListItemViewModel) -> Unit
): ViewModel() {
    constructor(practiceTrack: PracticeTrack, onClickListener: (Int, PracticeListItemViewModel) -> Unit): this (
        practiceTrack.id,
        practiceTrack.trackName,
        practiceTrack.startDate,
        practiceTrack.description,
        onClickListener
    )

    fun onClick(view: View){
        onClickListener(view.id, this)
    }

    class Factory(
        private val practiceTrack: PracticeTrack,
        private val onClickListener: (Int, PracticeListItemViewModel) -> Unit)
        : ViewModelProvider.KeyedFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(key: String, modelClass: Class<T>): T {
            return PracticeListItemViewModel(practiceTrack, onClickListener) as T
        }
    }
}