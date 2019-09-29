package com.threedee.presentation.user

import com.threedee.domain.model.User
import com.threedee.presentation.base.BaseViewState

sealed class UserViewState(
    val inProgress: Boolean = false,
    val error: String? = null,
    val data: Any? = null
) : BaseViewState {

    object InProgress : UserViewState(true, null)
    object LoginInProgress : UserViewState(true, null)
    object RegisterInProgress : UserViewState(true, null)

    data class Failed(val message: String) : UserViewState(false, message)
    data class LoginFailed(val message: String) : UserViewState(false, message)
    data class RegisterFailed(val message: String) : UserViewState(false, message)

    class Idle : UserViewState(false, null)
    class LoginIdle : UserViewState(false, null)
    class RegisterIdle : UserViewState(false, null)

    object LoginUserSuccess : UserViewState(false, null)
    object LogoutUserSuccess : UserViewState(false, null)
    object RegisterUserSuccess : UserViewState(false, null)
    object UpdateUserSuccess : UserViewState(false, null)
    data class GetUserSuccess(val user: User) : UserViewState(false, data = user)
    data class GetLoggedInUserSuccess(val user: User) : UserViewState(false, data = user)

}