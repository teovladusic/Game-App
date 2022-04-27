package com.example.gameapp.feature_games.presentation.games_genre_selection

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.viewpager2.widget.ViewPager2
import com.example.gameapp.R
import com.example.gameapp.core.di.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GamesGenreSelectionFragmentTests {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun onGetStartedBtnClick_shouldMoveToNextPage() {
        lateinit var viewPager: ViewPager2

        launchFragmentInHiltContainer<GamesGenreSelectionFragment> {
            viewPager = this.viewPager
        }

        assert(viewPager.currentItem == 0)

        onView(withId(R.id.btn_get_started)).perform(click())

        assert(viewPager.currentItem == 1)
    }

    @Test
    fun onPageSwipe_shouldChangePage() {
        lateinit var viewPager: ViewPager2

        launchFragmentInHiltContainer<GamesGenreSelectionFragment> {
            viewPager = this.viewPager
        }

        assert(viewPager.currentItem == 0)

        viewPager.setCurrentItem(1, false)

        assert(viewPager.currentItem == 1)
    }

    @Test
    fun onBackToWelcomeScreenClick_shouldGoToWelcomeScreen() {
        lateinit var viewPager: ViewPager2

        launchFragmentInHiltContainer<GamesGenreSelectionFragment> {
            viewPager = this.viewPager
        }

        assert(viewPager.currentItem == 0)

        viewPager.setCurrentItem(1, false)

        assert(viewPager.currentItem == 1)

        onView(withId(R.id.tv_back_to_welcome_screen)).perform(click())

        assert(viewPager.currentItem == 0)
    }
}