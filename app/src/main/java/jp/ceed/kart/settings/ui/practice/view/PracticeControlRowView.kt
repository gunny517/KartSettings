package jp.ceed.kart.settings.ui.practice.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import jp.ceed.kart.settings.BR
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.ControlItemViewBinding
import jp.ceed.kart.settings.databinding.SettingLabelViewBinding
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem

class PracticeControlRowView(context: Context, attr: AttributeSet): LinearLayout(context, attr) {

    private val inflater = LayoutInflater.from(context)

    private var controlItem: PracticeDetailAdapterItem.PracticeControlItem? = null

    fun setControlItem(_controlItem: PracticeDetailAdapterItem.PracticeControlItem?){
        controlItem = _controlItem
        resetView()
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
            for(controlItem in ctrlItem.controlItems){
                val binding: ControlItemViewBinding = DataBindingUtil.inflate(inflater, R.layout.control_item_view, this, false)
                binding.viewModel = controlItem
                addView(binding.root)
            }
        }
    }
}