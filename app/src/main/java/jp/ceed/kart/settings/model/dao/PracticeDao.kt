package jp.ceed.kart.settings.model.dao

import androidx.room.*
import jp.ceed.kart.settings.model.entity.Practice
import jp.ceed.kart.settings.model.entity.PracticeTrack

@Dao
interface PracticeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(practice: Practice): Long

    @Update
    fun update(practice: Practice)

    @Transaction
    fun save(practice: Practice){
        if(insertIgnore(practice) == -1L){
            update(practice)
        }
    }

    @Query("SELECT p.id, p.startDate, p.description, t.name FROM Practice p, Track t WHERE p.trackId = t.id ORDER BY p.startDate DESC")
    fun findAll(): List<PracticeTrack>

    @Query("SELECT p.id, p.startDate, p.description, t.name FROM Practice p, Track t WHERE p.trackId = t.id AND trackId = (:trackId)")
    fun findByTrackId(trackId: Int): List<PracticeTrack>

    @Query("DELETE FROM Practice WHERE id = (:id)")
    fun deleteById(id: Int)

}