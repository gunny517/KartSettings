package jp.ceed.kart.settings.domain.repository

import android.content.Context
import android.content.SharedPreferences

class AppSettingsRepository(val context: Context) {

    companion object {
        const val PREFS_NAME = "AppSettingsRepository"
        const val KEY_COPY_PREV_SESSION_VALUE_WHEN_CREATE = "COPY_PREV_SESSION_VALUE_WHEN_CREATE"
    }

    /**
     * セッション作成時に前セッションの値をコピーするかどうかの真偽値を返す。
     * 未選択の場合はNullを返す。
     */
    fun getSessionValueSetting(): Boolean? {
        val prefs = sharedPreference()
        return if(prefs.contains(KEY_COPY_PREV_SESSION_VALUE_WHEN_CREATE)){
            prefs.getBoolean(KEY_COPY_PREV_SESSION_VALUE_WHEN_CREATE, false)
        }else {
            null
        }
    }

    private fun sharedPreference(): SharedPreferences{
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

}
