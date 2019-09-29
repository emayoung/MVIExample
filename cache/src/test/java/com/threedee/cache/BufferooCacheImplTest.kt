package com.threedee.cache

import androidx.room.Room
import com.threedee.cache.db.ThreeDatabase
import com.threedee.cache.implementation.BufferooCacheImpl
import com.threedee.cache.mapper.BufferooEntityMapper
import com.threedee.cache.model.CachedBufferoo
import com.threedee.cache.test.factory.BufferooFactory
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class BufferooCacheImplTest {

    private var bufferoosDatabase = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application,
        ThreeDatabase::class.java
    ).allowMainThreadQueries().build()
    private var entityMapper = BufferooEntityMapper()
    private var preferencesHelper = PreferencesHelper(RuntimeEnvironment.application)

    private val databaseHelper = BufferooCacheImpl(
        bufferoosDatabase,
        entityMapper, preferencesHelper
    )

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearBufferoos().test()
        testObserver.assertComplete()
    }

    //<editor-fold desc="Save Bufferoos">
    @Test
    fun saveBufferoosCompletes() {
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(2)

        val testObserver = databaseHelper.saveBufferoos(bufferooEntities).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveBufferoosSavesData() {
        val bufferooCount = 2
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(bufferooCount)

        databaseHelper.saveBufferoos(bufferooEntities).test()
        checkNumRowsInBufferoosTable(bufferooCount)
    }
    //</editor-fold>

    //<editor-fold desc="Get Bufferoos">
    @Test
    fun getBufferoosCompletes() {
        val testObserver = databaseHelper.getBufferoos().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBufferoosReturnsData() {
        val bufferooEntities = BufferooFactory.makeBufferooEntityList(2)
        val cachedBufferoos = mutableListOf<CachedBufferoo>()
        bufferooEntities.forEach {
            cachedBufferoos.add(entityMapper.mapToCached(it))
        }
        insertBufferoos(cachedBufferoos)

        val testObserver = databaseHelper.getBufferoos().test()
        //  testObserver.assertValue(bufferooEntities)
    }
    //</editor-fold>

    private fun insertBufferoos(cachedBufferoos: List<CachedBufferoo>) {
        cachedBufferoos.forEach {
            bufferoosDatabase.cachedBufferooDao().insertBufferoo(it)
        }
    }

    private fun checkNumRowsInBufferoosTable(expectedRows: Int) {
        val numberOfRows = bufferoosDatabase.cachedBufferooDao().getBufferoos().size
        assertEquals(expectedRows, numberOfRows)
    }
}