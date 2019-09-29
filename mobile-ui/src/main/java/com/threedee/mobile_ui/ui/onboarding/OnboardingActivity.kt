package com.threedee.mobile_ui.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.threedee.mobile_ui.BaseActivity
import com.threedee.mobile_ui.R
import com.threedee.mobile_ui.databinding.ActivityOnboardingBinding
import timber.log.Timber

class OnboardingActivity : BaseActivity() {

    lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        initViews()
    }

    private fun initViews() {
        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = OnboardingPagerAdapter(this)
        binding.pager.adapter = pagerAdapter
        binding.pager.setPageTransformer(ZoomOutPageTransformer())
    }

    override fun onBackPressed() {
        if (binding.pager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
    }

    /**
     * A simple onboarding pager adapter that represents Fragment objects, in sequence.
     */
    private inner class OnboardingPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES


        override fun createFragment(position: Int): Fragment {
            Timber.e("position $position")
            return when (position) {
                0 -> OnboardingFragmentOne()
                1 -> OnboardingFragmentTwo()
                2 -> OnboardingFragmentThree()
                3 -> OnboardingFragmentFour()
                else -> throw IllegalArgumentException("Position not known.")
            }
        }
    }

    companion object {
        /**
         * The number of pages
         */
        private const val NUM_PAGES = 4
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f

        fun startActivity(context: Context){
            context.startActivity(Intent(context, OnboardingActivity::class.java))
        }
    }

    private inner class ZoomOutPageTransformer : ViewPager2.PageTransformer {

        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (MIN_ALPHA +
                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }
}
