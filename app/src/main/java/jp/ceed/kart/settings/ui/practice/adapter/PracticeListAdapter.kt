package jp.ceed.kart.settings.ui.practice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.PracticeListItemBinding
import jp.ceed.kart.settings.model.entity.PracticeTrack
import jp.ceed.kart.settings.BR

class PracticeListAdapter(context: Context): RecyclerView.Adapter<PracticeListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    private var itemList: List<PracticeTrack> = mutableListOf()

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PracticeListItemBinding = DataBindingUtil.inflate(inflater, R.layout.practice_list_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewDataBinding?.setVariable(BR.practiceTrack, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemList(_itemList: List<PracticeTrack>){
        itemList = _itemList
    }

}