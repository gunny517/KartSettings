package jp.ceed.kart.settings.ui.practice.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import jp.ceed.kart.settings.BR
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.SettingLabelViewBinding
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.ui.common.RowControlListener

class PracticeControlRowView(context: Context, attr: AttributeSet): LinearLayout(context, attr) {

    private val inflater = LayoutInflater.from(context)

    private var controlItem: PracticeDetailAdapterItem.PracticeControlItem? = null

    private var rowControlListener: RowControlListener? = null

    fun setControlItem(_controlItem: PracticeDetailAdapterItem.PracticeControlItem?){
        controlItem = _controlItem
        resetView()
    }

    fun setRowControlListener(_rowControlListener: RowControlListener?){
        rowControlListener = _rowControlListener
    }

    private fun resetView(){
        removeAllViews()
        setLabelView()
        setControlViews()
    }

    private fun setLabelView(){
        val binding: SettingLabelViewBinding = DataBindingUtil.inflate(inflater, R.layout.setting_label_view, this, true)
        binding.setVariable(BR.label, "")
    }

    private fun setControlViews(){
        controlItem?.let { ctrlItem ->
            for(sessionId in ctrlItem.sessionIdList){
                val view = inflater.inflate(R.layout.control_item_view, this, false)
                view.findViewById<TextView>(R.id.editButton).setOnClickListener{
                    rowControlListener?.let {
                        it.onClickControl(RowControlListener.RowControlCommand.EDIT, sessionId)
                    }
                }
                view.findViewById<TextView>(R.id.saveButton).setOnClickListener{
                    rowControlListener?.let {
                        it.onClickControl(RowControlListener.RowControlCommand.SAVE, sessionId)
                    }
                }
                addView(view)
            }
        }
    }
}