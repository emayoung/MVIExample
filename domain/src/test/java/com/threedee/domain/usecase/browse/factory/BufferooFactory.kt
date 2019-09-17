package com.threedee.domain.usecase.browse.factory

import com.threedee.domain.model.Bufferoo
import com.threedee.domain.usecase.browse.factory.DataFactory.Factory.randomLong
import com.threedee.domain.usecase.browse.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for Bufferoo related instances
 */
class BufferooFactory {

    companion object Factory {

        fun makeBufferooList(count: Int): List<Bufferoo> {
            val bufferoos = mutableListOf<Bufferoo>()
            repeat(count) {
                bufferoos.add(makeBufferoo())
            }
            return bufferoos
        }

        fun makeBufferoo(): Bufferoo {
            return Bufferoo(randomLong(), randomUuid(), randomUuid(), randomUuid())
        }
    }
}