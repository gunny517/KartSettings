package jp.ceed.kart.settings.ui.common.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import jp.ceed.kart.settings.R

class DeleteConfirmDialogFragment(val onClick: (DialogInterface, Int) -> Unit): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.msg_delete_session)
            .setPositiveButton(R.string.ok) { dialog, i -> onClick(dialog, i) }
            .setNegativeButton(R.string.cancel){ dialog, i -> onClick(dialog, i) }
            .create()
    }

    companion object{
        const val TAG = "DeleteConfirmDialogFragment"
    }
}