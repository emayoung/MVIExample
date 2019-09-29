package com.threedee.domain.interactor.user

import com.threedee.domain.executor.PostExecutionThread
import com.threedee.domain.executor.ThreadExecutor
import com.threedee.domain.interactor.CompletableUseCase
import com.threedee.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class RegisterUser @Inject constructor(
    val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<RegisterUser.Param>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: RegisterUser.Param): Completable {
        return userRepository.registerUser(params)
    }

    data class Param(
        val email: String,
        val password: String,
        val fullName: String,
        val phone: String
    )
}