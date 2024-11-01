package sdu.project.cinemaapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.domain.model.Movie
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.components.HomeLazyRowListComponent
import sdu.project.cinemaapp.presentation.viewModel.MovieViewModel


@Composable
fun HomeScreen(
    onItemClick: (Movie) -> Unit,
    onClick: (String) -> Unit,
    viewModel: MovieViewModel
) {
    val scrollState = rememberScrollState()

    val state by viewModel.state.collectAsStateWithLifecycle()
    val premieres by viewModel.premieres.collectAsStateWithLifecycle()
    val popularAll by viewModel.comicsCollections.collectAsState()
    val popularMovies by viewModel.popularMovies.collectAsStateWithLifecycle()

    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(bottom = 70.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {

                Text(
                    text = "Skill Cinema",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.graphikbold)),
                    ),
                    modifier = Modifier.padding(start = 30.dp, top = 30.dp)
                )
                HomeLazyRowListComponent("Премьеры", premieres, onItemClick, onClick)
                HomeLazyRowListComponent("Комиксы", popularAll, onItemClick, onClick)
                HomeLazyRowListComponent("Популярные фильмы", popularMovies, onItemClick, onClick)
            }
        }
        is ScreenState.Error -> ErrorScreen()
    }
}

