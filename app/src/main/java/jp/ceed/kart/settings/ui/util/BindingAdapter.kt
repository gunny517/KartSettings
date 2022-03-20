package jp.ceed.kart.settings.ui.util

import androidx.databinding.BindingAdapter
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.ui.common.RowControlListener
import jp.ceed.kart.settings.ui.practice.view.PracticeControlRowView
import jp.ceed.kart.settings.ui.practice.view.PracticeSettingRowView

object BindingAdapter {

    @BindingAdapter("practiceRowItem")
    @JvmStatic
    fun setPracticeRowItem(view: PracticeSettingRowView, practiceRowItem: PracticeDetailAdapterItem.PracticeRowItem?){
        view.setPracticeRowItem(practiceRowItem)
    }

    @BindingAdapter("controlItem")
    @JvmStatic
    fun setControlItem(view: PracticeControlRowView, controlItem: PracticeDetailAdapterItem.PracticeControlItem?){
        view.setControlItem(controlItem)
    }

    @BindingAdapter("rowControlListener")
    @JvmStatic
    fun setRowControlListener(view: PracticeControlRowView, listener: RowControlListener?){
        view.setRowControlListener(listener)
    }
}