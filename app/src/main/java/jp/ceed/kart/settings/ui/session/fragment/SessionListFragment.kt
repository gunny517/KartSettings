package jp.ceed.kart.settings.ui.session.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentSessionListBinding
import jp.ceed.kart.settings.ui.session.viewModel.SessionListFragmentViewModel

class SessionListFragment: Fragment() {

    private var _binding: FragmentSessionListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SessionListFragmentViewModel by viewModels(factoryProducer = ::factoryProducer)

    private val args: SessionListFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_session_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout(){

    }

    private fun factoryProducer(): SessionListFragmentViewModel.SessionListFragmentViewModelFactory {
        return SessionListFragmentViewModel.SessionListFragmentViewModelFactory(requireContext(), args.practiceId)
    }
}