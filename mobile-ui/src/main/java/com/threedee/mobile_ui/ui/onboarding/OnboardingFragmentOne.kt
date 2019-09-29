package com.threedee.mobile_ui.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.threedee.data.repository.user.UserCache
import com.threedee.mobile_ui.R
import com.threedee.mobile_ui.databinding.LayoutFragmentOneBinding
import com.threedee.mobile_ui.ui.auth.AuthenticationActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class OnboardingFragmentOne : DaggerFragment() {

    lateinit var binding: LayoutFragmentOneBinding
    @Inject
    lateinit var userCache: UserCache

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_one, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonVisitStore.setOnClickListener {
            activity?.let {
                updateAppFirstTime()
                AuthenticationActivity.startActivity(it)
                it.finish()
            }
        }
    }

    private fun updateAppFirstTime() {
        userCache.setIsUserFirstTime(false)
    }
}