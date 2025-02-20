package com.goldcompany.koreabike.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AddressEntity::class], version = 1, exportSchema = false)
abstract class KBikeDatabase : RoomDatabase() {
    abstract fun addressDAO(): AddressDAO

    companion object {
        @Volatile private var instance: KBikeDatabase? = null
        
        fun getInstance(context: Context): KBikeDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): KBikeDatabase {
            return Room.databaseBuilder(context, KBikeDatabase::class.java, "koreabike_database")
                .build()
        }
    }
}
