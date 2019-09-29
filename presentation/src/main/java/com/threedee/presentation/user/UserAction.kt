package com.threedee.presentation.user

import com.threedee.domain.interactor.user.GetUserByParam
import com.threedee.domain.interactor.user.LoginUser
import com.threedee.domain.interactor.user.LogoutUser
import com.threedee.domain.interactor.user.RegisterUser
import com.threedee.domain.interactor.user.UpdateUser
import com.threedee.presentation.base.BaseAction

sealed class UserAction : BaseAction {
    data class LoginAction(val param: LoginUser.Param) : UserAction()
    data class RegisterAction(val param: RegisterUser.Param) : UserAction()
    data class LogoutAction(val param: LogoutUser.Param) : UserAction()
    data class UpdateUserAction(val param: UpdateUser.Param) : UserAction()
    object GetUserAction : UserAction()
    object DoNothingUserAction : UserAction()
    data class GetUserByParamAction(val param: GetUserByParam.Param) : UserAction()
}