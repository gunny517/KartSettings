package jp.ceed.kart.settings.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.kart.settings.model.entity.Practice
import jp.ceed.kart.settings.model.entity.PracticeTrack

@Dao
interface PracticeDao {

    @Insert
    fun insert(practice: Practice)

    @Update
    fun update(practice: Practice)

    @Query("SELECT p.id, p.startDate, t.name FROM Practice p, Track t WHERE p.trackId = t.id")
    fun findAll(): List<PracticeTrack>

    @Query("SELECT p.id, p.startDate, t.name FROM Practice p, Track t WHERE p.trackId = t.id AND trackId = (:trackId)")
    fun findByTrackId(trackId: Int): List<PracticeTrack>
}