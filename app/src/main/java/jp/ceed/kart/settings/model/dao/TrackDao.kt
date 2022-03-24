package jp.ceed.kart.settings.model.dao

import androidx.room.*
import jp.ceed.kart.settings.model.entity.Track

@Dao
interface TrackDao {

    @Query("SELECT * FROM Track")
    fun findAll(): List<Track>

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
}