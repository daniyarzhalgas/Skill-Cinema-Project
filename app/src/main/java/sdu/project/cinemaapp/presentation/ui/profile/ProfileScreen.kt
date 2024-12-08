package sdu.project.cinemaapp.presentation.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.components.DialogPage
import sdu.project.cinemaapp.presentation.ui.components.ItemCard
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel


@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val movies by viewModel.watched.collectAsStateWithLifecycle()
    val openAlertDialog = remember { mutableStateOf(false) }
    val collectionTitle = viewModel.collectionTitle
    val movieCollections by viewModel.movieCollection.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 26.dp, vertical = 36.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        // Header Section
        item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Просмотрено",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                            color = Color(0xFF272727),
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            viewModel.event(
                                navController,
                                ProfileEvent.NavigateToListPage("Просмотрено")
                            )
                            sharedViewModel.setDataList(movies)
                        }
                    ) {
                        Text(
                            text = movies.size.toString(),
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
        }

        // Watched Movies Section
        item {
            when (state) {
                ScreenState.Initial -> {}
                ScreenState.Error -> ErrorScreen()
                ScreenState.Loading -> LoaderScreen()
                ScreenState.Success -> {
                    ListMovies(movies = movies) {
                        viewModel.event(navController, it)
                    }

                }
            }
        }


        item {
            Text(
                text = "Коллекции",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.graphikbold)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF272727),
                )
            )
        }

        // Create Collection Button
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.clickable {
                    openAlertDialog.value = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Создать свою коллекцию",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.graphikregular)),
                        color = Color(0xFF272727),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }

        // Predefined Collections
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CollectionCard(
                    image = R.drawable.like,
                    title = "Любимые",
                    viewModel
                ) { viewModel.event(navController, it) }
                CollectionCard(
                    image = R.drawable.save,
                    title = "Хочу посмотреть",
                    viewModel
                ) { viewModel.event(navController, it) }
            }
        }

        // Dynamic Collections
        items(movieCollections.chunked(2), key = { it.hashCode() }) { chunk ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                chunk.forEach { item ->
                    CollectionCard(
                        image = R.drawable.create_collection_icon,
                        title = item.collectionName,
                        viewModel = viewModel
                    ) { viewModel.event(navController, it) }
                }
            }
        }
    }

    // Alert Dialog
    if (openAlertDialog.value) {
        DialogPage(
            text = collectionTitle,
            onTextChange = { viewModel.event(navController, ProfileEvent.OnTextChange(it)) },
            onDismissRequest = { openAlertDialog.value = false },
            onCreateRequest = {
                openAlertDialog.value = false
                viewModel.event(navController, ProfileEvent.CreateCollection(collectionTitle))
            }
        )
    }
}

@Composable
fun CollectionCard(
    image: Int,
    title: String,
    viewModel: ProfileViewModel,
    onClick: (ProfileEvent) -> Unit
) {

    LaunchedEffect(title) {
        viewModel.getCollectionCount(title)
    }

    val count by viewModel.collectionCount.collectAsStateWithLifecycle()
    val collectionCount = count[title] ?: 0
    Box(
        Modifier
            .border(
                width = 1.dp,
                color = Color(0xFF000000),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .padding(0.5.dp)
            .width(146.dp)
            .height(146.dp)
            .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp))
            .clickable {
                onClick(ProfileEvent.NavigateToCollection(title))
            }
    )
    {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(image), contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF272727),
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(17.dp)
                    .background(
                        color = Color(0xFF3D3BFF),
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = collectionCount.toString(),
                    style = TextStyle(
                        fontSize = 8.sp,
                        fontFamily = FontFamily(Font(R.font.graphikmedium)),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}

@Composable
fun ListMovies(movies: List<Movie>, onClick: (ProfileEvent) -> Unit) {
    if (movies.isNotEmpty()) {
        LazyRow {
            item {
                Spacer(Modifier.width(10.dp))
            }
            items(movies.take(10)) { movie ->
                movie.posterUrlPreview?.let {
                    ItemCard(
                        movie.kinopoiskId,
                        it,
                        movie.ratingKinopoisk,
                        movie.nameRu ?: "Null",
                        movie.genres,
                        null
                    ) {
                        onClick(ProfileEvent.NavigateToMovie(movie.kinopoiskId))
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier.padding(top = 60.dp)
                ) {
                    Button(
                        onClick = { onClick(ProfileEvent.DeleteAllWatchedMovies) },
                        modifier = Modifier
                            .wrapContentSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                        )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = Color(0xFF3D3BFF)
                            )
                            Text(
                                text = "Очистить\nисторию",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.graphikregular)),
                                    color = Color(0xFF272727),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
