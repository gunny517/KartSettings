package jp.ceed.kart.settings.ui.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

class UiUtil(val context: Context) {

    fun hideKeyboard(root: View) {
        val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(root.windowToken, 0)
    }

    fun toYmdString(date: Date): String{
        return dateFormat.format(date)
    }

    fun fromYmdString(ymd: String): Calendar{
        val calendar = Calendar.getInstance()
        val date = dateFormat.parse(ymd)
        date?.let {
            calendar.time = it
        }
        return  calendar
    }

    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.JAPANESE)
    }
}