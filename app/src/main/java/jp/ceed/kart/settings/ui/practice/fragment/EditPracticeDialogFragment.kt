package jp.ceed.kart.settings.ui.practice.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentEditPracticeDaialogBinding
import jp.ceed.kart.settings.ui.navigation.AppNavArgs
import jp.ceed.kart.settings.ui.practice.viewModel.EditPracticeDialogFragmentViewModel

@AndroidEntryPoint
class EditPracticeDialogFragment: DialogFragment(), DialogInterface.OnClickListener {

    companion object {

        const val TAG = "EditPracticeDialogFragment"

        fun newInstance(practiceId: Int = 0, onEdit: () -> Unit): EditPracticeDialogFragment{
            val fragment = EditPracticeDialogFragment()
            val args = Bundle()
            args.putInt(AppNavArgs.PRACTICE_ID.name, practiceId)
            fragment.arguments = args
            fragment.onEdit = onEdit
            return fragment
        }
    }

    private val viewModel: EditPracticeDialogFragmentViewModel by viewModels()

    private var practiceId: Int = 0

    private var _binding: FragmentEditPracticeDaialogBinding? = null

    val binding get() = _binding!!

    var onEdit: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        practiceId = arguments?.getInt(AppNavArgs.PRACTICE_ID.name, 0) ?: 0
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_edit_practice_daialog, null ,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        init()
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.ok, this)
            .setNegativeButton(R.string.cancel, this)
            .create()
    }

    override fun onClick(dialog: DialogInterface?, button: Int) {
        when(button){
            DialogInterface.BUTTON_POSITIVE -> {
                viewModel.savePractice()
            }else -> {}
        }
        dismiss()
    }

    private fun init(){
        viewModel.labelList.observe(this){
            val spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, it ?: mutableListOf())
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = spinnerAdapter
        }
        viewModel.savedEvent.observe(this){
            onEdit()
        }
    }
}