package jp.ceed.kart.settings.ui.common.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import jp.ceed.kart.settings.R

class DeleteConfirmDialogFragment: DialogFragment(), DialogInterface.OnClickListener {

    companion object{
        const val KEY_MSG = "KEY_MSG"

        fun newInstance(msgRes: Int): DeleteConfirmDialogFragment{
            val fragment = DeleteConfirmDialogFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_MSG, msgRes)
            fragment.arguments = bundle
            return fragment
        }
    }

    var onClickListener: DialogInterface.OnClickListener? = null

    var msgRes: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            msgRes = it.getInt(KEY_MSG, 0)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(msgRes)
            .setPositiveButton(R.string.ok, this)
            .setNegativeButton(R.string.cancel, this)
            .create()
    }

    override fun onClick(p0: DialogInterface?, p1: Int) {
        onClickListener?.onClick(p0, p1)
    }

    fun setOnDialogClickListener(listener: DialogInterface.OnClickListener){
        onClickListener = listener
    }

    fun tag(cls: Class<Fragment>): String {
        return cls.simpleName + this.javaClass.simpleName
    }
}