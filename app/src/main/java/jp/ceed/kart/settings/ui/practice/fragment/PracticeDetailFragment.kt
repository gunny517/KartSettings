package jp.ceed.kart.settings.ui.practice.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentPracticeDetailBinding
import jp.ceed.kart.settings.ui.common.fragment.DeleteConfirmDialogFragment
import jp.ceed.kart.settings.ui.common.fragment.TimePickerDialogFragment
import jp.ceed.kart.settings.ui.practice.adapter.PracticeDetailAdapter
import jp.ceed.kart.settings.ui.practice.viewModel.PracticeDetailFragmentViewModel
import jp.ceed.kart.settings.ui.util.UiUtil

@AndroidEntryPoint
class PracticeDetailFragment: Fragment() {

    private val viewModel: PracticeDetailFragmentViewModel by viewModels()

    private var _binding: FragmentPracticeDetailBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: PracticeDetailAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
                val dialog = DeleteConfirmDialogFragment.newInstance(R.string.msg_delete_session)
                dialog.setOnDialogClickListener{ _, button -> onClickDialogButton(button, eventContent)}
                dialog.show(childFragmentManager, dialog.tag(this.javaClass))
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
            PracticeDetailFragmentViewModel.EventType.FOCUS_TIME_FIELD -> {
                val timePickerFragment = TimePickerDialogFragment().apply {
                    setOnTimeSetListener { _, hourOfDay, minute ->
                        onTimeSelect(
                            eventContent.value,
                            hourOfDay,
                            minute
                        )
                    }
                }
                timePickerFragment.show(childFragmentManager, TimePickerDialogFragment.TAG)
            }
        }
    }

    private fun onTimeSelect(sessionId: Int, hour: Int, minute: Int) {
        viewModel.resetStartTime(sessionId, hour, minute)
    }

    private fun onClickDialogButton(button: Int, eventContent: PracticeDetailFragmentViewModel.EventContent){
        when(button){
            DialogInterface.BUTTON_POSITIVE -> {
                viewModel.deleteSession(eventContent.value)
            }else -> {}
        }
    }
}