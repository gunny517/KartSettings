package jp.ceed.kart.settings.ui.practice.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import jp.ceed.kart.settings.R

class CreateSessionDialogFragment(val onClick: (Int) -> Unit): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.msg_create_session)
            .setPositiveButton(R.string.ok){_, i -> onClick(i)}
            .setNegativeButton(R.string.cancel){ _, i -> onClick(i) }
            .create()
    }

    companion object {
        const val TAG = "CreateSessionDialogFragment"
    }
}