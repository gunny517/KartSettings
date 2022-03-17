package jp.ceed.kart.settings.domain.repository

import android.content.Context
import jp.ceed.kart.settings.model.SettingLabel
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.dto.PracticeRowItem
import jp.ceed.kart.settings.model.dto.SettingItem
import jp.ceed.kart.settings.model.entity.Session
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class SessionRepository(val context: Context, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val sessionsDao = AppDatabase.getInstance(context).sessionDao()

    suspend fun insert(session: Session){
        withContext(dispatcher){
            sessionsDao.insert(session)
        }
    }

    suspend fun getPracticeRowList(practiceId: Int): List<PracticeRowItem> {
        var practiceRowList: List<PracticeRowItem>
        withContext(dispatcher){
            val sessionList = sessionsDao.findAByPracticeId(practiceId)
            practiceRowList = createPracticeRowList(sessionList)
        }
        return practiceRowList
    }


    private fun createPracticeRowList(sessionList: List<Session>): List<PracticeRowItem>{
        val cls = Session::class.java
        val fields = cls.declaredFields
        val resultList: ArrayList<PracticeRowItem> = ArrayList()
        for(field in fields){
            field.isAccessible = true
            val annotation = field.getAnnotation(SettingLabel::class.java) ?: continue
            val label = context.getString(annotation.label)
            val index = annotation.index
            val name = field.name
            val list: ArrayList<SettingItem> = ArrayList()
            for(session in sessionList){
                val value = field.get(session)
                list.add(createSettingItem(session.id, name, value))
            }
            resultList.add(PracticeRowItem(index, label, list))
        }
        Collections.sort(resultList, PracticeRowComparator())
        return resultList
    }

    private fun createSettingItem(sessionId: Int, name: String, value: Any?): SettingItem{
        if(value == null){
            return SettingItem(sessionId, name, "")
        }
        return when(value){
            is Int -> {
                SettingItem(sessionId, name, value as Int)
            }
            is Float -> {
                SettingItem(sessionId, name, value as Float)
            }
            else -> {
                SettingItem(sessionId, name, value as String)
            }
        }
    }

    class PracticeRowComparator: Comparator<PracticeRowItem> {

        override fun compare(p0: PracticeRowItem?, p1: PracticeRowItem?): Int {
            val f = p0?.index ?: 0
            val r = p1?.index ?: 0
            return when {
                f < r -> {
                    -1
                }
                f > r -> {
                    1
                }
                else -> {
                    0
                }
            }
        }

    }
}