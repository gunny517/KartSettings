package jp.ceed.kart.settings.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.kart.settings.model.entity.Session

@Dao
interface SessionsDao {

    @Query("SELECT * FROM Session WHERE practiceId = (:practiceId)")
    fun findAByPracticeId(practiceId: Int): List<Session>

    @Insert
    fun insert(session: Session)

    @Update
    fun update(session: Session)

    @Query("SELECT * FROM Session ORDER BY id DESC LIMIT 1")
    fun getLatest(): Session?

    @Query("SELECT * FROM Session WHERE practiceId = (:practiceId) ORDER BY id DESC LIMIT 1")
    fun getLatestByPracticeId(practiceId: Int): Session?

    @Query("DELETE FROM Session WHERE id = (:id)")
    fun deleteById(id: Int)

    @Query("DELETE FROM Session WHERE practiceId = (:practiceId)")
    fun deleteByPracticeId(practiceId: Int)

}