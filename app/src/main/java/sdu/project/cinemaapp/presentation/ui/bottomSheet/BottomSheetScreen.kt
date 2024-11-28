package sdu.project.cinemaapp.presentation.ui.bottomSheet

import android.util.Log
import androidx.compose.foundation.Image

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import sdu.project.cinemaapp.R

@Composable
fun BottomSheetScreen(
    movieId: Int,
    viewModel: BottomSheetViewModel = hiltViewModel()
) {

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }

    val movie by viewModel.movie.collectAsStateWithLifecycle()

    var isExpanded by remember { mutableStateOf(false) }
    var isWatched by remember (movie) { mutableStateOf(movie.isWatched) }

    Log.i("isWatched", "$isWatched")
    Log.i("movieBottom" ,"$movie")
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .height(132.dp)
                .padding(horizontal = 20.dp),
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(96.dp)
                    .height(132.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                movie.nameRu?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.graphiksemibold)),
                            color = Color(0xFF272727),
                        )
                    )
                }
                Text(
                    text = movie.year.toString() + ", " + (movie.genres?.firstOrNull()?.genre
                        ?: "no genres"),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.graphikregular)),
                        color = Color(0xFF838390),
                    )
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable {
                    isWatched = !isWatched
                    val updMovie = movie.copy(isWatched = isWatched)
                    viewModel.event(BottomSheetEvent.UpdateWatchedStatus(updMovie))
                }
                .border(width = 1.dp, color = Color(0x4DB5B5C9))
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                painter = painterResource(
                    if (isWatched) R.drawable.eye else R.drawable.eye_black
                ),
                contentDescription = "", modifier = Modifier
                    .size(20.dp)
            )
            Text(
                text = "Просмотрен",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable {
                    isExpanded = !isExpanded
                    //todo Добавить в коллецию
                }
                .border(width = 1.dp, color = Color(0x4DB5B5C9))
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.create_collection_icon),
                contentDescription = "", modifier = Modifier
                    .size(20.dp)
            )
            Text(
                text = "Добавить в коллецию",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)),
                    color = Color(0xFF272727),
                    textAlign = TextAlign.Center,
                )
            )
        }
        if (isExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(width = 1.dp, color = Color(0x4DB5B5C9))
                        .padding(horizontal = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    var checked by remember { mutableStateOf(false) }

                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = "Русское кино",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.graphikregular)),
                            color = Color(0xFF272727),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(width = 1.dp, color = Color(0x4DB5B5C9))
                        .padding(horizontal = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    var checked by remember { mutableStateOf(false) }

                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = "Хочу посмотреть",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.graphikregular)),
                            color = Color(0xFF272727),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(width = 1.dp, color = Color(0x4DB5B5C9))
                        .padding(horizontal = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    var checked by remember { mutableStateOf(false) }

                    Checkbox(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Gray,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(
                        text = "Любимое",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.graphikregular)),
                            color = Color(0xFF272727),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .border(width = 1.dp, color = Color(0x4DB5B5C9))
                        .clickable {
                            //todo create collection
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(61.dp))
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
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
        }
    }
}