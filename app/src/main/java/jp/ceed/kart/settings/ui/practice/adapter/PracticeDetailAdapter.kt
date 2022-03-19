package jp.ceed.kart.settings.ui.practice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.BR
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.PracticeDetailControlItemBinding
import jp.ceed.kart.settings.databinding.PracticeDetailSettingItemBinding
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.ui.common.RowControlListener

class PracticeDetailAdapter(context: Context, private val rowControlListener: RowControlListener)
    : RecyclerView.Adapter<PracticeDetailAdapter.ViewHolder>() {

    companion object {
        const val TYPE_DATA_ROW = 0
        const val TYPE_CONTROL = 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    private var rowList: List<PracticeDetailAdapterItem> = mutableListOf()

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(TYPE_CONTROL == viewType){
            val binding: PracticeDetailControlItemBinding = DataBindingUtil.inflate(inflater, R.layout.practice_detail_control_item, parent, false)
            ViewHolder(binding.root)
        }else{
            val binding: PracticeDetailSettingItemBinding = DataBindingUtil.inflate(inflater, R.layout.practice_detail_setting_item, parent, false)
            ViewHolder(binding.root)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(TYPE_CONTROL == getItemViewType(position)){
            holder.viewDataBinding?.setVariable(BR.controlItem, rowList[position])
            holder.viewDataBinding?.setVariable(BR.rowControlListener, rowControlListener)
        }else {
            holder.viewDataBinding?.setVariable(BR.practiceRowItem, rowList[position])
        }
    }

    override fun getItemCount(): Int {
        return rowList.size
    }

    fun setRowList(_rowList: List<PracticeDetailAdapterItem>){
        rowList = _rowList
    }

    override fun getItemViewType(position: Int): Int {
        return when(rowList[position]){
            is PracticeDetailAdapterItem.PracticeControlItem -> {
               TYPE_CONTROL
            }else -> {
                TYPE_DATA_ROW
            }
        }
    }

}