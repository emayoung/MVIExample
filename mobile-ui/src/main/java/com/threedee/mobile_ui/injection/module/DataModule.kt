package com.threedee.mobile_ui.injection.module

import com.threedee.data.executors.JobExecutor
import com.threedee.data.implementation.BufferooDataRepository
import com.threedee.domain.executor.ThreadExecutor
import com.threedee.domain.repository.BufferooRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindBufferooRepository(bufferooDataRepository: BufferooDataRepository):
        BufferooRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}