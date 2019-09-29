package com.threedee.data.implementation

import com.threedee.data.repository.user.UserCache
import com.threedee.data.source.user.UserDataStoreFactory
import com.threedee.domain.interactor.user.GetUserByParam
import com.threedee.domain.interactor.user.LoginUser
import com.threedee.domain.interactor.user.LogoutUser
import com.threedee.domain.interactor.user.RegisterUser
import com.threedee.domain.interactor.user.SaveUser
import com.threedee.domain.interactor.user.SaveUserByParam
import com.threedee.domain.interactor.user.UpdateUser
import com.threedee.domain.model.User
import com.threedee.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleTransformer
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val userCache: UserCache,
    private val factory: UserDataStoreFactory
) : UserRepository {
    override fun loginUser(param: LoginUser.Param): Completable {
        return factory.retrieveRemoteDataStore().loginUser(param)
    }

    override fun registerUser(param: RegisterUser.Param): Completable {
        return factory.retrieveRemoteDataStore().registerUser(param)
    }

    override fun logoutUser(param: LogoutUser.Param): Completable {
        return factory.retrieveRemoteDataStore().logoutUser(param)
            .compose(clearUser())
    }

    override fun saveUser(param: SaveUser.Param): Completable {
        return factory.retrieveCacheDataStore().saveUser(param)
    }

    override fun updateUser(param: UpdateUser.Param): Completable {
        return factory.retrieveRemoteDataStore().updateUser(param)
    }

    override fun getLoggedInUser(): Single<User> {
        //todo: refactor to user cache and remote
        return factory.retrieveRemoteDataStore().getLoggedInUser()
            .doOnSuccess {
                //todo: refactor to use transformers
                saveUser(SaveUser.Param(it)).subscribe()
            }
    }

    override fun getUserByParam(param: GetUserByParam.Param): Single<User> {
        return factory.retrieveRemoteDataStore().getUserByParam(param)
            .doOnSuccess {
                saveUserByParam(SaveUserByParam.Param(it)).subscribe()
            }
    }

    override fun saveUserByParam(param: SaveUserByParam.Param): Completable {
        return factory.retrieveCacheDataStore().saveUserByParam(param)
    }

    private fun clearUser(): CompletableTransformer = CompletableTransformer {
        userCache.clearUser()
    }
}