package jp.ceed.kart.settings.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.ceed.kart.settings.ui.util.UiUtil

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    fun bindsUiUtil(@ApplicationContext context: Context): UiUtil {
        return UiUtil(context = context)
    }
}