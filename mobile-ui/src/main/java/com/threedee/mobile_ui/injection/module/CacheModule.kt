package com.threedee.mobile_ui.injection.module

import android.app.Application
import androidx.room.Room
import com.threedee.cache.db.ThreeDatabase
import com.threedee.cache.implementation.BufferooCacheImpl
import com.threedee.cache.implementation.UserCacheImpl
import com.threedee.data.repository.buffer.BufferooCache
import com.threedee.data.repository.user.UserCache
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Module that provides all dependencies from the cache package/layer.
 */
@Module
abstract class CacheModule {

    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideBufferoosDatabase(application: Application): ThreeDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                ThreeDatabase::class.java, "bufferoos.db"
            )
                .build()
        }
    }

    @Binds
    abstract fun bindBufferooCache(bufferooCacheImpl: BufferooCacheImpl): BufferooCache

    @Binds
    abstract fun bindUserCache(userCacheImpl: UserCacheImpl): UserCache
}