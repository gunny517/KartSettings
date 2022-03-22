package jp.ceed.kart.settings.ui.practice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.PracticeDetailControlItemBinding
import jp.ceed.kart.settings.databinding.PracticeDetailSettingItemBinding
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem

class PracticeDetailAdapter(context: Context, private val lifecycleOwner: LifecycleOwner)
    : RecyclerView.Adapter<PracticeDetailAdapter.ViewHolder>() {

    companion object {
        const val TYPE_DATA_ROW = 0
        const val TYPE_CONTROL = 1
    }

    sealed class ViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
        class ControlViewHolder(val binding: PracticeDetailControlItemBinding): ViewHolder(binding)
        class SettingItemViewHolder(val binding: PracticeDetailSettingItemBinding): ViewHolder(binding)
    }

    private var itemList: MutableLiveData<List<PracticeDetailAdapterItem>> = MutableLiveData()

    private val inflater = LayoutInflater.from(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(TYPE_CONTROL == viewType){
            val binding: PracticeDetailControlItemBinding = DataBindingUtil.inflate(inflater, R.layout.practice_detail_control_item, parent, false)
            binding.lifecycleOwner = lifecycleOwner
            ViewHolder.ControlViewHolder(binding)
        }else{
            val binding: PracticeDetailSettingItemBinding = DataBindingUtil.inflate(inflater, R.layout.practice_detail_setting_item, parent, false)
            binding.lifecycleOwner = lifecycleOwner
            binding.practiceRowView.setLifecycleOwner(lifecycleOwner)
            ViewHolder.SettingItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder.ControlViewHolder -> {
                holder.binding.controlItem = getItem(position) as PracticeDetailAdapterItem.PracticeControlItem
            }
            is ViewHolder.SettingItemViewHolder -> {
                holder.binding.practiceRowItem = getItem(position) as PracticeDetailAdapterItem.PracticeRowItem
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.value?.size ?: 0
    }

    fun setRowList(_rowList: List<PracticeDetailAdapterItem>){
        itemList.value = _rowList
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is PracticeDetailAdapterItem.PracticeControlItem -> {
               TYPE_CONTROL
            }else -> {
                TYPE_DATA_ROW
            }
        }
    }

    private fun getItem(position: Int): PracticeDetailAdapterItem? {
        return itemList.value?.get(position)
    }

}