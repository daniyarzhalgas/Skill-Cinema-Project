package sdu.project.cinemaapp.presentation.ui.profile

import android.util.Log
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
import androidx.compose.runtime.getValue
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
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.components.ItemCard
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val movies by viewModel.watched.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .padding(horizontal = 26.dp, vertical = 56.dp)
            .fillMaxSize()
    ) {
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
//                    fontWeight = FontWeight(600),
                    color = Color(0xFF272727),
                )
            )
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    //todo navigate to list watched
                }) {
                Text(
                    text = "15",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.graphikmedium)),
//                        fontWeight = FontWeight(500),
                        color = Color(0xFF3D3BFF),
                        textAlign = TextAlign.Center,
                    )
                )
                Image(painter = painterResource(R.drawable.caret_right), contentDescription = "")
            }
        }

        // todo fetching movies

        when (state) {
            ScreenState.Initial -> {}
            ScreenState.Error -> ErrorScreen()
            ScreenState.Loading -> LoaderScreen()
            ScreenState.Success -> {
                ListMovies(movies = movies) {
                    viewModel.event(it)
                }
            }
        }
//

        Spacer(modifier = Modifier.height(36.dp))
        Text(
            text = "Коллекции",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.graphikbold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF272727),
            )
        )
        Spacer(modifier = Modifier.height(28.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Delete",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        //todo navigate to create collection
                    }
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

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            CollectionCard(R.drawable.like, "Любимые", 15)
            Spacer(modifier = Modifier.width(20.dp))
            CollectionCard(R.drawable.save, "Хочу посмотреть", 15)
        }
        Spacer(modifier = Modifier.height(20.dp))
        CollectionCard(R.drawable.like, "Русское кино", 15)
    }
}

@Composable
fun CollectionCard(image: Int, title: String, count: Int) {
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
//            .padding(start = 43.5.dp, top = 30.5.dp, end = 43.5.dp, bottom = 27.5.dp)
            .clickable {
                //todo navigate to collection
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
                    text = count.toString(),
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
    LazyRow {
        item {
            Spacer(Modifier.width(30.dp))
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
