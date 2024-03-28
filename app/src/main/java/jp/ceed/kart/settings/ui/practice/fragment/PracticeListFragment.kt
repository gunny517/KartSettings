package jp.ceed.kart.settings.ui.practice.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentPracticeListBinding
import jp.ceed.kart.settings.ui.common.fragment.DeleteConfirmDialogFragment
import jp.ceed.kart.settings.ui.navigation.AppNavArgs
import jp.ceed.kart.settings.ui.practice.adapter.PracticeListAdapter
import jp.ceed.kart.settings.ui.practice.viewModel.PracticeListFragmentViewModel

@AndroidEntryPoint
class PracticeListFragment: Fragment() {

    private var _binding: FragmentPracticeListBinding? = null

    val binding get() = _binding!!

    val viewModel: PracticeListFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_practice_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        val adapter = PracticeListAdapter(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.practiceViewModelList.observe(viewLifecycleOwner){
            adapter.setItemList(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.itemClickEvent.observe(viewLifecycleOwner){
            val itemViewModel = it.getContentIfNotHandled()
            itemViewModel?.let { item ->
                onClickPractice(item.id, "${item.startDate} / ${item.trackName}")
            }
        }
        viewModel.deleteButtonEvent.observe(viewLifecycleOwner){
            activity?.let { activity ->
                val content = it.getContentIfNotHandled()
                content?.let { practiceListItemViewModel ->
                    val dialogFragment = DeleteConfirmDialogFragment.newInstance(R.string.msg_delete_practice)
                    dialogFragment.setOnDialogClickListener{
                            dialog, button -> onClickDialogButton(practiceListItemViewModel.id, dialog, button)
                    }
                    dialogFragment.show(activity.supportFragmentManager, dialogFragment.tag(this.javaClass))
                }
            }
        }
        viewModel.editEvent.observe(viewLifecycleOwner){
            val content = it.getContentIfNotHandled()
            content?.let { practiceId ->
                showEditDialog(practiceId ?: 0)
            }
        }
    }

    private fun onClickDialogButton(practiceId: Int, dialog: DialogInterface, button: Int){
        when (button){
            DialogInterface.BUTTON_POSITIVE -> {
                viewModel.deletePractice(practiceId)
            }else -> {}
        }
        dialog.dismiss()
    }

    private fun onClickPractice(practiceId: Int, titleLabel: String){
        findNavController().navigate(
            R.id.toPracticeDetail,
            Bundle().apply {
                putInt(AppNavArgs.PRACTICE_ID.name, practiceId)
                putString(AppNavArgs.TITLE_LABEL.name, titleLabel)
            }
        )
    }

    private fun showEditDialog(practiceId: Int){
        EditPracticeDialogFragment.newInstance(practiceId, ::onPracticeEdit).show(childFragmentManager, EditPracticeDialogFragment.TAG)
    }

    private fun onPracticeEdit(){
        viewModel.loadPracticeList()
    }

}