package com.example.gameapp.feature_games.presentation.games

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.gameapp.R
import com.example.gameapp.core.di.launchFragmentInHiltContainer
import com.example.gameapp.core.util.atPosition
import com.example.gameapp.feature_games.data.local.GenresDataStoreFakeImpl
import com.example.gameapp.feature_games.data.repository.GamesRepositoryFakeImpl
import com.squareup.javawriter.JavaWriter.type
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@HiltAndroidTest
class GamesFragmentTests {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    /**
     * Throws illegal state exception (view does not have a navController set)
     */

    /*@Test
    fun onGenreNameNotSet_shouldReturnToGenreSelection() {
        val navController = mock(NavController::class.java)

        navController.navigate(R.id.gamesFragment)

        GenresDataStoreFakeImpl.genreName = ""

        launchFragmentInHiltContainer<GamesFragment> {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(this.requireView(), navController)
        }

        val action = GamesFragmentDirections.actionGamesFragmentToGamesGenreSelectionFragment()

        verify(navController).navigate(action)
    }*/


    /**
     * Test never finishes
     */
    /* @Test
     fun onGetGamesSuccess_shouldSetGamesToAdapter() = runTest {
         GenresDataStoreFakeImpl.genreName = "indie"
         launchFragmentInHiltContainer<GamesFragment> { }

         onView(withId(R.id.rec_view_games))
             .check(
                 ViewAssertions.matches(
                     atPosition(
                         0,
                         ViewMatchers.hasDescendant(ViewMatchers.withText("game1"))
                     )
                 )
             )
     }*/

}