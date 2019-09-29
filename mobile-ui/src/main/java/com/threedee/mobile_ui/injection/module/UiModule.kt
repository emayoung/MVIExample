package com.threedee.mobile_ui.injection.module

import com.threedee.domain.executor.PostExecutionThread
import com.threedee.mobile_ui.ui.browse.MainActivity
import com.threedee.mobile_ui.UiThread
import com.threedee.mobile_ui.ui.auth.AuthenticationActivity
import com.threedee.mobile_ui.ui.onboarding.OnboardingActivity
import com.threedee.mobile_ui.ui.splash.SplashActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module that provides all dependencies from the mobile-ui package/layer and injects all activities.
 */
@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [(OnboardingFragmentModule::class)])
    abstract fun contributeOnboardingActivity(): OnboardingActivity

    @ContributesAndroidInjector(modules = [(AuthenticationFragmentModule::class)])
    abstract fun contributeAuthenticationActivity(): AuthenticationActivity
}