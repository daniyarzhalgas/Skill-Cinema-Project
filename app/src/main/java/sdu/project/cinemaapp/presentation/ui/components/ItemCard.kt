package sdu.project.cinemaapp.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import sdu.project.cinemaapp.R
import sdu.project.cinemaapp.domain.model.Genre

@Composable
fun ItemCard(
    id: Int,
    posterUrl: String,
    ratingKinopoisk: Double?,
    nameRu: String,
    genres: List<Genre>?,
    profession: String?,
    onClick: (Int) -> Unit
){
    Column(
        modifier = Modifier
            .padding(end = 8.dp)
            .clickable {
                onClick(id)
            }
            .wrapContentWidth()
            .width(131.dp)
            .height(235.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box{
            AsyncImage(
                model = posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .align(Alignment.TopEnd)
                    .background(color = Color(0xFF3D3BFF), shape = RoundedCornerShape(4.dp))
                    .width(22.dp)
                    .height(14.dp),
                contentAlignment = Alignment.Center
            )
            {
                ratingKinopoisk?.let {
                    Text(
                        text = ratingKinopoisk.toString(),
                        style = TextStyle(
                            fontSize = 8.sp
                        ),
                        color = Color.White
                    )
                }
            }

        }
        Column {
            Text(
                text = nameRu,
                modifier = Modifier.padding(top = 8.dp).width(120.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.graphikregular)),
                    color = Color(0xFF272727)
                )
            )
            genres?.let {
                Text(
                    text = genres.firstOrNull()?.genre ?: "No genre",
                    modifier = Modifier.padding(top = 2.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.graphikregular)),
                        color = Color(0xFF838390)
                    )
                )
            }
            profession?.let {
                Text(
                    text = profession,
                    modifier = Modifier.padding(top = 2.dp),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.graphikregular)),
                        color = Color(0xFF838390)
                    )
                )
            }
        }
    }
}