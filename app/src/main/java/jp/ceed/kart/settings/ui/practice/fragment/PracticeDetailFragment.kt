package jp.ceed.kart.settings.ui.practice.fragment

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
import jp.ceed.kart.settings.ui.common.RowControlListener
import jp.ceed.kart.settings.ui.practice.adapter.PracticeDetailAdapter
import jp.ceed.kart.settings.ui.practice.viewModel.PracticeDetailFragmentViewModel

class PracticeDetailFragment: Fragment(), RowControlListener {

    private val args: PracticeDetailFragmentArgs by navArgs()

    private val viewModel: PracticeDetailFragmentViewModel by viewModels(factoryProducer = ::factoryProducer)

    private var _binding: FragmentPracticeDetailBinding? = null

    private val binding get() = _binding!!

    private fun factoryProducer(): PracticeDetailFragmentViewModel.Factory {
        return PracticeDetailFragmentViewModel.Factory(requireContext(), args.practiceId)
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
        val adapter = PracticeDetailAdapter(requireContext(), this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.practiceRowList.observe(viewLifecycleOwner){
            adapter.setRowList(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onClickControl(command: RowControlListener.RowControlCommand, sessionId: Int) {
        viewModel.onClickControl(command, sessionId)
    }


}