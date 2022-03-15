package jp.ceed.kart.settings.ui.track.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import jp.ceed.kart.settings.R

class EditTrackDialogFragment(val onInput: (trackName: String) -> Unit): DialogFragment() {

    lateinit var editText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = layoutInflater.inflate(R.layout.fragment_edit_track_dialog, null, false)
        editText = view.findViewById(R.id.trackNameEditText)
        return AlertDialog.Builder(requireContext())
            .setPositiveButton(R.string.ok) { _, _ -> onClickOk() }
            .setNegativeButton(R.string.cancel) { _, _ -> onClickCancel() }
            .setView(view)
            .create()
    }

    private fun onClickOk(){
        val trackName: String = editText.text.toString()
        onInput(trackName)
        dismiss()
    }

    private fun onClickCancel(){
        dismiss()
    }

    companion object {
        const val TAG = "EditTrackDialogFragment"
    }

}