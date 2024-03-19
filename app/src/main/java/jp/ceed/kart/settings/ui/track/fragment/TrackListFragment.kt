package jp.ceed.kart.settings.ui.track.fragment

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
import jp.ceed.kart.settings.databinding.FragmentTackListBinding
import jp.ceed.kart.settings.ui.common.fragment.DeleteConfirmDialogFragment
import jp.ceed.kart.settings.ui.track.adapter.TrackListAdapter
import jp.ceed.kart.settings.ui.track.viewModel.TrackListFragmentViewModel

@AndroidEntryPoint
class TrackListFragment: Fragment() {

    private var _binding: FragmentTackListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TrackListFragmentViewModel by viewModels()

    private lateinit var adapter: TrackListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tack_list, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewModelStoreOwner = this
        init()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadTrackList()
    }

    private fun init(){
        adapter = TrackListAdapter(requireContext(), viewLifecycleOwner)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        viewModel.trackList.observe(viewLifecycleOwner){
            adapter.setItemList(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.editEvent.observe(viewLifecycleOwner){
            val trackId = it.getContentIfNotHandled()
            trackId?.let {
                showEditDialog(it)
            }
        }
        viewModel.deleteEvent.observe(viewLifecycleOwner){
            val trackId = it.getContentIfNotHandled()
            trackId?.let {
                onClickItemDelete(it)
            }
        }
    }

    private fun onClickItemDelete(trackId: Int){
        val dialog = DeleteConfirmDialogFragment.newInstance(R.string.msg_delete_track)
        dialog.setOnDialogClickListener{
                _, button -> onClickDeleteDialogButton(trackId, button)
        }
        dialog.show(childFragmentManager, dialog.tag(this.javaClass))
    }

    private fun onClickDeleteDialogButton(trackId: Int, button: Int){
        when(button){
            DialogInterface.BUTTON_POSITIVE -> {
                viewModel.deleteById(trackId)
            } else -> {}
        }
    }

    private fun showEditDialog(trackId: Int){
        EditTrackDialogFragment.newInstance(trackId)
            .show(childFragmentManager, EditTrackDialogFragment.TAG)
    }

}