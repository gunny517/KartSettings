package jp.ceed.kart.settings.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class PracticeDetailAdapterItem {

    @Parcelize
    data class PracticeRowItem(
        val index: Int,
        var label: String,
        val values: List<SettingItem>
    ): PracticeDetailAdapterItem(), Parcelable

    data class PracticeControlItem(
        val sessionIdList: List<Int>
    ): PracticeDetailAdapterItem()
}