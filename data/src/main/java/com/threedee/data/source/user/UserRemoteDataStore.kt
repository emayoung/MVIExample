package com.threedee.data.source.user

import com.threedee.data.repository.user.UserDataStore
import com.threedee.data.repository.user.UserRemote
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

class UserRemoteDataStore @Inject constructor(private val userRemote: UserRemote) : UserDataStore {
    override fun loginUser(param: LoginUser.Param): Completable {
        return userRemote.loginUser(param)
    }

    override fun registerUser(param: RegisterUser.Param): Completable {
        return userRemote.registerUser(param)
    }

    override fun logoutUser(param: LogoutUser.Param): Completable {
        return userRemote.logoutUser(param)
    }

    override fun updateUser(param: UpdateUser.Param): Completable {
        return userRemote.updateUser(param)
    }

    override fun getLoggedInUser(): Single<User> {
        return userRemote.getLoggedInUser()
    }

    override fun getUserByParam(param: GetUserByParam.Param): Single<User> {
        return userRemote.getUserByParam(param)
    }

    override fun saveUser(param: SaveUser.Param): Completable {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun saveUserByParam(param: SaveUserByParam.Param): Completable {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun isUserLoggedIn(): Boolean {
        throw UnsupportedOperationException("Not supported.")
    }

    override fun isUserByParamCached(param: GetUserByParam.Param): Single<Boolean> {
        throw UnsupportedOperationException("Not supported.")
    }
}