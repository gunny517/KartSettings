package jp.ceed.kart.settings.model.dto

import android.os.Parcelable
import jp.ceed.kart.settings.model.entity.Session
import kotlinx.parcelize.Parcelize

sealed class SessionListItem {

    @Parcelize
    data class SessionContent(
        val session: Session
    ): SessionListItem(), Parcelable

    @Parcelize
    data class SessionHeader(
        val labelList: ArrayList<String>
    ): SessionListItem(), Parcelable
}