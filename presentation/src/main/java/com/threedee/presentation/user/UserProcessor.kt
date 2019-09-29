package com.threedee.presentation.user

import com.threedee.domain.interactor.user.GetLoggedInUser
import com.threedee.domain.interactor.user.LoginUser
import com.threedee.domain.interactor.user.RegisterUser
import com.threedee.presentation.base.BaseActionProcessor
import io.reactivex.CompletableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import javax.inject.Inject

class UserProcessor @Inject constructor(
    private val loginUser: LoginUser,
    private val registerUser: RegisterUser,
    private val getLoggedInUser: GetLoggedInUser
) : BaseActionProcessor<UserAction, UserResult>() {

    override fun getActionProcessors(shared: Observable<UserAction>): List<Observable<UserResult>> {
        return listOf(
            shared.connect(loginUserProcessor),
            shared.connect(registerUserProcessor)
        )
    }

    private val loginUserProcessor: ObservableTransformer<UserAction.LoginAction, UserResult> =
        ObservableTransformer {
            it.publish {
                it.switchMap {
                    loginUser.execute(it.param)
                        .andThen(Observable.just(UserResult.LoginSuccess as UserResult))
                        .startWith(Observable.just(UserResult.LoginLoading))
                        .onErrorReturn { UserResult.LoginError(it.localizedMessage) }
                }
            }
        }

    private val registerUserProcessor: ObservableTransformer<UserAction.RegisterAction, UserResult> =
        ObservableTransformer {
            it.publish {
                it.switchMap {
                    registerUser.execute(it.param)
                        .andThen(Observable.just(UserResult.RegisterUserSuccess as UserResult))
                        .startWith(Observable.just(UserResult.RegisterLoading))
                        .onErrorReturn { UserResult.RegisterError(it.localizedMessage) }
                }
            }
        }
}