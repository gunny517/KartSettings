package jp.ceed.kart.settings.domain.repository

import android.content.Context
import jp.ceed.kart.settings.model.SettingLabel
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
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

    /**
     * 新しいセッションデータ作成用のエンティティを生成して返す。
     * 指定したプラクティスIDと同じセッションがあればその最終値を、無い場合はすべてのセッションの最終地をコピーする。
     * それもない場合はデフォルト値のエンティティを生成する。
     * IDは常に０が代入される。
     */
    suspend fun getNewEntityForInsert(practiceId: Int): Session{
        var session: Session?
        withContext(dispatcher){
            session = sessionsDao.getLatestByPracticeId(practiceId)
            if(session == null){
                session = sessionsDao.getLatest()
            }
        }
        return Session.createCopyAsZeroId(session ?: Session(practiceId = practiceId) , practiceId)
    }

    suspend fun getPracticeRowList(practiceId: Int): List<PracticeDetailAdapterItem> {
        val practiceRowList: ArrayList<PracticeDetailAdapterItem> = ArrayList()
        withContext(dispatcher){
            val sessionList = sessionsDao.findAByPracticeId(practiceId)
            practiceRowList.add(createControlItem(sessionList))
            practiceRowList.addAll(createPracticeRowList(sessionList))
        }
        return practiceRowList
    }

    suspend fun saveSession(practiceRowList: List<PracticeDetailAdapterItem>, sessionId: Int, practiceId: Int){
        val session = Session.fromPracticeRowItemList(practiceRowList, sessionId, practiceId)
        withContext(dispatcher){
            sessionsDao.update(session)
        }
    }

    private fun createControlItem(sessionList: List<Session>): PracticeDetailAdapterItem{
        val list: ArrayList<Int> = ArrayList()
        for(session in sessionList){
            list.add(session.id)
        }
        return PracticeDetailAdapterItem.PracticeControlItem(list)
    }


    private fun createPracticeRowList(sessionList: List<Session>): List<PracticeDetailAdapterItem.PracticeRowItem>{
        val cls = Session::class.java
        val fields = cls.declaredFields
        val resultList: ArrayList<PracticeDetailAdapterItem.PracticeRowItem> = ArrayList()
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
            resultList.add(PracticeDetailAdapterItem.PracticeRowItem(index, label, list))
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

    class PracticeRowComparator: Comparator<PracticeDetailAdapterItem.PracticeRowItem> {

        override fun compare(p0: PracticeDetailAdapterItem.PracticeRowItem?, p1: PracticeDetailAdapterItem.PracticeRowItem?): Int {
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