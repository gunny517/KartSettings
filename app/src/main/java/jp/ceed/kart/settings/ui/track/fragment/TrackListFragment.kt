package jp.ceed.kart.settings.ui.track.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.FragmentTackListBinding
import jp.ceed.kart.settings.ui.common.fragment.DeleteConfirmDialogFragment
import jp.ceed.kart.settings.ui.extension.getApplication
import jp.ceed.kart.settings.ui.track.adapter.TrackListAdapter
import jp.ceed.kart.settings.ui.track.viewModel.TrackListFragmentViewModel

class TrackListFragment: Fragment() {

    private var _binding: FragmentTackListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: TrackListFragmentViewModel by viewModels(factoryProducer = ::factoryProvider)

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
        init()
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
            trackId?.let { trackId ->
                showEditDialog(trackId)
            }
        }
        viewModel.deleteEvent.observe(viewLifecycleOwner){
            val trackId = it.getContentIfNotHandled()
            trackId?.let { trackId ->
                onClickItemDelete(trackId)
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
        EditTrackDialogFragment.newInstance(trackId, ::onEdit).show(childFragmentManager, EditTrackDialogFragment.TAG)
    }

    private fun onEdit(){
        viewModel.loadTrackList()
    }

    private fun factoryProvider(): TrackListFragmentViewModel.Factory {
        return TrackListFragmentViewModel.Factory(getApplication(), activity as ViewModelStoreOwner)
    }

}