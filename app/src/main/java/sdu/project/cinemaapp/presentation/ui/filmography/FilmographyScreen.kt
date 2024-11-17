package sdu.project.cinemaapp.presentation.ui.filmography

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.domain.model.Actor
import sdu.project.cinemaapp.domain.model.Movie
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp, vertical = 15.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(painter = painterResource(R.drawable.caret_left), contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        viewModel.event(navController, FilmographyEvent.OnBackClick)
                    })
            Text(
                text = "Фильмография",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = actor.nameRu,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                color = Color(0xFF272727),
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Отображение фильмов по профессиям
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(uniqueProfessions.size) { index ->
                val profession = uniqueProfessions[index]
                var isClicked by remember { mutableStateOf(false) }
                Button(
                    modifier = Modifier
                        .height(36.dp)
                        .background(
                            color = if(isClicked) Color(0xFF3D3BFF) else Color.Red,
                            shape = RoundedCornerShape(size = 56.dp)
                        )
                        .padding(horizontal = 5.dp),
                    onClick = {
                        isClicked = !isClicked
                        viewModel.event(
                            navController,
                            FilmographyEvent.LoadMovieByProfessionKey(profession)
                        )
                        Log.i("FilmographyScreen", "Clicked profession: $profession")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                ) {
                    var profession = profession.replace("_", " ")
                    Text(
                        text = profession.replaceFirstChar { it.uppercase() },
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.graphikregular)),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        // Отображение списка фильмов по выбранной профессии
        when (state) {
            is ScreenState.Initial -> {}
            is ScreenState.Loading -> {
                LoaderScreen()
            }

            is ScreenState.Success -> {
                FilmographyScreen(filmography)
            }

            is ScreenState.Error -> {
                ErrorScreen()
            }
        }
    }
}


@Composable
fun FilmographyScreen(
    filmography: List<Movie>,
) {
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(filmography) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Row(
        modifier = Modifier.height(132.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box() {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp)
                    .width(22.dp)
                    .height(12.dp)
                    .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)

            ) {
                Text(
                    text = movie.ratingKinopoisk.toString(),
                    style = TextStyle(
                        fontSize = 8.sp,
                        fontFamily = FontFamily(Font(R.font.graphikmedium)),
                        color = Color(0xFF272727),
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = movie.nameRu ?: "Нет названия",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                    color = Color(0xFF272727),
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = movie.year.toString() + ", " + movie.genres.joinToString { it.genre },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)),
                    color = Color(0xFF838390),
                )
            )
        }
    }
}
