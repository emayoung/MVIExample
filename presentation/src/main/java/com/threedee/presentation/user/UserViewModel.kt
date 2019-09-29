package com.threedee.presentation.user

import androidx.lifecycle.ViewModel
import com.threedee.presentation.base.BaseIntent
import com.threedee.presentation.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class UserViewModel @Inject internal constructor(
    private val userProcessor: UserProcessor
) : ViewModel(), BaseViewModel<UserIntent, UserViewState> {

    private var intentsSubject: PublishSubject<UserIntent> = PublishSubject.create()

    private val reducer: BiFunction<UserViewState, UserResult, UserViewState> =
        BiFunction<UserViewState, UserResult, UserViewState> { previousState, result ->
            println("previous state:$previousState, result: $result")
            when (result) {
                UserResult.Loading -> {
                    UserViewState.InProgress
                }
                UserResult.LoginLoading -> {
                    UserViewState.LoginInProgress
                }
                UserResult.RegisterLoading -> {
                    UserViewState.RegisterInProgress
                }
                is UserResult.Error -> {
                    UserViewState.Failed(result.errorMessage)
                }
                is UserResult.LoginError -> {
                    UserViewState.LoginFailed(result.errorMessage)
                }
                is UserResult.RegisterError -> {
                    UserViewState.RegisterFailed(result.errorMessage)
                }
                is UserResult.LoginSuccess -> {
                    UserViewState.LoginUserSuccess
                }
                is UserResult.Idle -> {
                    UserViewState.Idle()
                }
                is UserResult.LoginIdle -> {
                    UserViewState.LoginIdle()
                }
                is UserResult.RegisterIdle -> {
                    UserViewState.RegisterIdle()
                }
                is UserResult.RegisterUserSuccess -> {
                    UserViewState.RegisterUserSuccess
                }
                is UserResult.LogoutUserSuccess -> {
                    UserViewState.LogoutUserSuccess
                }
                is UserResult.GetLoggedInUserSuccess -> {
                    UserViewState.GetUserSuccess(result.data)
                }
                is UserResult.GetLoggedInUserByParamSuccess -> {
                    UserViewState.GetLoggedInUserSuccess(result.data)
                }
                is UserResult.UpdateUserSuccess -> {
                    UserViewState.UpdateUserSuccess
                }
            }
        }

    private val statesSubject: Observable<UserViewState> = compose()

    override fun processIntents(intents: Observable<UserIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<UserViewState> {
        return statesSubject
    }

    private fun compose(): Observable<UserViewState> {
        return intentsSubject
            .map { actionFromIntent(it) }
            .compose(userProcessor)
            .map {
                println("user result $it")
                it
            }
            .scan<UserViewState>(UserViewState.Idle(), reducer)
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: BaseIntent): UserAction {
        return when(intent){
            is UserIntent.LoginIntent -> UserAction.LoginAction(intent.param)
            is UserIntent.LogoutIntent -> UserAction.LogoutAction(intent.param)
            is UserIntent.RegisterIntent -> UserAction.RegisterAction(intent.param)
            is UserIntent.UpdateUserIntent -> UserAction.UpdateUserAction(intent.param)
            is UserIntent.GetUserIntent -> UserAction.GetUserAction
            is UserIntent.IdleIntent -> UserAction.DoNothingUserAction
            is UserIntent.GetUserByParamIntent -> UserAction.GetUserByParamAction(intent.param)
            else -> throw UnsupportedOperationException(
                "Oops, that looks like an unknown intent: $intent"
            )
        }
    }
}