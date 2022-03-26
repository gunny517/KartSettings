package jp.ceed.kart.settings.ui.practice.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentEditTrackDialogBinding
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.track.viewModel.EditTrackDialogFragmentViewModel

class EditTrackDialogFragment(private val track: Track)
    : DialogFragment(), DialogInterface.OnClickListener {

    val viewModel: EditTrackDialogFragmentViewModel by viewModels(factoryProducer = ::factoryProducer)

    private var activity: Activity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: FragmentEditTrackDialogBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_edit_track_dialog, null, false)
        binding.viewModel = viewModel
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.ok, this)
            .setNegativeButton(R.string.cancel, this)
            .create()
    }

    companion object {
        const val TAG = "EditTrackDialogFragment"
    }

    override fun onClick(dialog: DialogInterface?, button: Int) {
        when(button){
            DialogInterface.BUTTON_POSITIVE -> {
                viewModel.saveTrack(track)
            }else -> {}
        }
        dismiss()
    }

    fun factoryProducer(): EditTrackDialogFragmentViewModel.Factory{
        return EditTrackDialogFragmentViewModel.Factory(requireContext(), track)
    }
}