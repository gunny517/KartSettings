package jp.ceed.kart.settings.domain.repository

import android.content.Context
import jp.ceed.kart.settings.model.database.AppDatabase
import jp.ceed.kart.settings.model.dto.PracticeDetailAdapterItem
import jp.ceed.kart.settings.model.entity.Session
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class SessionRepository(val context: Context, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val sessionsDao = AppDatabase.getInstance(context).sessionDao()

    suspend fun insert(session: Session){
        withContext(dispatcher){
            sessionsDao.insert(session)
        }
    }

    suspend fun deleteBySessionId(sessionId: Int){
        withContext(dispatcher){
            sessionsDao.deleteById(sessionId)
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
        var isSamePractice = true
        withContext(dispatcher){
            session = sessionsDao.getLatestByPracticeId(practiceId)
            if(session == null){
                isSamePractice = false
                session = sessionsDao.getLatest()
            }
        }
        return Session.createCopyAsZeroId(session ?: Session(practiceId = practiceId) , practiceId, isSamePractice)
    }

    suspend fun getSessionList(practiceId: Int): List<Session> {
        var list: List<Session>
        withContext(dispatcher){
            list = sessionsDao.findAByPracticeId(practiceId)
        }
        return list
    }

    suspend fun saveSession(practiceRowList: List<PracticeDetailAdapterItem>, sessionId: Int, practiceId: Int){
        val session = Session.fromPracticeRowItemList(practiceRowList, sessionId, practiceId)
        withContext(dispatcher){
            sessionsDao.update(session)
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