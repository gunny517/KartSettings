package jp.ceed.kart.settings.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.ceed.kart.settings.model.TrackDao
import jp.ceed.kart.settings.model.entity.Track

@Database(entities = [Track::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun trackDao(): TrackDao

    companion object {

        const val DB_NAME = "KART_SETTINGS_DB"

        @Volatile var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = tempInstance
                tempInstance
            }
        }
    }

}