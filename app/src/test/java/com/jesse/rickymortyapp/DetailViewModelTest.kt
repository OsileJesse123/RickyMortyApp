package com.jesse.rickymortyapp


import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jesse.rickymortyapp.network.Location
import com.jesse.rickymortyapp.network.Origin
import com.jesse.rickymortyapp.network.RickMortyCharacter
import com.jesse.rickymortyapp.screens.detail.DetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailViewModelTest {

    @get: Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var character: RickMortyCharacter

    @Before
    fun setup(){
        val location = Location("Citadel of Ricks", "")
        val origin = Origin("unknown", "")
        character = RickMortyCharacter(
            "", listOf(), "Male", 0, "", location, "Morty Smith",
            origin, "Human", "Alive", "Non", ""
        )
    }

    @Test
    fun test_setCharacterDetails(){
        //Arrange
        val app = ApplicationProvider.getApplicationContext() as Application
        val viewModel = DetailViewModel(app, character)

        val listOfDetails = mutableListOf<String>()

        val name = app.applicationContext
            .getString(R.string.character_name, character.name)
        val status = app.applicationContext
            .getString(R.string.character_status, character.status)
        val species = app.applicationContext
            .getString(R.string.character_species, character.species)
        val location = app.applicationContext
            .getString(R.string.character_location, character.location.name)
        val origin = app.applicationContext
            .getString(R.string.character_origin, character.origin.name)
        val gender = app.applicationContext
            .getString(R.string.character_gender, character.gender)
        val type = if(character.type.isEmpty()) app.applicationContext
            .getString(R.string.non)
        else app.applicationContext
            .getString(R.string.character_type, character.type)

       listOfDetails.add(name)
       listOfDetails.add(status)
       listOfDetails.add(species)
       listOfDetails.add(location)
       listOfDetails.add(origin)
       listOfDetails.add(gender)
       listOfDetails.add(type)

        //Act
        val actualDetails = viewModel.characterDetails.value

        //Assert
        assert(actualDetails == listOfDetails)
    }
}