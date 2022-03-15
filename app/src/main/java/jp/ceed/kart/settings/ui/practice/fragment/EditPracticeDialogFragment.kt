package jp.ceed.kart.settings.ui.practice.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentEditPracticeDialogBinding
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.ui.practice.viewModel.EditPracticeDialogFragmentViewModel

class EditPracticeDialogFragment(
    val onSelect: (track: Track, year: String, month: String, date:String) -> Unit): DialogFragment() {

    companion object {
        const val TAG = "EditPracticeDialogFragment"
    }

    private var _binding: FragmentEditPracticeDialogBinding? = null

    val binding get() = _binding!!

    val viewModel: EditPracticeDialogFragmentViewModel by viewModels()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_edit_practice_dialog, null, false)
        binding.viewModel = viewModel
        init()
        return AlertDialog.Builder(requireContext())
            .setPositiveButton(R.string.ok){ _, _ -> onClickOk() }
            .setNegativeButton(R.string.cancel){_, _ -> onClickCancel()}
            .setView(binding.root)
            .create()
    }

    private fun init(){
        viewModel.labelList.observe(this){
            val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, it ?: mutableListOf())
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
    }

    private fun onClickOk(){
        val track: Track = viewModel.trackList.value?.get(binding.spinner.selectedItemPosition) as Track
        val year = binding.yearEditText.text.toString()
        val month = binding.mothEditText.text.toString()
        val date = binding.dateEditText.text.toString()
        onSelect(track, year, month, date)
        dismiss()
    }

    private fun onClickCancel(){
        dismiss()
    }
}