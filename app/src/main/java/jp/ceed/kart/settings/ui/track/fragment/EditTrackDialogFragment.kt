package jp.ceed.kart.settings.ui.track.fragment

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
import jp.ceed.kart.settings.ui.track.viewModel.EditTrackDialogFragmentViewModel

class EditTrackDialogFragment(): DialogFragment(), DialogInterface.OnClickListener {

    companion object {
        const val TAG = "EditTrackDialogFragment"
        const val KEY_TRACK_ID = "KEY_TRACK_ID"

        fun newInstance(trackId: Int, _onEdit: () -> Unit): EditTrackDialogFragment {
            val fragment = EditTrackDialogFragment()
            val bundle = Bundle()
            bundle.putInt(KEY_TRACK_ID, trackId)
            fragment.arguments = bundle
            fragment.onEdit = _onEdit
            return fragment
        }
    }

    val viewModel: EditTrackDialogFragmentViewModel by viewModels(factoryProducer = ::factoryProducer)

    private var activity: Activity? = null

    private var trackId: Int = 0

    var onEdit: () -> Unit = {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackId = arguments?.getInt(KEY_TRACK_ID, 0) ?: 0
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = getActivity()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: FragmentEditTrackDialogBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_edit_track_dialog, null, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.ok, this)
            .setNegativeButton(R.string.cancel, this)
            .create()
    }

    override fun onClick(dialog: DialogInterface?, button: Int) {
        when(button){
            DialogInterface.BUTTON_POSITIVE -> {
                viewModel.saveTrack()
            }else -> {}
        }
        dismiss()
        onEdit()
    }

    private fun factoryProducer(): EditTrackDialogFragmentViewModel.Factory{
        return EditTrackDialogFragmentViewModel.Factory(requireContext(), trackId)
    }
}