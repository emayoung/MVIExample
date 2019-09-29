package com.threedee.data.source.user

import com.threedee.data.repository.user.UserDataStore
import javax.inject.Inject

class UserDataStoreFactory @Inject constructor(
    private val userCacheDataStore: UserCacheDataStore,
    private val userRemoteDataStore: UserRemoteDataStore
) {
    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): UserDataStore {
        return userCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): UserDataStore {
        return userRemoteDataStore
    }
}