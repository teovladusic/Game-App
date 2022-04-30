package com.example.gameapp.feature_games.presentation.game_details

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.gameapp.R
import com.example.gameapp.core.di.launchFragmentInHiltContainer
import com.example.gameapp.feature_games.domain.model.Game
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GameDetailsFragmentTests {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val game = Game(
        1,
        "game",
        "backgroundImage",
        4.0f,
        1,
        listOf("genre1", "genre2", "genre3"),
        listOf("screenshot1"),
        200
    )

    private lateinit var bundle: Bundle

    @Before
    fun setUp() {
        bundle = Bundle()
        bundle.putParcelable("game", game)
        hiltRule.inject()
    }

    @Test
    fun setGameDetailsTitle() {
        launchFragmentInHiltContainer<GameDetailsFragment>(bundle) { }
        onView(withId(R.id.tv_game_details_title)).check(
            matches(withText(game.name))
        )
    }

    @Test
    fun setGameDetailsGenres() {
        launchFragmentInHiltContainer<GameDetailsFragment>(bundle) { }
        val genres = "genre1, genre2"
        onView(withId(R.id.tv_game_details_genres)).check(
            matches(withText(genres))
        )
    }


    @Test
    fun setGameDetailsRating() {
        launchFragmentInHiltContainer<GameDetailsFragment>(bundle) { }
        val rating = "4.0"
        onView(withId(R.id.tv_game_details_rating)).check(
            matches(withText(rating))
        )
    }

    @Test
    fun setGameDetailsLikes() {
        launchFragmentInHiltContainer<GameDetailsFragment>(bundle) { }
        onView(withId(R.id.tv_likes)).check(
            matches(withText(game.suggestionsCount.toString()))
        )
    }

    @Test
    fun setGameDetailsRatingsCount() {
        launchFragmentInHiltContainer<GameDetailsFragment>(bundle) { }
        val ratingsCount = "${game.ratingsCount} ratings"
        onView(withId(R.id.tv_ratings_count)).check(
            matches(withText(ratingsCount))
        )
    }

    @Test
    fun setGameDetailsRatings() {
        launchFragmentInHiltContainer<GameDetailsFragment>(bundle) { }
        onView(withId(R.id.tv_rating)).check(
            matches(withText("4.0"))
        )
    }

    @Test
    fun setGameDetailsStarsCount() {
        lateinit var imgViewStar5: ImageView
        launchFragmentInHiltContainer<GameDetailsFragment>(bundle) {
            imgViewStar5 = this.requireActivity().findViewById(R.id.img_view_star5)
        }
        //if rating is 4, first 4 stars should be yellow (default), the rest gray

        assert(imgViewStar5.imageTintList == ColorStateList.valueOf(
            ContextCompat.getColor(
                ApplicationProvider.getApplicationContext(),
                R.color.gray
            )
        ))
    }
}