package com.threedee.data.mapper

import com.threedee.data.model.BufferooEntity
import com.threedee.data.test.factory.BufferooFactory
import com.threedee.domain.model.Bufferoo
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BufferooMapperTest {

    private lateinit var bufferooMapper: BufferooMapper

    @Before
    fun setUp() {
        bufferooMapper = BufferooMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val bufferooEntity = BufferooFactory.makeBufferooEntity()
        val bufferoo = bufferooMapper.mapFromEntity(bufferooEntity)

        assertBufferooDataEquality(bufferooEntity, bufferoo)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedBufferoo = BufferooFactory.makeBufferoo()
        val bufferooEntity = bufferooMapper.mapToEntity(cachedBufferoo)

        assertBufferooDataEquality(bufferooEntity, cachedBufferoo)
    }

    private fun assertBufferooDataEquality(
        bufferooEntity: BufferooEntity,
        bufferoo: Bufferoo
    ) {
        assertEquals(bufferooEntity.name, bufferoo.name)
        assertEquals(bufferooEntity.title, bufferoo.title)
        assertEquals(bufferooEntity.avatar, bufferoo.avatar)
    }
}