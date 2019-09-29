package com.threedee.domain.interactor.user

import com.threedee.domain.executor.PostExecutionThread
import com.threedee.domain.executor.ThreadExecutor
import com.threedee.domain.interactor.SingleUseCase
import com.threedee.domain.model.Bufferoo
import com.threedee.domain.model.User
import com.threedee.domain.repository.BufferooRepository
import com.threedee.domain.repository.UserRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case used for retreiving a [User] instances from the [UserRepository]
 */
open class GetLoggedInUser @Inject constructor(
    val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<User, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Single<User> {
        return userRepository.getLoggedInUser()
    }
}