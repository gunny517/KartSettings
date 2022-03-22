package jp.ceed.kart.settings.ui.practice.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import jp.ceed.kart.settings.BR
import jp.ceed.kart.settings.R
import jp.ceed.kart.settings.databinding.ControlItemViewBinding
import jp.ceed.kart.settings.databinding.SettingLabelViewBinding
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.ui.common.RowControlListener
import jp.ceed.kart.settings.ui.practice.viewModel.PracticeControlItemViewModel

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
            for(controlItem in ctrlItem.controlItems){
                val binding: ControlItemViewBinding = DataBindingUtil.inflate(inflater, R.layout.control_item_view, this, false)
                val viewModelStoreOwner = findViewTreeViewModelStoreOwner()
                viewModelStoreOwner?.let {
                    val factory = PracticeControlItemViewModel.Factory(controlItem.sessionId, rowControlListener)
                    val viewModel = ViewModelProvider(it, factory).get(PracticeControlItemViewModel::class.java)
                    binding.viewModel = viewModel
                }
                addView(binding.root)
            }
        }
    }
}