package com.threedee.mobile_ui.ui.splash

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.threedee.data.repository.user.UserCache
import com.threedee.mobile_ui.BaseActivity
import com.threedee.mobile_ui.R
import com.threedee.mobile_ui.ui.auth.AuthenticationActivity
import com.threedee.mobile_ui.ui.onboarding.OnboardingActivity
import com.threedee.presentation.base.BaseView
import com.threedee.presentation.user.UserIntent
import com.threedee.presentation.user.UserViewModel
import com.threedee.presentation.user.UserViewState
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var userCache: UserCache

    override fun onCreate(savedInstanceState: Bundle?) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)

//        test.setOnClickListener {
//            loginP.onNext(UserIntent.LoginIntent(LoginUser.Param("email", "password")))
//        }

        continueAppWorkflow()
    }

    private fun continueAppWorkflow() {
        if (userCache.isUserFirstTime()) {
            //send to onboarding
            OnboardingActivity.startActivity(this)
        } else if (!userCache.isUserLoggedIn()) {
            //send to login
            AuthenticationActivity.startActivity(this)
        } else {
            //send to home
        }
        finish()
    }

}
