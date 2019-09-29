package com.threedee.data.repository.user

import com.threedee.domain.interactor.user.GetUserByParam
import com.threedee.domain.interactor.user.LoginUser
import com.threedee.domain.interactor.user.LogoutUser
import com.threedee.domain.interactor.user.RegisterUser
import com.threedee.domain.interactor.user.UpdateUser
import com.threedee.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRemote {
    fun loginUser(param: LoginUser.Param): Completable
    fun registerUser(param: RegisterUser.Param): Completable
    fun logoutUser(param: LogoutUser.Param): Completable
    fun updateUser(param: UpdateUser.Param): Completable
    fun getLoggedInUser(): Single<User>
    fun getUserByParam(param: GetUserByParam.Param): Single<User>
}