package com.threedee.domain.interactor.user

import com.threedee.domain.executor.PostExecutionThread
import com.threedee.domain.executor.ThreadExecutor
import com.threedee.domain.interactor.SingleUseCase
import com.threedee.domain.model.User
import com.threedee.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case used for retreiving a [User] instances from the [UserRepository]
 */
open class GetUserByParam @Inject constructor(
    val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<User, GetUserByParam.Param>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Param?): Single<User> {
        return userRepository.getUserByParam(
            params ?: throw IllegalArgumentException("Param should not be null.")
        )
    }

    data class Param(
        val id: Int
    )
}