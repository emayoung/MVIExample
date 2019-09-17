package com.threedee.presentation.browse

import com.threedee.domain.model.Bufferoo
import com.threedee.presentation.base.BaseResult
import com.threedee.presentation.base.model.TaskStatus

sealed class BrowseResult : BaseResult {

    class LoadBufferoosTask(
        val taskStatus: TaskStatus,
        val data: List<Bufferoo>? = null
    ) : BrowseResult() {
        companion object {

            internal fun success(data: List<Bufferoo>?): LoadBufferoosTask {
                return LoadBufferoosTask(
                    TaskStatus.SUCCESS,
                    data
                )
            }

            internal fun failure(): LoadBufferoosTask {
                return LoadBufferoosTask(
                    TaskStatus.FAILURE,
                    null
                )
            }

            internal fun inFlight(): LoadBufferoosTask {
                return LoadBufferoosTask(TaskStatus.IN_FLIGHT)
            }
        }
    }
}