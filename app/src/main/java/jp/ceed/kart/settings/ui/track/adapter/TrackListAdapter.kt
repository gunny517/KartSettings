package jp.ceed.kart.settings.ui.track.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.TrackListItemBinding
import jp.ceed.kart.settings.ui.track.viewModel.TrackListItemViewModel

class TrackListAdapter(context: Context, private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<TrackListAdapter.ViewHolder>() {

    class ViewHolder(val binding: TrackListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val inflater = LayoutInflater.from(context)

    private var viewModelList: List<TrackListItemViewModel> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TrackListItemBinding = DataBindingUtil.inflate(inflater, R.layout.track_list_item, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.viewModel = viewModelList[position]
    }

    override fun getItemCount(): Int {
        return viewModelList.size
    }

    fun setItemList(viewModelList: List<TrackListItemViewModel>){
        this.viewModelList = viewModelList
    }
}