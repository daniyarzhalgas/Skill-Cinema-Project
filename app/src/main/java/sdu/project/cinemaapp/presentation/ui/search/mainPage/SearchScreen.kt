package sdu.project.cinemaapp.presentation.ui.search.mainPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen
import sdu.project.cinemaapp.domain.model.Movie
import androidx.compose.ui.Modifier
import sdu.project.cinemaapp.presentation.ui.filmography.MovieItem
import sdu.project.cinemaapp.presentation.ui.screens.MovieItem

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val movies by viewModel.movies.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()


    SearchScreenContent(state, movies, searchText) { viewModel.event(navController, it) }


}

@Composable
fun SearchScreenContent(
    state: ScreenState,
    movies: List<Movie>?,
    searchText: String,
    onClick: (SearchEvent) -> Unit
) {
    val imeInsets = WindowInsets.ime
    val imeHeightPx = imeInsets.getBottom(LocalDensity.current)
    val imeHeightDp = with(LocalDensity.current) { imeHeightPx.toDp() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = imeHeightDp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(searchText) { onClick(it) }
        Spacer(modifier = Modifier.height(24.dp))

        when (state) {
            ScreenState.Error -> ErrorScreen()
            ScreenState.Initial -> {}
            ScreenState.Loading -> {
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
            ScreenState.Success -> {
                if (movies?.isEmpty() == true) {
                    Text(
                        text = "К сожалению, по вашему запросу ничего не найдено",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(movies!!) { movie ->
                            MovieItem(movie) { onClick(SearchEvent.OnMovieClick(movie.kinopoiskId))}
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onEvent: (SearchEvent) -> Unit
) {

    TextField(
        value = searchText,
        onValueChange = { newQuery ->
            run {
                onEvent(SearchEvent.OnSearchTextChange(newQuery))
            }
        },
        placeholder = {
            Text(
                text = "Фильмы, актеры, режиссеры",
                color = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xFFF2F2F2)),
        singleLine = true,
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .clickable {  },
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Gray,
            )
        },

        trailingIcon = {
            Row(horizontalArrangement = Arrangement.Center) {
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .height(20.dp)
                        .width(0.5.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Filter",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { onEvent(SearchEvent.OnFilterClick) } // Navigate to FilterScreen
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
        },

        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}