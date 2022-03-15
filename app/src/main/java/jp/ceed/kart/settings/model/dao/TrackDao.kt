package jp.ceed.kart.settings.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import jp.ceed.kart.settings.model.entity.Track

@Dao
interface TrackDao {

    @Query("SELECT * FROM Track")
    fun findAll(): List<Track>

    @Insert
    fun insert(track: Track)

    @Update
    fun update(track: Track)

}