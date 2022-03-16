package jp.ceed.kart.settings.model.dto

import jp.ceed.kart.settings.model.entity.Session

sealed class SessionListItem {

    data class SessionContent(
        val session: Session
    ): SessionListItem()

    data class SessionHeader(
        val labelList: List<String>
    ): SessionListItem()
}