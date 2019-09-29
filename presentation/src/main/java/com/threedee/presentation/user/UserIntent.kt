package com.threedee.presentation.user

import com.threedee.domain.interactor.user.GetUserByParam
import com.threedee.domain.interactor.user.LoginUser
import com.threedee.domain.interactor.user.LogoutUser
import com.threedee.domain.interactor.user.RegisterUser
import com.threedee.domain.interactor.user.UpdateUser
import com.threedee.presentation.base.BaseIntent

sealed class UserIntent : BaseIntent {
    object IdleIntent : UserIntent()
    data class LoginIntent(val param: LoginUser.Param) : UserIntent()
    data class RegisterIntent(val param: RegisterUser.Param) : UserIntent()
    data class LogoutIntent(val param: LogoutUser.Param) : UserIntent()
    data class UpdateUserIntent(val param: UpdateUser.Param) : UserIntent()
    object GetUserIntent : UserIntent()
    data class GetUserByParamIntent(val param: GetUserByParam.Param) : UserIntent()
}