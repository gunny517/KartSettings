package jp.ceed.kart.settings.ui.practice

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import jp.ceed.kart.settings.BR
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.SettingItemViewBinding
import jp.ceed.kart.settings.databinding.SettingLabelViewBinding
import jp.ceed.kart.settings.model.dto.PracticeRowItem
import jp.ceed.kart.settings.model.dto.SettingItem

class PracticeRowView(context: Context, attr: AttributeSet): LinearLayout(context, attr) {

    private val inflater = LayoutInflater.from(context)

    private var practiceRowItem: PracticeRowItem? = null


    fun setPracticeRowItem(_practiceRowItem: PracticeRowItem){
        practiceRowItem = _practiceRowItem
        resetView()
    }

    private fun resetView(){
        practiceRowItem?.let {
            removeAllViews()
            setLabelView(it.label)
            setValueViews(it.values)
        }
    }

    private fun setLabelView(label: String){
        val binding: SettingLabelViewBinding = DataBindingUtil.inflate(inflater, R.layout.setting_label_view, this, true)
        binding.setVariable(BR.label, label)
    }

    private fun setValueViews(settingItems: List<SettingItem>){
        for(entry in settingItems){
            addValueView(entry)
        }
    }

    private fun addValueView(settingItem: SettingItem){
        val binding: SettingItemViewBinding = DataBindingUtil.inflate(inflater, R.layout.setting_item_view, this, true)
        binding.setVariable(BR.settingItem, settingItem)
    }

}