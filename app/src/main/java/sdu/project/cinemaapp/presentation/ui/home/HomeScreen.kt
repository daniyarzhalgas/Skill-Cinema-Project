package sdu.project.cinemaapp.presentation.ui.home
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
import androidx.navigation.NavHostController
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.presentation.state.ScreenState
import sdu.project.cinemaapp.presentation.ui.components.HomeLazyRowListComponent
import sdu.project.cinemaapp.presentation.ui.screens.ErrorScreen
import sdu.project.cinemaapp.presentation.ui.screens.LoaderScreen
import sdu.project.cinemaapp.presentation.viewModel.SharedViewModel


@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    val state by viewModel.state.collectAsStateWithLifecycle()

    val premieres by viewModel.premieres.collectAsStateWithLifecycle()
    val popularAll by viewModel.comicsCollections.collectAsState()
    val popularMovies by viewModel.popularMovies.collectAsStateWithLifecycle()

    when (state) {
        is ScreenState.Initial -> {}
        is ScreenState.Loading -> LoaderScreen()
        is ScreenState.Error -> ErrorScreen()
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
                HomeLazyRowListComponent("Премьеры", premieres){
                    sharedViewModel.setDataList(premieres)
                    viewModel.event(navController, it)
                }
                HomeLazyRowListComponent("Комиксы", popularAll){
                    sharedViewModel.setDataList(popularAll)
                    viewModel.event(navController, it)
                }
                HomeLazyRowListComponent("Популярные фильмы", popularMovies){
                    sharedViewModel.setDataList(popularMovies)
                    viewModel.event(navController, it)
                }
            }
        }
    }
}


