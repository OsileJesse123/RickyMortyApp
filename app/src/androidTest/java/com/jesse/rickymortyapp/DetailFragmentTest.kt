package com.jesse.rickymortyapp

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.rickymortyapp.network.Location
import com.jesse.rickymortyapp.network.Origin
import com.jesse.rickymortyapp.network.RickMortyCharacter
import com.jesse.rickymortyapp.screens.detail.CharacterDetailAdapter
import com.jesse.rickymortyapp.screens.detail.DetailFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    private lateinit var rmCharacter: RickMortyCharacter

    @Before
    fun setup(){
        val location = Location("Earth", "https://rickandmortyapi.com/api/location/20")
        val origin = Origin("Alien Spa", "https://rickandmortyapi.com/api/location/64")
        rmCharacter = RickMortyCharacter("2018-01-10T18:20:41.703Z",
            listOf("https://rickandmortyapi.com/api/episode/27"), "Male", 361,
            "https://rickandmortyapi.com/api/character/avatar/361.jpeg", location,
            "Toxic Rick", origin, "Humanoid", "Dead", "Rick's Toxic Side",
            "https://rickandmortyapi.com/api/character/361")
    }

    @Test
    fun test_launchDetailFragmentAndScrollDown(){
        runBlocking {
            val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

            launchFragmentInContainer(bundleOf("character" to rmCharacter)){
                DetailFragment().also {
                    it.viewLifecycleOwnerLiveData.observeForever {
                            viewLifecycleOwner ->
                        if(viewLifecycleOwner != null){
                            navController.setGraph(R.navigation.nav_graph)
                            Navigation.setViewNavController(it.requireView(), navController)
                        }
                    }
                }
            }
            //This delay puts into consideration the time it will probably take the network to
            //respond to the request.
            delay(5000)
            onView(withId(R.id.detail_recycler)).perform(RecyclerViewActions
                .scrollToPosition<CharacterDetailAdapter.CharacterDetailViewHolder>(7))
        }
    }
}