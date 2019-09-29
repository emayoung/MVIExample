package com.threedee.mobile_ui.injection.module

import com.threedee.mobile_ui.ui.onboarding.OnboardingFragmentFour
import com.threedee.mobile_ui.ui.onboarding.OnboardingFragmentOne
import com.threedee.mobile_ui.ui.onboarding.OnboardingFragmentThree
import com.threedee.mobile_ui.ui.onboarding.OnboardingFragmentTwo
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OnboardingFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeOnboardingFragmentOne(): OnboardingFragmentOne

    @ContributesAndroidInjector
    abstract fun contributeOnboardingFragmentTwo(): OnboardingFragmentTwo

    @ContributesAndroidInjector
    abstract fun contributeOnboardingFragmentThree(): OnboardingFragmentThree

    @ContributesAndroidInjector
    abstract fun contributeOnboardingFragmentFour(): OnboardingFragmentFour


}