package com.threedee.cache.implementation

import com.threedee.cache.PreferencesHelper
import com.threedee.cache.db.ThreeDatabase
import com.threedee.data.repository.user.UserCache
import com.threedee.domain.interactor.user.GetUserByParam
import com.threedee.domain.interactor.user.SaveUser
import com.threedee.domain.interactor.user.SaveUserByParam
import com.threedee.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserCacheImpl @Inject constructor(
    val threeDatabase: ThreeDatabase,
    private val preferencesHelper: PreferencesHelper
) : UserCache {
    override fun saveUser(param: SaveUser.Param): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLoggedInUser(): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUserByParam(param: GetUserByParam.Param): Single<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveUserByParam(param: SaveUserByParam.Param): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isUserLoggedIn(): Boolean {
        return preferencesHelper.isUserLoggedIn
    }

    override fun setUserIsLoggedIn(loggedIn: Boolean) {
        preferencesHelper.isUserLoggedIn = loggedIn
    }

    override fun isUserByParamCached(param: GetUserByParam.Param): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearUser(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isUserFirstTime(): Boolean {
        return preferencesHelper.isUserFirstTime
    }

    override fun setIsUserFirstTime(status: Boolean) {
        preferencesHelper.isUserFirstTime = status
    }
}