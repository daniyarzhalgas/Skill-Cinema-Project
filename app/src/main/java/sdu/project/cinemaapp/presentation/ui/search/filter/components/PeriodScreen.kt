package sdu.project.cinemaapp.presentation.ui.search.filter.components

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import sdu.project.cinemaapp.presentation.ui.search.filter.FilterEvent
import sdu.project.cinemaapp.presentation.ui.search.filter.FilterViewModel


@Composable
fun PeriodScreen(navController: NavHostController, viewModel: FilterViewModel) {
    val yearsFirst by viewModel.visibleYearsFirst.collectAsState()
    val yearsSecond by viewModel.visibleYearsSecond.collectAsState()
    val selectedYearFrom by viewModel.yearFrom.collectAsState()
    val selectedYearTo by viewModel.yearTo.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Период",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }

        Text(
            text = "Искать в период с",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 10.dp),
            color = Color.Gray
        )

        YearSelector(
            selectedYear = selectedYearFrom,
            isYearFrom = true,
            years = yearsFirst,
        ){viewModel.event(navController, it)}


        Text(
            text = "Искать в период до",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 10.dp),
            color = Color.Gray
        )

        YearSelector(
            selectedYear = selectedYearTo,
            isYearFrom = false,
            years = yearsSecond
        ){viewModel.event(navController, it)}

        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {
                viewModel.event(navController, FilterEvent.OnBackClicked)
                Log.i("", "$selectedYearTo   $selectedYearFrom")
            },
            modifier = Modifier
                .width(125.dp)
                .height(48.dp)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F4DFF))
        ) {
            Text(
                text = "Выбрать",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Composable
fun YearSelector(
    years: List<Int>,
    isYearFrom: Boolean,
    selectedYear: Int,
    onYearSelected: (FilterEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${years.first()} - ${years.last()}",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = Color(0xFF3D3BFF),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onYearSelected(FilterEvent.NavigateYears(isYearFrom, backward = true)) }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Previous")
            }
            IconButton(onClick = { onYearSelected(FilterEvent.NavigateYears(isYearFrom,backward = false)) }) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }

        // Grid
        val rows = years.chunked(3)
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.forEach { year ->
                    Text(
                        text = year.toString(),
                        modifier = Modifier
                            .clickable { onYearSelected(FilterEvent.OnYearSelected(isYearFrom, year)) }
                            .padding(4.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (year == selectedYear) Color(0xFF4F4DFF) else Color.Black
                    )
                }
            }
        }
    }
}
