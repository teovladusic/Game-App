package com.example.gameapp.feature_games.presentation.games_genre_selection

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.viewpager2.widget.ViewPager2
import com.example.gameapp.R
import com.example.gameapp.core.di.launchFragmentInHiltContainer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.gameapp.core.util.atPosition
import com.example.gameapp.feature_games.data.repository.GenresRepositoryFakeImpl
import com.example.gameapp.feature_games.presentation.games_genre_selection.select_games_genre.GenresAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@HiltAndroidTest
@ExperimentalCoroutinesApi
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

        onView(withId(R.id.img_view_back_to_welcome_screen)).perform(click())

        assert(viewPager.currentItem == 0)
    }

    @Test
    fun getGenresStatusIsError_showErrorMessage() = runTest {
        lateinit var viewPager: ViewPager2

        GenresRepositoryFakeImpl.shouldReturnError = true

        launchFragmentInHiltContainer<GamesGenreSelectionFragment> {
            viewPager = this.viewPager
        }

        viewPager.setCurrentItem(1, false)

        onView(withText("error message")).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun getGenresStatusIsSuccess_showGenresList() = runTest {
        lateinit var viewPager: ViewPager2

        GenresRepositoryFakeImpl.shouldReturnError = false
        GenresRepositoryFakeImpl.delayTime = 0L

        launchFragmentInHiltContainer<GamesGenreSelectionFragment> {
            viewPager = this.viewPager
        }

        viewPager.setCurrentItem(1, false)

        onView(withId(R.id.rec_view_genres))
            .check(matches(atPosition(0, hasDescendant(withText("genre1")))))

        onView(withId(R.id.rec_view_genres))
            .check(matches(atPosition(1, hasDescendant(withText("genre2")))))
    }

    @Test
    fun getGenresStatusIsLoading_showLoading() {
        lateinit var viewPager: ViewPager2

        GenresRepositoryFakeImpl.shouldReturnError = false
        GenresRepositoryFakeImpl.delayTime = 10000

        launchFragmentInHiltContainer<GamesGenreSelectionFragment> {
            viewPager = this.viewPager
        }

        viewPager.setCurrentItem(1, false)

        onView(withId(R.id.lottie_animation_loading))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onConfirmGenre_shouldNavigateToGamesFragment() {
        lateinit var viewPager: ViewPager2

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        GenresRepositoryFakeImpl.shouldReturnError = false

        launchFragmentInHiltContainer<GamesGenreSelectionFragment> {
            viewPager = this.viewPager
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(requireView(), navController)
            navController.navigate(R.id.gamesGenreSelectionFragment)
        }

        viewPager.setCurrentItem(1, false)

        onView(withId(R.id.rec_view_genres)).perform(
            RecyclerViewActions.actionOnItemAtPosition<GenresAdapter.GenresViewHolder>(0, click())
        )

        onView(withId(R.id.fab_confirm_genre))
            .perform(click())

        assert(navController.currentDestination?.id == R.id.gamesFragment)
    }
}