package jp.ceed.kart.settings.ui.session.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.BR
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.SettingAdapterItemBinding

class SessionHeaderAdapter(context: Context): RecyclerView.Adapter<SessionHeaderAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    private var itemList: List<String> = mutableListOf()

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SettingAdapterItemBinding = DataBindingUtil.inflate(inflater, R.layout.session_header_item, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewDataBinding?.setVariable(BR.label, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemList(_itemList: List<String>){
        itemList = _itemList
    }
}