package jp.ceed.kart.settings.domain.repository

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceRepository @Inject constructor(
    @ApplicationContext val context: Context,
) {

    fun getString(@StringRes res: Int): String {
        return context.getString(res)
    }
}