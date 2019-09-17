package com.threedee.presentation.browse

import com.threedee.presentation.base.BaseIntent

//represents the user interactions
sealed class BrowseIntent : BaseIntent {
    object InitialIntent : BrowseIntent()
    object LoadingIntent : BrowseIntent()
    object RefreshBufferoosIntent : BrowseIntent()
}