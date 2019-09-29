package com.threedee.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.threedee.cache.dao.CachedBufferooDao
import com.threedee.cache.model.CachedBufferoo
import javax.inject.Inject

@Database(entities = arrayOf(CachedBufferoo::class), version = 1)
abstract class ThreeDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedBufferooDao(): CachedBufferooDao

    private var INSTANCE: ThreeDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): ThreeDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ThreeDatabase::class.java, "bufferoos.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }

}