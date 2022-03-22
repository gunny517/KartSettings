package jp.ceed.kart.settings.ui.common

interface RowControlListener {

    fun onClickControl(command: RowControlCommand, sessionId: Int)

    enum class RowControlCommand {
        EDIT,
        SAVE,
        DELETE
    }
}