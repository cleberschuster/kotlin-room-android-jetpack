package com.cleberschuster.roomdatabase.jetpack.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cleberschuster.roomdatabase.jetpack.data.db.dao.SubscriberDAO
import com.cleberschuster.roomdatabase.jetpack.data.db.entity.SubscriberEntity

@Database(
    version = 1,
    entities = [SubscriberEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subscriberDAO(): SubscriberDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                }

                return instance
            }
        }
    }
}