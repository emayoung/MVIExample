package com.threedee.mobile_ui.injection.module

import com.threedee.mobile_ui.ui.auth.LoginFragment
import com.threedee.mobile_ui.ui.auth.RegisterFragment
import com.threedee.mobile_ui.ui.onboarding.OnboardingFragmentFour
import com.threedee.mobile_ui.ui.onboarding.OnboardingFragmentOne
import com.threedee.mobile_ui.ui.onboarding.OnboardingFragmentThree
import com.threedee.mobile_ui.ui.onboarding.OnboardingFragmentTwo
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthenticationFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

}