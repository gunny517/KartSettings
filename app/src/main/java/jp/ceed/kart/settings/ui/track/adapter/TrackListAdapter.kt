package jp.ceed.kart.settings.ui.track.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.TrackListItemBinding
import jp.ceed.kart.settings.model.entity.Track

class TrackListAdapter(context: Context, private val lifecycleOwner: LifecycleOwner, val onSaveCommand: (Track) -> Unit)
    : RecyclerView.Adapter<TrackListAdapter.ViewHolder>() {

    class ViewHolder(val binding: TrackListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val inflater = LayoutInflater.from(context)

    private var itemList: List<Track> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: TrackListItemBinding = DataBindingUtil.inflate(inflater, R.layout.track_list_item, parent, false)
        binding.lifecycleOwner = lifecycleOwner
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.track = itemList[position]
        holder.binding.trackListOperator = TrackListOperator(this, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemList(_itemList: List<Track>){
        itemList = _itemList
    }

    class TrackListOperator(private val adapter: TrackListAdapter, val track: Track) {

        var isEditable: MutableLiveData<Boolean> = MutableLiveData(false)

        fun onClickSave() {
            adapter.onSaveCommand(track)
            isEditable.value = false
        }

        fun onClickEdit(){
            isEditable.value = isEditable.value?.not()
        }
    }
}