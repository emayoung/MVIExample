package com.threedee.cache.implementation

import com.threedee.cache.PreferencesHelper
import com.threedee.cache.db.ThreeDatabase
import com.threedee.cache.mapper.BufferooEntityMapper
import com.threedee.data.model.BufferooEntity
import com.threedee.data.repository.buffer.BufferooCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving Bufferoo instances. This class implements the
 * [BufferooCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class BufferooCacheImpl  @Inject constructor(val bufferoosDatabase: ThreeDatabase,
    private val entityMapper: BufferooEntityMapper,
    private val preferencesHelper: PreferencesHelper
) :
    BufferooCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Retrieve an instance from the database, used for tests.
     */
    internal fun getDatabase(): ThreeDatabase {
        return bufferoosDatabase
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override fun clearBufferoos(): Completable {
        return Completable.defer {
            bufferoosDatabase.cachedBufferooDao().clearBufferoos()
            Completable.complete()
        }
    }

    /**
     * Save the given list of [BufferooEntity] instances to the database.
     */
    override fun saveBufferoos(bufferoos: List<BufferooEntity>): Completable {
        return Completable.defer {
            bufferoos.forEach {
                bufferoosDatabase.cachedBufferooDao().insertBufferoo(
                    entityMapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

    /**
     * Retrieve a list of [BufferooEntity] instances from the database.
     */
    override fun getBufferoos(): Flowable<List<BufferooEntity>> {
        return Flowable.defer {
            Flowable.just(bufferoosDatabase.cachedBufferooDao().getBufferoos())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    /**
     * Check whether there are instances of [CachedBufferoo] stored in the cache.
     */
    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(bufferoosDatabase.cachedBufferooDao().getBufferoos().isNotEmpty())
        }
    }

    /**
     * Set a point in time at when the cache was last updated.
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time.
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}