package jp.ceed.kart.settings.ui.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class UiUtil(val context: Context) {

    fun hideKeyboard(root: View) {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(root.windowToken, 0)
    }
}