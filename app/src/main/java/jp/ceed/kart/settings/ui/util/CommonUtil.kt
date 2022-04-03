package jp.ceed.kart.settings.ui.util

import java.util.*

class CommonUtil {

    fun getOneHourPastTime(startTime: String): String{
        val cal = Calendar.getInstance()
        val hm = startTime.split(":")
        val hour = hm[0]
        val minutes = hm[1]
        cal.set(Calendar.HOUR_OF_DAY, hour.toInt() + 1)
        val hh: String = cal.get(Calendar.HOUR_OF_DAY).toString()
        return "${hh}:${minutes}"
    }

    fun createRandomKey(): String {
        return Random().nextInt().toString()
    }
}