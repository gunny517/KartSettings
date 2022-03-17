package jp.ceed.kart.settings.model.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PracticeRowItem(
    val index: Int,
    val label: String,
    val values: List<SettingItem>
): Parcelable