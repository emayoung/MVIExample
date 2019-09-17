package com.threedee.presentation.browse

import com.threedee.presentation.base.BaseViewState
import com.threedee.presentation.browse.model.BufferooView

sealed class BrowseViewState(
    val inProgress: Boolean = false,
    val bufferoos: List<BufferooView>? = null
) : BaseViewState {

    object InProgress : BrowseViewState(true, null)

    object Failed : BrowseViewState()

    data class Success(private val result: List<BufferooView>?) : BrowseViewState(false, result)

    class Idle : BrowseViewState(false, null)
}