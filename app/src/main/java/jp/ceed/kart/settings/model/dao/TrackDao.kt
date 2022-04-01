package jp.ceed.kart.settings.model.dao

import androidx.room.*
import jp.ceed.kart.settings.model.entity.Track

@Dao
interface TrackDao {

    @Query("SELECT * FROM Track")
    fun findAll(): List<Track>

    @Query("SELECT * FROM Track WHERE id = (:id)")
    fun findById(id: Int): Track

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(track: Track): Long

    @Update
    fun update(track: Track)

    @Transaction
    fun save(track: Track){
        if(insertIgnore(track) == -1L){
            update(track)
        }
    }

    @Query("DELETE FROM Track WHERE id = (:id)")
    fun deleteById(id: Int)
}