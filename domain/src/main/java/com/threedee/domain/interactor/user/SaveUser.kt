package com.threedee.domain.interactor.user

import com.threedee.domain.executor.PostExecutionThread
import com.threedee.domain.executor.ThreadExecutor
import com.threedee.domain.interactor.CompletableUseCase
import com.threedee.domain.model.User
import com.threedee.domain.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class SaveUser @Inject constructor(
    val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<SaveUser.Param>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: SaveUser.Param): Completable {
        return userRepository.saveUser(params)
    }

    data class Param(
        val user: User
    )
}