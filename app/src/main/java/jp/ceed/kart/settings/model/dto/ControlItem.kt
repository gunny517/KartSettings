package jp.ceed.kart.settings.model.dto

data class ControlItem(
    val sessionId: Int,
    var isEditable: Boolean = false
)