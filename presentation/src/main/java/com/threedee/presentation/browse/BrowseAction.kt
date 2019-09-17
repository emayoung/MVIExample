package com.threedee.presentation.browse

import com.threedee.presentation.base.BaseAction

//these are actions mapped from intents
sealed class BrowseAction : BaseAction {
    object LoadBufferoosAction : BrowseAction()
}