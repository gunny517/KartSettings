package jp.ceed.kart.settings.ui.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentSettingsBinding
import jp.ceed.kart.settings.ui.settings.viewModel.SettingsFragmentViewModel

class SettingsFragment: Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    val binding get() = _binding!!

    val viewModel: SettingsFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }
}