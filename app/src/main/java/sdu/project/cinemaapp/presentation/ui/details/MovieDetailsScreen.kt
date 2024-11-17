package sdu.project.cinemaapp.presentation.ui.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.Image
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    movieId: Int?,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(movieId) {
        movieId?.let {
            viewModel.loadMovieDetails(it)
        }
    }

    val getMovie by viewModel.movie.collectAsStateWithLifecycle()
    val getActors by viewModel.actors.collectAsStateWithLifecycle()
    val getStaff by viewModel.staff.collectAsStateWithLifecycle()
    val getImages by viewModel.images.collectAsStateWithLifecycle()
    val getSimilarFilms by viewModel.similarFilms.collectAsStateWithLifecycle()


    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Success -> {
            MovieContent(
                getMovie,
                getActors,
                getStaff,
                getImages,
                getSimilarFilms,
                navController,
                viewModel
            )
            Log.i("MovieContent", "Getting Movie: ${getMovie.toString()}")
            Log.i("MovieContent", "Getting Actors: $getActors")
            Log.i("MovieContent", "Getting Staff: $getStaff")
            Log.i("MovieContent", "Getting Images: $getImages")
            Log.i("MovieContent", "Getting Similar Films: $getSimilarFilms")
        }

        is ScreenState.Error -> ErrorScreen()
    }

}

@Composable
fun MovieContent(
    movie: Movie?,
    actors: List<FilmStaff>,
    staff: List<FilmStaff>,
    images: List<Image>,
    similarFilms: List<SimilarMovie>,
    navController: NavHostController,
    viewModel: MovieDetailsViewModel
) {

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        movie?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            {

                AsyncImage(
                    model = movie.posterUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(27, 27, 27, 1),
                                    Color(27, 27, 27)
                                )
                            )
                        )
                )
                Image(painter = painterResource(R.drawable.caret_left), contentDescription = "",
                    modifier = Modifier
                        .size(35.dp)
                        .offset(x = 10.dp, y = 10.dp)
                        .align(Alignment.TopStart)
                        .clickable {
                            viewModel.event(navController, MovieDetailsEvent.OnBackClick)
                        }
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = movie.ratingKinopoisk.toString() + " " + movie.nameRu,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = movie.year.toString() + " " + movie.genres.joinToString(", ") { it.genre },
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = movie.countries.joinToString(", ") { it.country } + " " + movie.filmLength.toString() + " " + movie.ratingAgeLimits,
                        color = Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(25.dp)) {
                Text(
                    text = movie.shortDescription
                        ?: "Чукотский парень влюбляется \u2028в американскую вебкам-модель. Приз Венеции, Кристина Асмус \u2028в роли девушки мечты",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.graphikbold)),
                        color = Color(0xFF272727),
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = movie.description
                        ?: "Все меняется в жизни юного чукотского охотника Лёшки \u2028с появлением в поселке интернета. Он влюбляется — впервые и сильно — \u2028в молчаливую девушку...",
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily(Font(R.font.graphikregular)),
                        color = Color(0xFF272727),
                    ),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(14.dp))
                Header("В фильме снимались", actors.size)
                Spacer(modifier = Modifier.height(14.dp))
                StaffListView(staffs = actors, 4){
                    viewModel.event(navController, it)
                }
                Spacer(modifier = Modifier.height(14.dp))
                Header("Над фильмом работали", staff.size)
                Spacer(modifier = Modifier.height(14.dp))
                StaffListView(staffs = staff, countItem = 2){
                    viewModel.event(navController, it)
                }
                Spacer(modifier = Modifier.height(14.dp))
                Header("Галерея",images.size)
                Spacer(modifier = Modifier.height(14.dp))
                ListImages(images = images)
                Spacer(modifier = Modifier.height(14.dp))
                Header("Похожие фильмы", similarFilms.size)
                Spacer(modifier = Modifier.height(14.dp))
                SimilarMoviesList(similarFilms){
                    viewModel.event(navController, it)
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun Header(headerText: String, size: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = headerText,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                color = Color(0xFF272727),
            )
        )
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                //todo переход на экран актеров
            }) {
            Text(
                text = size.toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.graphikmedium)),
                    color = Color(0xFF3D3BFF),
                    textAlign = TextAlign.Center,
                )
            )
            Image(
                painter = painterResource(R.drawable.caret_left),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun SimilarMoviesList(similarMovies: List<SimilarMovie>, onClick: (event: MovieDetailsEvent) -> Unit) {
    if (similarMovies.isNotEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(similarMovies) { item ->
                MoviesItem(item){
                    onClick(MovieDetailsEvent.LoadMovie(item.filmId))
                }
            }

        }
    }
}

@Composable
fun MoviesItem(movie: SimilarMovie, onClick: () -> Unit ) {
    Column(modifier = Modifier
        .width(111.dp)
        .clickable {
            onClick()
        }) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier,
            contentScale = ContentScale.Crop
        )
        Text(
            text = movie.nameRu,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.graphikregular)),
                color = Color(0xFF272727),
            )
        )
        Text(
            text = movie.relationType,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.graphikregular)),
                color = Color(0xFF838390),
            )
        )
    }
}

@Composable
fun ListImages(images: List<Image>) {
    if (images.isNotEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(images) { item ->
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.height(108.dp),
                    contentScale = ContentScale.Crop
                )
            }

        }
    }
}


@Composable
fun StaffListView(staffs: List<FilmStaff>, countItem: Int, onClick: (event: MovieDetailsEvent) -> Unit) {
    if (staffs.isNotEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(staffs.chunked(countItem)) { item ->
                Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
                    item.forEach { staff ->
                        StaffItem(staff = staff){
                            Log.i("StaffItem", "StaffItem: $staff")
                            onClick(MovieDetailsEvent.LoadStaff(staff.staffId))
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun StaffItem(staff: FilmStaff, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .height(68.dp)
            .clickable {
                onClick(staff.staffId)
            },
        verticalAlignment = Alignment.CenterVertically
        , horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = staff.posterUrl,
            modifier = Modifier.fillMaxHeight(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                text = staff.nameRu,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)),// 400
                    color = Color(0xFF272727),
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = staff.professionText,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)), //400
                    color = Color(0xFF838390),
                )
            )
        }
    }
}