package com.threedee.presentation.user

import com.threedee.domain.model.User
import com.threedee.presentation.base.BaseResult

sealed class UserResult : BaseResult {
    object Idle : UserResult()
    object LoginIdle : UserResult()
    object RegisterIdle : UserResult()

    object Loading : UserResult()
    object LoginLoading : UserResult()
    object RegisterLoading : UserResult()

    data class Error(val errorMessage: String) : UserResult()
    data class LoginError(val errorMessage: String) : UserResult()
    data class RegisterError(val errorMessage: String) : UserResult()

    object LoginSuccess : UserResult()
    object RegisterUserSuccess : UserResult()
    object LogoutUserSuccess : UserResult()
    object UpdateUserSuccess : UserResult()
    data class GetLoggedInUserSuccess(val data: User) : UserResult()
    data class GetLoggedInUserByParamSuccess(val data: User) : UserResult()
}