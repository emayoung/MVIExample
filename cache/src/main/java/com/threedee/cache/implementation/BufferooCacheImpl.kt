package com.threedee.cache.implementation

import com.threedee.data.model.BufferooEntity
import com.threedee.data.repository.BufferooCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving Bufferoo instances. This class implements the
 * [BufferooCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class BufferooCacheImpl @Inject constructor() : BufferooCache {
    override fun clearBufferoos(): Completable {
        return Completable.complete()
    }

    override fun saveBufferoos(bufferoos: List<BufferooEntity>): Completable {
        return Completable.complete()
    }

    override fun getBufferoos(): Flowable<List<BufferooEntity>> {
        throw Throwable("empty")
    }

    override fun isCached(): Single<Boolean> {
        return Single.just(false)
    }

    override fun setLastCacheTime(lastCache: Long) {

    }

    override fun isExpired(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}