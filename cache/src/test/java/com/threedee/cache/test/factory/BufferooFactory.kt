package com.threedee.cache.test.factory

import com.threedee.cache.model.CachedBufferoo
import com.threedee.cache.test.factory.DataFactory.Factory.randomLong
import com.threedee.cache.test.factory.DataFactory.Factory.randomUuid
import com.threedee.data.model.BufferooEntity

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeCachedBufferoo(): CachedBufferoo {
            return CachedBufferoo(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeBufferooEntity(): BufferooEntity {
            return BufferooEntity(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeBufferooEntityList(count: Int): List<BufferooEntity> {
            val bufferooEntities = mutableListOf<BufferooEntity>()
            repeat(count) {
                bufferooEntities.add(makeBufferooEntity())
            }
            return bufferooEntities
        }

        fun makeCachedBufferooList(count: Int): List<CachedBufferoo> {
            val cachedBufferoos = mutableListOf<CachedBufferoo>()
            repeat(count) {
                cachedBufferoos.add(makeCachedBufferoo())
            }
            return cachedBufferoos
        }

    }

}