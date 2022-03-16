package jp.ceed.kart.settings.domain.repository

import android.content.Context
import jp.ceed.kart.settings.model.SettingLabel
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.dto.SessionListItem
import jp.ceed.kart.settings.model.entity.Session
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SessionRepository(val context: Context, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val sessionsDao = AppDatabase.getInstance(context).sessionDao()

    suspend fun getSessionContentByPracticeId(practiceId: Int): List<SessionListItem.SessionContent> {
        val list: ArrayList<SessionListItem.SessionContent> = ArrayList()
        withContext(dispatcher){
            val sessions = sessionsDao.findAByPracticeId(practiceId)
            for(entry in sessions){
                list.add(SessionListItem.SessionContent(entry))
            }
        }
        return list
    }

    fun getSessionHeader(): SessionListItem.SessionHeader{
        val list: ArrayList<String> = ArrayList()
        val cls = Session::class.java
        val fields = cls.declaredFields
        for(field in fields){
            val annotation = field.getAnnotation(SettingLabel::class.java)
            annotation?.let {
                list.add(context.getString(annotation.name))
            }
        }
        return SessionListItem.SessionHeader(list)
    }


}