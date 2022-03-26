package jp.ceed.kart.settings.domain.repository

import android.content.Context
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.entity.Practice
import jp.ceed.kart.settings.model.entity.PracticeTrack
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PracticeRepository(context: Context, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val practiceDao = AppDatabase.getInstance(context).practiceDao()

    private val sessionDao = AppDatabase.getInstance(context).sessionDao()

    suspend fun findAll(): List<PracticeTrack> {
        val list: List<PracticeTrack>
        withContext(dispatcher){
            list = practiceDao.findAll()
        }
        return list
    }

    suspend fun findPracticeTrackByPracticeId(id: Int): PracticeTrack {
        val practiceTrack: PracticeTrack
        withContext(dispatcher){
            practiceTrack = practiceDao.findPracticeTrackByPracticeId(id)
        }
        return practiceTrack
    }

    suspend fun save(practice: Practice){
        withContext(dispatcher){
            practiceDao.save(practice)
        }
    }

    suspend fun deleteById(id: Int){
        withContext(dispatcher){
            practiceDao.deleteById(id)
            sessionDao.deleteByPracticeId(id)
        }
    }

}