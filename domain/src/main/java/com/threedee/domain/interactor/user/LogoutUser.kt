package com.threedee.domain.interactor.user

import com.threedee.domain.executor.PostExecutionThread
import com.threedee.domain.executor.ThreadExecutor
import com.threedee.domain.interactor.CompletableUseCase
import com.threedee.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class LogoutUser @Inject constructor(
    val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<LogoutUser.Param>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: LogoutUser.Param): Completable {
        return userRepository.logoutUser(params)
    }

    data class Param(
        val email: String,
        val password: String
    )
}