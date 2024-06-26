package jp.ceed.kart.settings.domain.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.ceed.kart.settings.di.IoDispatcher
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.entity.Track
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackRepository @Inject constructor (
    @ApplicationContext context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val trackDao = AppDatabase.getInstance(context).trackDao()

    suspend fun getTrackList(): List<Track>{
        val list: List<Track>
        withContext(dispatcher){
            list = trackDao.findAll()
        }
        return list
    }

    suspend fun findById(id: Int): Track{
        val track: Track
        withContext(dispatcher){
            track = trackDao.findById(id)
        }
        return track
    }

    suspend fun save(track: Track) {
        withContext(dispatcher){
            trackDao.save(track)
        }
    }

    suspend fun update(track: Track){
        withContext(dispatcher){
            trackDao.update(track)
        }
    }

    suspend fun deleteById(id: Int){
        withContext(dispatcher){
            trackDao.deleteById(id)
        }
    }

}