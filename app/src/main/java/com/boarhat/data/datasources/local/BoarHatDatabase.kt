package com.boarhat.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PastelEntity::class, PedidoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BoarHatDatabase : RoomDatabase() {
    abstract fun pastelDao(): PastelDao
    abstract fun pedidoDao(): PedidoDao

    companion object {
        @Volatile
        private var INSTANCE: BoarHatDatabase? = null

        fun getDatabase(context: Context): BoarHatDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BoarHatDatabase::class.java,
                    "boarhat_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}