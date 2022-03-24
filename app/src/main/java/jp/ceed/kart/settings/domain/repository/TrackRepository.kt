package jp.ceed.kart.settings.domain.repository

import android.content.Context
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.entity.Track
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrackRepository(context: Context, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val trackDao = AppDatabase.getInstance(context).trackDao()

    suspend fun getTrackList(): List<Track>{
        val list: List<Track>
        withContext(dispatcher){
            list = trackDao.findAll()
        }
        return list
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

}