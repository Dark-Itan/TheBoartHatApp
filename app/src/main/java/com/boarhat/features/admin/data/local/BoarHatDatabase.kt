package com.boarhat.features.admin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PastelEntity::class, PedidoEntity::class],
    version = 2, // 1. SUBIMOS LA VERSIÓN: Room sabrá que el diseño cambió
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
                )
                    // 2. AGREGAMOS ESTA LÍNEA: Si la versión no coincide,
                    // borra la vieja y crea la nueva automáticamente.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}