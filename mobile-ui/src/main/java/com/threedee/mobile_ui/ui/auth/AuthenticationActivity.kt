package com.threedee.mobile_ui.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.threedee.data.repository.user.UserCache
import com.threedee.mobile_ui.BaseActivity
import com.threedee.mobile_ui.R
import com.threedee.mobile_ui.databinding.ActivityAuthenticationBinding
import com.threedee.presentation.base.BaseView
import com.threedee.presentation.user.UserIntent
import com.threedee.presentation.user.UserViewModel
import com.threedee.presentation.user.UserViewState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class AuthenticationActivity : BaseActivity() {

    lateinit var binding: ActivityAuthenticationBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var userCache: UserCache
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)

        initViewModel()
        initViews()
    }

    private fun initViewModel(){
        userViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(UserViewModel::class.java)

    }

    private fun initViews(){
        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = AuthenticationPagerAdapter(this)
        // Must be declared before TabLayoutMediator.attach()
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager, object : TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                val tabTitle = when(position) {
                    0 -> "Sign up"
                    1 -> "Login"
                    else -> throw IllegalArgumentException("Position not known")
                }
                tab.text = tabTitle
            }
        }).attach()
    }


    companion object {
        /**
         * The number of pages
         */
        private const val NUM_PAGES = 2

        fun startActivity(context: Context){
            context.startActivity(Intent(context, AuthenticationActivity::class.java))
        }
    }

    /**
     * A simple authentication pager adapter that represents Fragment objects, in sequence.
     */
    private inner class AuthenticationPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES


        override fun createFragment(position: Int): Fragment {
            Timber.e("position $position")
            return when (position) {
                0 -> RegisterFragment()
                1 -> LoginFragment()
                else -> throw IllegalArgumentException("Position not known.")
            }
        }
    }
}
