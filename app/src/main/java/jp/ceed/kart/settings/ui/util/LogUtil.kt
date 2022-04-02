package jp.ceed.kart.settings.ui.util

import android.util.Log
import jp.ceed.kart.settings.BuildConfig

class LogUtil {

    companion object {

        private const val TAG = "KartSettingsLog"

        fun d(msg: String){
            if(BuildConfig.DEBUG){
                Log.d(TAG, msg)
            }
        }
    }
}