package jp.ceed.kart.settings.ui.settings.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentSettingsBinding
import jp.ceed.kart.settings.ui.settings.viewModel.SettingsFragmentViewModel

class SettingsFragment: Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    val binding get() = _binding!!

    val viewModel: SettingsFragmentViewModel by viewModels()

    lateinit var composeView: ComposeView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        composeView = ComposeView(requireContext()).apply {
            setContent {
                MainContent()
            }
        }
        return composeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.versionName.observe(viewLifecycleOwner){
            composeView.setContent {
                AppVersion(it)
            }
        }
    }

    @Composable
    fun MainContent(){

    }

    @Composable
    fun AppVersion(version: String){
        Row(modifier = Modifier.padding(20.dp)) {
            DefaultText(text = stringResource(R.string.label_settings_version_name))
            DefaultText(text = version,
                modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp))
        }
    }

    @Composable
    fun DefaultText(text: String, modifier: Modifier = Modifier){
        Text(text = text,
            modifier = modifier,
            color = Color.White,
            fontSize = 16.sp)
    }

    @Preview
    @Composable
    fun Preview(){
        AppVersion(version = "1.0.0")
    }
}