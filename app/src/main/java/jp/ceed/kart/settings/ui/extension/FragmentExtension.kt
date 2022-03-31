package jp.ceed.kart.settings.ui.extension

import android.app.Application
import androidx.fragment.app.Fragment


fun Fragment.getApplication(): Application {
    when(val context = requireContext().applicationContext){
        is Application -> {
            return context
        }
        else ->{
            throw IllegalStateException("unKnown.")
        }
    }
}