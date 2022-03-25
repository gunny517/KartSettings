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
import jp.ceed.kart.settings.ui.practice.viewModel.PracticeListItemViewModel

class PracticeListAdapter(context: Context)
    : RecyclerView.Adapter<PracticeListAdapter.ViewHolder>() {

    class ViewHolder(val binding: PracticeListItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var itemList: List<PracticeListItemViewModel> = mutableListOf()

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: PracticeListItemBinding = DataBindingUtil.inflate(inflater, R.layout.practice_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.binding.practiceListItem = item
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemList(_itemList: List<PracticeListItemViewModel>){
        itemList = _itemList
    }

}