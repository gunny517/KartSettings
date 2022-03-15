package jp.ceed.kart.settings.ui.track.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.TrackListItemBinding
import jp.ceed.kart.settings.model.entity.Track
import jp.ceed.kart.settings.BR

class TrackListAdapter(context: Context): RecyclerView.Adapter<TrackListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    private val inflater = LayoutInflater.from(context)

    private var itemList: List<Track> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TrackListItemBinding = DataBindingUtil.inflate(inflater, R.layout.track_list_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewDataBinding?.setVariable(BR.track, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemList(_itemList: List<Track>){
        itemList = _itemList
    }
}