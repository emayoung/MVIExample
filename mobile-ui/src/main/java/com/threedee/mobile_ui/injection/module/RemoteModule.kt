package com.threedee.mobile_ui.injection.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.threedee.data.repository.buffer.BufferooRemote
import com.threedee.data.repository.user.UserRemote
import com.threedee.mobile_ui.BuildConfig
import com.threedee.remote.BufferooRemoteImpl
import com.threedee.remote.BufferooService
import com.threedee.remote.BufferooServiceFactory
import com.threedee.remote.UserRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Module that provides all dependencies from the repository package/layer.
 */
@Module
abstract class RemoteModule {

    /**
     * This companion object annotated as a module is necessary in order to provide dependencies
     * statically in case the wrapping module is an abstract class (to use binding)
     */
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideBufferooService(): BufferooService {
            return BufferooServiceFactory.makeBuffeoorService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        @JvmStatic
        fun provideFirebaseStore(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }

    }

    @Binds
    abstract fun bindBufferooRemote(bufferooRemoteImpl: BufferooRemoteImpl): BufferooRemote

    @Binds
    abstract fun bindUserRemotee(userRemoteImpl: UserRemoteImpl): UserRemote
}