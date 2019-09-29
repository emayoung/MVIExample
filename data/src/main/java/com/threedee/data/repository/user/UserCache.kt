package com.threedee.data.repository.user

import com.threedee.domain.interactor.user.GetUserByParam
import com.threedee.domain.interactor.user.SaveUser
import com.threedee.domain.interactor.user.SaveUserByParam
import com.threedee.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserCache {
    fun saveUser(param: SaveUser.Param): Completable
    fun getLoggedInUser(): Single<User>
    fun getUserByParam(param: GetUserByParam.Param): Single<User>
    fun saveUserByParam(param: SaveUserByParam.Param): Completable
    fun isUserLoggedIn(): Boolean
    fun setUserIsLoggedIn(loggedIn: Boolean)
    fun isUserByParamCached(param: GetUserByParam.Param): Single<Boolean>
    fun clearUser(): Completable
    fun isUserFirstTime(): Boolean
    fun setIsUserFirstTime(status: Boolean)
}