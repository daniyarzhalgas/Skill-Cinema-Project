package sdu.project.cinemaapp.presentation.ui.search.filter.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.navigation.NavHostController
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.presentation.ui.components.ShowContent
import sdu.project.cinemaapp.presentation.ui.search.mainPage.SearchEvent
import sdu.project.cinemaapp.presentation.ui.search.mainPage.SearchViewModel


@Composable
fun FilterScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    FilterPage(viewModel) { viewModel.event(navController, it) }
}

@Composable
fun FilterPage(viewModel: SearchViewModel, onClick: (SearchEvent) -> Unit) {
    val selectedCountry by viewModel.country.collectAsState()
    val selectedGenre by viewModel.genre.collectAsState()
    val selectedYearFrom by viewModel.yearFrom.collectAsState()
    val selectedYearTo by viewModel.yearTo.collectAsState()
    val sliderValues by viewModel.sliderValues.collectAsState()


    val tabs = viewModel.tabs
    val tabs2 = viewModel.tabs2

    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val selectedTabSecond by viewModel.selectedTabSecond.collectAsState()

    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp),
                horizontalArrangement = Arrangement.spacedBy(86.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onClick(SearchEvent.OnBackClicked) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.caret_left),
                        contentDescription = "not watched list",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Text(
                    text = "Настройки поиска",
                    style = TextStyle(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        item {
            ShowContent(
                tabs = tabs,
                selectedTab = selectedTab.displayName,
                isFirst = true,
                title = "Показывать"
            ){ onClick(it) }
        }
        item {
            Sorting("Страна", selectedCountry) { onClick(SearchEvent.OnCountryClicked) }
            CustomDivider()
            Sorting("Жанр", selectedGenre) {onClick(SearchEvent.OnGenreClicked)}
            CustomDivider()
            Sorting("Год", "c $selectedYearFrom до $selectedYearTo") {onClick(SearchEvent.OnYearClicked)}
            CustomDivider()
            Sorting("Рейтинг", "любой") {}
        }
        item {
            RangeSliderExample(sliderValues){onClick(it)}
            CustomDivider()
            ShowContent(
                tabs = tabs2,
                selectedTab = selectedTabSecond.displayName,
                isFirst = false,
                title = "Сортировать"
            ){onClick(it)}
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(

                    onClick = { Log.i("FilterScreen", "clicked")
                        onClick(SearchEvent.OnFilterUsed) },
                    modifier = Modifier.align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text(
                        "Применить",
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun Sorting(category: String, type: String, onClick: () -> Unit) {

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .fillMaxWidth(1f)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = category)
        Text(text = type, color = Color.Gray)
    }
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun CustomDivider() {
    HorizontalDivider(
        modifier = Modifier
            .height(0.5.dp)
            .fillMaxWidth()
            .padding(horizontal = 26.dp),
        color = Color.Gray
    )
}

@Composable
fun RangeSliderExample(sliderValues: ClosedFloatingPointRange<Float>, onClick: (SearchEvent) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RangeSlider(
            value = sliderValues,
            onValueChange = { newValues ->
                onClick(SearchEvent.OnSliderValueChange(newValues))},
            valueRange = 1f..10f,
            steps = 0,
            onValueChangeFinished = { /* По завершению изменения */ },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.Blue,
                inactiveTrackColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${sliderValues.start.toInt()}", color = Color.Gray)
            Text(text = "${sliderValues.endInclusive.toInt()}", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

