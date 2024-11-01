package sdu.project.cinemaapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.domain.model.Movie


@Composable
fun HomeLazyRowListComponent(
    title: String,
    movies: List<Movie>,
    onItemClick: (Movie) -> Unit,
    onClick: (String) -> Unit
) {

    Column{
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.graphiksemibold))
                )
            )
            TextButton(onClick = { onClick(title) }, Modifier.background(Color.White)) {
                Text(
                    text = "Все", modifier = Modifier.padding(end = 26.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.graphikmedium)),
                        color = Color(0xFF3D3BFF),
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
        LazyRow {
            item {
                Spacer(Modifier.width(30.dp))
            }
            items(movies.take(10)) { movie ->
                MovieItemCard(movie, onClick = onItemClick)
            }
            item {
                Column(
                    modifier = Modifier
                        .height(150.dp)
                        .padding(end = 30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(
                        onClick = {onClick(title)} ,
                        modifier = Modifier
                            .size(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                        )
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillParentMaxSize()
                                .border(1.dp, color = Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Next",
                                tint = Color(0xFF3D3BFF)
                            )
                        }
                    }
                    TextButton(onClick = {onClick(title)}){
                        Text(text = "Показать все")
                    }

                }
            }
        }
    }
}