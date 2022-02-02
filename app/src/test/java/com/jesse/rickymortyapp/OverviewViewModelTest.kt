package com.jesse.rickymortyapp

import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jesse.rickymortyapp.network.*
import com.jesse.rickymortyapp.screens.overview.OverviewViewModel
import com.jesse.rickymortyapp.screens.overview.RickMortyState
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class OverviewViewModelTest {

    @get:Rule
    val coroutineDispatcherRule = CoroutineDispatcherRule()

    @get: Rule
    val instantTaskExecutionRule = InstantTaskExecutorRule()

    private lateinit var rickMortyObjectAlive: RickMortyObject
    private lateinit var rickMortyObjectDead: RickMortyObject
    private lateinit var aliveCharacter: RickMortyCharacter
    private lateinit var deadCharacter: RickMortyCharacter

    @Before
    fun setup(){
        val info = Info(0,"",0,"")
        val origin = Origin("","")
        val location = Location("", "")

        aliveCharacter = RickMortyCharacter("", listOf(),"", 0,"",
            location, "", origin, "", "Alive", "", "")
        deadCharacter = RickMortyCharacter("", listOf(),"", 0,"",
            location, "", origin, "", "Dead", "", "")

        val rickMortyCharactersAlive = listOf(aliveCharacter)
        val rickMortyCharactersDead = listOf(deadCharacter)

        rickMortyObjectAlive = RickMortyObject(info, rickMortyCharactersAlive)
        rickMortyObjectDead = RickMortyObject(info, rickMortyCharactersDead)

    }

    @Test
    fun test_getAllRickMortyCharacters(){
        runBlockingTest {
            //Arrange
            val retrofitService: RickyMortyService = mock{
                whenever(it.getCharacters()).thenReturn(rickMortyObjectAlive)
            }
            val viewModel = OverviewViewModel(retrofitService)

            //Act
            viewModel.setCharacters("All")

            //Assert
            verify(retrofitService, times(2)).getCharacters()
            assertEquals(rickMortyObjectAlive.results, viewModel.characters.value)
            assert(viewModel.state.value == RickMortyState.SUCCESSFUL)
        }
    }

    @Test
    fun test_getAliveRickMortyCharacters(){
        runBlockingTest {
            //Arrange
            val status = "Alive"
            rickMortyObjectAlive.results
            val retrofitService: RickyMortyService = mock{
                whenever(it.getCharactersByStatus(status)).thenReturn(rickMortyObjectAlive)
            }
            val viewModel = OverviewViewModel(retrofitService)

            //Act
            viewModel.setCharacters(status)
            val characters = viewModel.characters.value
            val state = viewModel.state.value

            //Assert
            verify(retrofitService).getCharactersByStatus(argThat {
                status == "Alive"
            })
            assertEquals(characters, rickMortyObjectAlive.results)
            assert(characters?.get(0)?.status == status)
            assert(state == RickMortyState.SUCCESSFUL)
        }
    }

    @Test
    fun test_getDeadRickMortyCharacters(){
        runBlockingTest {
            //Arrange
            val status = "Dead"
            val retrofitService: RickyMortyService = mock{
                whenever(it.getCharactersByStatus(any())).thenReturn(rickMortyObjectDead)
            }
            val viewModel = OverviewViewModel(retrofitService)

            //Act
            viewModel.setCharacters(status)
            val characters = viewModel.characters.value
            val state = viewModel.state.value

            //Assert
            verify(retrofitService).getCharactersByStatus(argThat {
                status == "Dead"
            })
            assert(characters == rickMortyObjectDead.results)
            assert(characters?.get(0)?.status == status)
            assert(state == RickMortyState.SUCCESSFUL)
        }
    }

    @Test
    fun test_setRickMortyCharactersFailed(){
       runBlockingTest {

           //Arrange
           val characterTypes = listOf("All", "Alive", "Dead")
           val retrofitService: RickyMortyService = mock{
               whenever(it.getCharacters()).thenReturn(null)
               whenever(it.getCharactersByStatus("Alive")).thenReturn(null)
               whenever(it.getCharactersByStatus("Dead")).thenReturn(null)
           }
           val viewModel = OverviewViewModel(retrofitService)

           //Act
           for (type in characterTypes){
               viewModel.setCharacters(type)
               delay(3000)
           }
           val characters = viewModel.characters.value
           val state = viewModel.state.value

           //Assert
           verify(retrofitService, times(2)).getCharacters()
           verify(retrofitService, times(2)).getCharactersByStatus(any())
           assert(characters?.isEmpty() ?: false)
           assert(state == RickMortyState.ERROR)
       }
    }
}