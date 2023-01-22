package com.goldcompany.koreabike.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AddressEntity::class], version = 1, exportSchema = false)
abstract class KBikeDatabase : RoomDatabase() {
    abstract fun addressDAO(): AddressDAO

    companion object {
        fun getInstance(context: Context): KBikeDatabase {
            return buildDatabase(context)
        }

        private fun buildDatabase(context: Context): KBikeDatabase {
            return Room.databaseBuilder(context, KBikeDatabase::class.java, "koreabike_database")
                .build()
        }
    }
}