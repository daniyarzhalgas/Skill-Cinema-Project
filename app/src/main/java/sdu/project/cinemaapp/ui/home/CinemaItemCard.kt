package sdu.project.cinemaapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skill_cinema.R
import sdu.project.cinemaapp.data.CinemaItem

@Composable
fun CinemaItemCard(item: CinemaItem, onClick: (CinemaItem) -> Unit) {
    Column(
        modifier = Modifier
            .padding(end = 8.dp)
            .clickable { onClick(item) }
    ) {
        Box() {
            Image(
                painter = painterResource(id = R.drawable.intersleller),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
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
                Text(
                    text = item.rating.toString(),
                    style = TextStyle(
                        fontSize = 8.sp
                    ),
                    color = Color.White
                )
            }

        }
        Text(
            text = item.title,
            modifier = Modifier.padding(top = 8.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.graphikregular)),
                color = Color(0xFF272727)
            )
        )
        Text(
            text = item.genre,
            modifier = Modifier.padding(top = 2.dp),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.graphikregular)),
                color = Color(0xFF838390)
            )
        )
    }
}