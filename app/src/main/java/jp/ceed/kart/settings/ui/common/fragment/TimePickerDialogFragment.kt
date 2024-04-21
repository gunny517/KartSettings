package jp.ceed.kart.settings.ui.common.fragment

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TimePickerDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "TimePickerDialogFragment"
    }

    private var onTimeSetListener: OnTimeSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val listener = OnTimeSetListener { view, hourOfDay, minute ->
            onTimeSetListener?.onTimeSet(
                view,
                hourOfDay,
                minute
            )
        }

        return TimePickerDialog(
            requireContext(),
            listener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
    }

    fun setOnTimeSetListener(onTimeSetListener: OnTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener
    }
}