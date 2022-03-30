package jp.ceed.kart.settings.ui.data.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentFinalRatioBinding
import jp.ceed.kart.settings.domain.repository.FinalRatioRepository
import jp.ceed.kart.settings.ui.data.adapter.FinalRatioAdapter
import jp.ceed.kart.settings.ui.data.viewModel.FinalRatioViewModel

class FinalRatioFragment: Fragment() {

    private var _binding: FragmentFinalRatioBinding? = null

    val binding get() = _binding!!

    val viewModel: FinalRatioViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_final_ratio, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        viewModel.finalRatioList.observe(viewLifecycleOwner){
            val adapter = FinalRatioAdapter(requireContext(), it)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), viewModel.colSize.value ?: 0)
            binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), GridLayoutManager.VERTICAL))
        }
    }
}