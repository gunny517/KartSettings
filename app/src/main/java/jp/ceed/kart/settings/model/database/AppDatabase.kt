package jp.ceed.kart.settings.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import jp.ceed.kart.settings.model.dao.PracticeDao
import jp.ceed.kart.settings.model.dao.SessionsDao
import jp.ceed.kart.settings.model.dao.TrackDao
import jp.ceed.kart.settings.model.entity.Practice
import jp.ceed.kart.settings.model.entity.Session
import jp.ceed.kart.settings.model.entity.Track

@Database(entities = [Track::class, Practice::class, Session::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun trackDao(): TrackDao

    abstract fun practiceDao(): PracticeDao

    abstract fun sessionDao(): SessionsDao

    companion object {

        private const val DB_NAME = "KART_SETTINGS_DB"

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