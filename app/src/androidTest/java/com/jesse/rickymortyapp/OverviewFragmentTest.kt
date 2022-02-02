package com.jesse.rickymortyapp



import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.rickymortyapp.screens.overview.OverviewCharacterAdapter
import com.jesse.rickymortyapp.screens.overview.OverviewFragment
import com.jesse.rickymortyapp.utils.CategoriesList
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OverviewFragmentTest {

    @Test
    fun test_launchOverviewFragmentAndSelectAllCategories(){
        runBlocking {
            //The 5 second delay puts into consideration how long it'll take
            //the network to respond to the GET request

            launchFragmentInContainer<OverviewFragment>()

            //Select All Category
            delay(5000)
            onView(withId(R.id.character_recycler)).perform(RecyclerViewActions
                .scrollToPosition<OverviewCharacterAdapter.CharacterViewHolder>(19))

            //Select Alive Category
            onView(withId(R.id.category_recycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition<OverviewCharacterAdapter
                .CharacterViewHolder>(1, click()))
            delay(5000)
            onView(withId(R.id.character_recycler)).perform(RecyclerViewActions.scrollToPosition<
                    OverviewCharacterAdapter.CharacterViewHolder>(19))
            assert(CategoriesList.currentCategoryItemPosition == 1)

            //Select Dead Category
            onView(withId(R.id.category_recycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition<OverviewCharacterAdapter
                .CharacterViewHolder>(2, click()))
            delay(5000)
            onView(withId(R.id.character_recycler)).perform(RecyclerViewActions
                .scrollToPosition<OverviewCharacterAdapter.CharacterViewHolder>(19))
            assert(CategoriesList.currentCategoryItemPosition == 2)
        }
    }

    @Test
    fun test_selectCharacterItemAndNavigateToDetailFragment(){
        runBlocking{
            //Note that when using TestNavHostController use ApplicationProvider to
            //get context and not InstrumentationRegistry
            val scenario = launchFragmentInContainer<OverviewFragment>()
            val navController = TestNavHostController(
                ApplicationProvider
                    .getApplicationContext())

            scenario.onFragment{
                navController.setGraph(R.navigation.nav_graph)
                Navigation.setViewNavController(it.requireView(), navController)
            }
            delay(5000)
            onView(withId(R.id.character_recycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition<OverviewCharacterAdapter
                .CharacterViewHolder>(13, click()))

            assert(navController.currentDestination?.id == R.id.detailFragment)
        }
    }
}