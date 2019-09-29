package com.threedee.data.source.user

import com.threedee.data.repository.user.UserCache
import com.threedee.data.repository.user.UserDataStore
import com.threedee.domain.interactor.user.GetUserByParam
import com.threedee.domain.interactor.user.LoginUser
import com.threedee.domain.interactor.user.LogoutUser
import com.threedee.domain.interactor.user.RegisterUser
import com.threedee.domain.interactor.user.SaveUser
import com.threedee.domain.interactor.user.SaveUserByParam
import com.threedee.domain.interactor.user.UpdateUser
import com.threedee.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserCacheDataStore @Inject constructor(private val userCache: UserCache) : UserDataStore {
    override fun loginUser(param: LoginUser.Param): Completable {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun registerUser(param: RegisterUser.Param): Completable {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun logoutUser(param: LogoutUser.Param): Completable {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun saveUser(param: SaveUser.Param): Completable {
        return userCache.saveUser(param)
    }

    override fun updateUser(param: UpdateUser.Param): Completable {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun getLoggedInUser(): Single<User> {
        return userCache.getLoggedInUser()
    }

    override fun getUserByParam(param: GetUserByParam.Param): Single<User> {
        return userCache.getUserByParam(param)
    }

    override fun saveUserByParam(param: SaveUserByParam.Param): Completable {
        return userCache.saveUserByParam(param)
    }

    override fun isUserLoggedIn(): Boolean {
        return userCache.isUserLoggedIn()
    }

    override fun isUserByParamCached(param: GetUserByParam.Param): Single<Boolean> {
        return userCache.isUserByParamCached(param)
    }
}