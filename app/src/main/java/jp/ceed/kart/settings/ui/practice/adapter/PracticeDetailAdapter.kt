package jp.ceed.kart.settings.ui.practice.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.model.dto.PracticeRowItem
import jp.ceed.kart.settings.ui.practice.PracticeRowView

class PracticeDetailAdapter(context: Context): RecyclerView.Adapter<PracticeDetailAdapter.ViewHolder>() {

    companion object {
        const val TYPE_DATA_ROW = 0
        const val TYPE_FOOTER = 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val viewDataBinding: ViewDataBinding? = DataBindingUtil.bind(itemView)
    }

    private var rowList: List<PracticeRowItem> = mutableListOf()

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.practice_detail_adapter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val practiceRowView = holder.itemView.findViewById<PracticeRowView>(R.id.practiceRowView)
        practiceRowView.setPracticeRowItem(rowList[position])
    }

    override fun getItemCount(): Int {
        return rowList.size
    }

    fun setRowList(_rowList: List<PracticeRowItem>){
        rowList = _rowList
    }


}