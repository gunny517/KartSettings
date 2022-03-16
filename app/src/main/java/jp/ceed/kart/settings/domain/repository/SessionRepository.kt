package jp.ceed.kart.settings.domain.repository

import android.content.Context
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.entity.Session
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionRepository(context: Context, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val sessionsDao = AppDatabase.getInstance(context).sessionDao()

    suspend fun findByPracticeId(practiceId: Int): List<Session> {
        val list: List<Session>
        withContext(dispatcher){
            list = sessionsDao.findAByPracticeId(practiceId)
        }
        return list
    }


}