
package sdu.project.cinemaapp.presentation.ui.details

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.isFocusable
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
import sdu.project.cinemaapp.data.local.MockData
import sdu.project.cinemaapp.domain.model.FilmStaff
import sdu.project.cinemaapp.domain.model.MovieImage
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.domain.model.MovieCollection
import sdu.project.cinemaapp.domain.model.SimilarMovie
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.bottomSheet.BottomSheetScreen
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
    val movieCollection by viewModel.movieCollections.collectAsState()

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
                movieCollection
            ) {
                viewModel.event(navController, it)
            }
        }

        is ScreenState.Error -> ErrorScreen()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieContent(
    movie: Movie?,
    actors: List<FilmStaff>,
    staff: List<FilmStaff>,
    images: List<MovieImage>,
    similarFilms: List<SimilarMovie>,
    navController: NavHostController,
    movieCollections: List<MovieCollection>,
    onClick: (MovieDetailsEvent) -> Unit
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val scrollState = rememberScrollState()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    var likeState by remember(movie) {
        mutableStateOf(movie?.collectionName?.contains("Любимые") == true)
    }


    var noteState by remember(movie) {
        mutableStateOf(movie?.collectionName?.contains("Хочу посмотреть") == true)
    }

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
                                    Color(0x00000000),
                                    Color(0xFF1B1B1B)
                                )
                            )
                        )
                )
                Image(painter = painterResource(R.drawable.caret_left), contentDescription = "",
                    modifier = Modifier
                        .size(45.dp)
                        .offset(x = 10.dp, y = 10.dp)
                        .align(Alignment.TopStart)
                        .clickable {
                            onClick(MovieDetailsEvent.OnBackClick)
                        }
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = movie.ratingKinopoisk.toString() + " " + (movie.nameRu?.split(':')
                            ?.get(0) ?: "name not passed"),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.graphikmedium)),
                            color = Color(0xFFB5B5C9),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = movie.year.toString() + ", " + (movie.genres?.joinToString(", ") { it.genre }
                            ?: "no genres"),
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.graphikregular)),
                            color = Color(0xFFB5B5C9),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    val hour = (movie.filmLength?.div(60)).toString() + " ч"
                    val minute = (movie.filmLength?.rem(60)).toString() + " мин"
                    Text(
                        text = (movie.countries?.joinToString(", ") { it.country }
                            ?: "") + ", " + hour + " " + minute + "," + (movie.ratingAgeLimits?.substring(
                            3
                        ) ?: "") + "+",
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.graphikregular)),
                            color = Color(0xFFB5B5C9),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(if (likeState) R.drawable.like_orange else R.drawable.like),
                            contentDescription = "", modifier = Modifier
                                .wrapContentSize()
                                .clickable {
                                    likeState = !likeState
                                    onClick(
                                        MovieDetailsEvent.UpdateCollection(
                                            movie.kinopoiskId,
                                            "Любимые"
                                        )
                                    )
                                }
                        )

                        Image(
                            painter = painterResource(if (noteState) R.drawable.save_orange else R.drawable.save),
                            contentDescription = "",
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 16.dp)
                                .clickable {
                                    noteState = !noteState
                                    onClick(
                                        MovieDetailsEvent.UpdateCollection(
                                            movie.kinopoiskId,
                                            "Хочу посмотреть"
                                        )
                                    )
                                }
                        )


                        Image(
                            painter = painterResource(R.drawable.share),
                            contentDescription = "",
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(start = 16.dp)
                                .clickable { TODO() }
                        )
                        Spacer(Modifier.width(20.dp))
                        Image(
                            painter = painterResource(R.drawable.dots),
                            contentDescription = "",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    showBottomSheet = true
                                }
                        )

                    }
                }
            }

            Column(modifier = Modifier.padding(25.dp)) {
                Text(
                    text = movie.shortDescription
                        ?: MockData.shortDescription,
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
                        ?: MockData.description,
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
                val filteredActor = actors.filter { it.nameRu != "" }
                if (filteredActor.isNotEmpty()) {
                    Header("В фильме снимались", filteredActor.size) {
                        sharedViewModel.setDataList(filteredActor)
                        onClick(MovieDetailsEvent.NavigateToList("В фильме снимались"))

                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    StaffListView(staffs = filteredActor, 4) {
                        onClick(it)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                val filteredStaffs = staff.filter { it.nameRu != "" }
                if (filteredStaffs.isNotEmpty()) {
                    Header("Над фильмом работали", filteredStaffs.size) {
                        sharedViewModel.setDataList(filteredStaffs)
                        onClick(MovieDetailsEvent.NavigateToList("Над фильмом работали"))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    StaffListView(staffs = filteredStaffs, countItem = 2) {
                        onClick(it)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                if (images.isNotEmpty()) {
                    Header("Галерея", images.size) {
                        sharedViewModel.setDataList(images)
                        navController.navigate("gallery_screen")
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    ListImages(images = images)
                }

                Spacer(modifier = Modifier.height(14.dp))
                if (similarFilms.isNotEmpty()) {
                    Header("Похожие фильмы", similarFilms.size) {
                        sharedViewModel.setDataList(similarFilms)
                        onClick(MovieDetailsEvent.NavigateToList("Похожие фильмы"))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    SimilarMoviesList(similarFilms) {
                        onClick(it)
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }


        if(showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,

            ) {
                BottomSheetScreen(movie!!.kinopoiskId, movieCollections)
            }
        }
    }
}


@Composable
fun Header(headerText: String, size: Int, onClick: (String) -> Unit) {

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
                onClick(headerText)
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
                painter = painterResource(R.drawable.caret_right),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color(0xFF3D3BFF))
            )
        }
    }
}

@Composable
fun SimilarMoviesList(
    similarMovies: List<SimilarMovie>,
    onClick: (event: MovieDetailsEvent) -> Unit
) {
    if (similarMovies.isNotEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(similarMovies) { item ->
                MoviesItem(item) {
                    onClick(it)
                }
            }

        }
    }
}

@Composable
fun MoviesItem(movie: SimilarMovie, onClick: (MovieDetailsEvent) -> Unit) {
    Column(modifier = Modifier
        .width(111.dp)
        .height(220.dp)
        .clickable {
            onClick(MovieDetailsEvent.LoadMovie(movie.filmId))
        }) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier.height(170.dp),
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
fun ListImages(images: List<MovieImage>) {
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
fun StaffListView(
    staffs: List<FilmStaff>,
    countItem: Int,
    onClick: (event: MovieDetailsEvent) -> Unit
) {

    if (staffs.isNotEmpty()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(staffs.chunked(countItem)) { item ->
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item.take(16).forEach { staff ->
                        if (staff.nameRu != "") {
                            StaffItem(staff = staff) {
                                Log.i("StaffItem", "StaffItem: $staff")
                                onClick(MovieDetailsEvent.LoadStaff(staff.staffId))
                            }
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
            .height(75.dp)
            .clickable {
                onClick(staff.staffId)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = staff.posterUrl,
            modifier = Modifier.fillMaxHeight(),
            contentDescription = null,
            contentScale = ContentScale.FillHeight
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

//todo fix the Header navigation