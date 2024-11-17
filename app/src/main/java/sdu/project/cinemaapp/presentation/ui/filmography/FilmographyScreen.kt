package sdu.project.cinemaapp.presentation.ui.filmography

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel

@Composable
fun FilmographyScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel = hiltViewModel(),
    viewModel: FilmographyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val filmography by viewModel.filmography.collectAsStateWithLifecycle()

    // Получаем объект Actor из sharedViewModel
    val actor = sharedViewModel.getDataOfType<Actor>()

    val uniqueProfessions = actor.films.map { it.professionKey }.distinct()
    Log.i("FilmographyScreen", "Unique Professions: $uniqueProfessions")
    Column(modifier = Modifier.fillMaxWidth()) {
        // Отображение фильмов по профессиям
        LazyRow {
            items(uniqueProfessions.size) { index ->
                val profession = uniqueProfessions[index]
                Button(
                    onClick = {
                        viewModel.event(
                            navController,
                            FilmographyEvent.LoadMovieByProfessionKey(profession)
                        )
                        Log.i("FilmographyScreen", "Clicked profession: $profession")
                    }
                ) {
                    Text(text = profession)
                }
            }
        }

        // Отображение списка фильмов по выбранной профессии
        when (state) {
            is ScreenState.Initial -> {}
            is ScreenState.Loading -> {
                LoaderScreen()
            }

            is ScreenState.Success -> {
                LazyColumn {
                    item { Button(onClick = {viewModel.event(navController, FilmographyEvent.OnBackClick)}) {
                        Text(text = "Back")
                    } }
                    items(filmography) { movie ->
                        Text(movie.nameRu ?: "Нет названия")
                    }
                }
            }

            is ScreenState.Error -> {
                ErrorScreen()
            }
        }
    }
}