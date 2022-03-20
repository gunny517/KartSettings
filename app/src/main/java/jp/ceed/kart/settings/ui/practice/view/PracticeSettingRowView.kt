package jp.ceed.kart.settings.ui.practice.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import jp.ceed.kart.settings.BR
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.SettingItemViewBinding
import jp.ceed.kart.settings.databinding.SettingLabelViewBinding
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.model.dto.SettingItem

class PracticeSettingRowView(context: Context, attr: AttributeSet): LinearLayout(context, attr) {

    private val inflater = LayoutInflater.from(context)

    private var practiceRowItem: MutableLiveData<PracticeDetailAdapterItem.PracticeRowItem> = MutableLiveData()

    private var lifecycleOwner: LifecycleOwner? = null


    fun setPracticeRowItem(_practiceRowItem: PracticeDetailAdapterItem.PracticeRowItem?){
        _practiceRowItem?.let {
            practiceRowItem.value = it
            resetView()
        }
    }

    fun setLifecycleOwner(_lifecycleOwner: LifecycleOwner){
        lifecycleOwner = _lifecycleOwner
    }

    fun toggleEditable(sessionId: Int){
        practiceRowItem.value?.let {
            for(settingItem in it.values){
                if(settingItem.sessionId == sessionId){
                    settingItem.isEditable = settingItem.isEditable.not()
                    break
                }
            }
        }
    }

    private fun resetView(){
        practiceRowItem.value?.let {
            removeAllViews()
            setLabelView(it.label)
            setValueViews(it.values)
        }
    }

    private fun setLabelView(label: String){
        val binding: SettingLabelViewBinding = DataBindingUtil.inflate(inflater, R.layout.setting_label_view, this, true)
        binding.label = label
    }

    private fun setValueViews(settingItems: List<SettingItem>){
        for(entry in settingItems){
            addValueView(entry)
        }
    }

    private fun addValueView(settingItem: SettingItem){
        val binding: SettingItemViewBinding = DataBindingUtil.inflate(inflater, R.layout.setting_item_view, this, true)
        binding.lifecycleOwner = lifecycleOwner
        binding.settingItem = settingItem
    }

}