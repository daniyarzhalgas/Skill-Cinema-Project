package sdu.project.cinemaapp.presentation.ui.actor

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
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
fun ActorScreen(
    navController: NavHostController,
    actorId: Int,
    viewModel: ActorViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(actorId) {
        viewModel.loadActorDetails(actorId)
    }

    val actor by viewModel.actor.collectAsStateWithLifecycle()
    val movies by viewModel.movies.collectAsStateWithLifecycle()

    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Error -> ErrorScreen()
        is ScreenState.Success -> {
            actor?.let {
                Log.i("ActorScreen", "Getting Actor: $actor")
                ActorContent(actor!!, movies, navController, viewModel)
            }
        }
    }

}

@Composable
fun ActorContent(
    actor: Actor,
    movies: List<Movie>,
    navController: NavHostController,
    viewModel: ActorViewModel,
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp, vertical = 20.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.caret_left), contentDescription = "",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 10.dp, bottom = 10.dp)
                .size(30.dp)
                .clickable {
                    viewModel.event(navController, ActorEvent.OnBackClick)
                }
        )
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = actor.posterUrl, contentDescription = "",
                modifier = Modifier
                    .height(201.dp)
                    .width(146.dp)
            )
            Column {
                Text(
                    text = actor.nameRu,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                        color = Color(0xFF272727),
                    )
                )

                Text(
                    text = actor.profession,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.graphikregular)),
                        color = Color(0xFF838390),
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Лучшее",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                    color = Color(0xFF272727),
                )
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    sharedViewModel.setDataList(movies)
                    viewModel.event(navController, ActorEvent.NavigateToList("Лучшее"))
                }) {
                Text(
                    text = "Все",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.graphikmedium)),
                        color = Color(0xFF3D3BFF),
                        textAlign = TextAlign.Center,
                    )
                )
                Image(
                    painter = painterResource(R.drawable.caret_right),
                    contentDescription = ""
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))


        ActorFilms(movies){movies ->
            viewModel.event(navController, ActorEvent.OnMovieClick(movies))
        }


        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Фильмография",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.graphikbold)),
                    color = Color(0xFF272727),
                )
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    sharedViewModel.setData(actor)
                    sharedViewModel.setMovies(movies)
                    viewModel.event(navController, ActorEvent.OnFilmographyClick)
                }) {
                Text(
                    text = "К списку",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.graphikmedium)),
                        color = Color(0xFF3D3BFF),
                        textAlign = TextAlign.Center,
                    )
                )
                Image(
                    painter = painterResource(R.drawable.caret_right),
                    contentDescription = ""
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = actor.films.size.toString() + " фильма",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.graphikregular)),
                color = Color(0xFF838391),
            )
        )
    }
}


@Composable
fun ActorFilms(movies: List<Movie>, onItemClick: (Int) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(movies.take(10)) { movie ->
            FilmItem(movie) { onItemClick(movie.kinopoiskId) }
        }
    }
}


@Composable
fun FilmItem(movie: Movie, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .width(111.dp)
            .clickable {
                onClick(movie.kinopoiskId)
            },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            model = movie.posterUrlPreview,
            contentDescription = "",
            modifier = Modifier
                .width(111.dp)
                .height(156.dp),
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                text = movie.nameRu,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)),
                    color = Color(0xFF272727),
                )
            )
            Text(
                text = movie.genres.firstOrNull()?.genre ?: "no genre",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)),
                    color = Color(0xFF838390),
                )
            )
        }

    }

}