package jp.ceed.kart.settings.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.kart.settings.domain.repository.*

@Module
@InstallIn(FragmentComponent::class)
object RepositoryModule {

    @Provides
    fun bindsPracticeRepository(@ApplicationContext context: Context): PracticeRepository {
        return PracticeRepository(context = context)
    }

    @Provides
    fun bindsTrackRepository(@ApplicationContext context: Context): TrackRepository {
        return TrackRepository(context = context)
    }

    @Provides
    fun bindsPracticeTrackRepository(@ApplicationContext context: Context): PracticeRepository {
        return PracticeRepository(context = context)
    }

    @Provides
    fun bindsSessionRepository(@ApplicationContext context: Context): SessionRepository {
        return SessionRepository(context = context)
    }

    @Provides
    fun bindsResourceRepository(@ApplicationContext context: Context): ResourceRepository {
        return ResourceRepository(context = context)
    }

    @Provides
    fun bindsFinalRadioRepository(@ApplicationContext context: Context): FinalRatioRepository {
        return FinalRatioRepository(context = context)
    }

}