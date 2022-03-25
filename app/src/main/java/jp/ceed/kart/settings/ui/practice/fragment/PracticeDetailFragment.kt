package jp.ceed.kart.settings.ui.practice.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentPracticeDetailBinding
import jp.ceed.kart.settings.ui.common.fragment.DeleteConfirmDialogFragment
import jp.ceed.kart.settings.ui.practice.adapter.PracticeDetailAdapter
import jp.ceed.kart.settings.ui.practice.viewModel.PracticeDetailFragmentViewModel
import jp.ceed.kart.settings.ui.util.UiUtil

class PracticeDetailFragment: Fragment() {

    private val args: PracticeDetailFragmentArgs by navArgs()

    private val viewModel: PracticeDetailFragmentViewModel by viewModels(factoryProducer = ::factoryProducer)

    private var _binding: FragmentPracticeDetailBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: PracticeDetailAdapter

    private fun factoryProducer(): PracticeDetailFragmentViewModel.Factory {
        return PracticeDetailFragmentViewModel.Factory(this, requireContext(), args.practiceId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_practice_detail, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        adapter = PracticeDetailAdapter(requireContext(), viewLifecycleOwner, )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.practiceRowList.observe(viewLifecycleOwner){
            adapter.setRowList(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.event.observe(viewLifecycleOwner){
            onViewModelEvent(it.getContentIfNotHandled() as PracticeDetailFragmentViewModel.EventContent)
        }
    }

    private fun onViewModelEvent(eventContent: PracticeDetailFragmentViewModel.EventContent){
        when(eventContent.eventType){
            PracticeDetailFragmentViewModel.EventType.CLICK_DELETE_DIALOG -> {
                DeleteConfirmDialogFragment { _, button ->
                    when(button){
                        DialogInterface.BUTTON_POSITIVE -> {
                            viewModel.deleteSession(eventContent.value)
                        }else -> {}
                    }
                }.show(childFragmentManager, DeleteConfirmDialogFragment.TAG)
            }
            PracticeDetailFragmentViewModel.EventType.EDIT_MODE_COMPLETED -> {
                UiUtil(requireContext()).hideKeyboard(binding.root)
            }
            PracticeDetailFragmentViewModel.EventType.CLICK_CREATE_DIALOG -> {
                CreateSessionDialogFragment { button ->
                    when(button){
                        DialogInterface.BUTTON_POSITIVE -> {
                            viewModel.createSessionAndReload()
                        } else -> {}
                    }
                }.show(childFragmentManager, CreateSessionDialogFragment.TAG)
            }
        }
    }
}