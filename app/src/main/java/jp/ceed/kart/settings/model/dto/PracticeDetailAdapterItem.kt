package jp.ceed.kart.settings.model.dto

import android.os.Parcelable
import jp.ceed.kart.settings.ui.practice.viewModel.PracticeControlItemViewModel
import jp.ceed.kart.settings.ui.practice.viewModel.PracticeSettingItemViewModel
import kotlinx.parcelize.Parcelize

sealed class PracticeDetailAdapterItem {

    @Parcelize
    data class PracticeRowItem(
        val index: Int,
        var label: String,
        val values: List<PracticeSettingItemViewModel>
    ): PracticeDetailAdapterItem(), Parcelable

    data class PracticeControlItem(
        val controlItems: List<PracticeControlItemViewModel>
    ): PracticeDetailAdapterItem()
}