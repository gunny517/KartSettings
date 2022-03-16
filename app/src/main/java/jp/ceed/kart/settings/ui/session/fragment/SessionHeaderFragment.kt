package jp.ceed.kart.settings.ui.session.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentSessionHeaderBinding
import jp.ceed.kart.settings.model.dto.SessionListItem
import jp.ceed.kart.settings.ui.session.adapter.SessionHeaderAdapter
import jp.ceed.kart.settings.ui.session.viewModel.SessionHeaderFragmentViewModel

class SessionHeaderFragment: Fragment() {

    private val viewModel: SessionHeaderFragmentViewModel by viewModels()

    private var _binding: FragmentSessionHeaderBinding? = null

    val binding get() = _binding!!

    companion object {
        fun newInstance(sessionHeader: SessionListItem.SessionHeader){

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_session_header, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){
        val adapter = SessionHeaderAdapter(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.labelList.observe(viewLifecycleOwner){
            adapter.setItemList(it)
            adapter.notifyDataSetChanged()
        }
    }
}