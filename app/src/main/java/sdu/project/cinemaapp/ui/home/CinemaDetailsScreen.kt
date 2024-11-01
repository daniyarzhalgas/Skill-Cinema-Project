package sdu.project.cinemaapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.skill_cinema.R
import sdu.project.cinemaapp.data.MockData

@Composable
fun CinemaDetailsScreen(itemId: Int) {
    val item = MockData.findItemById(itemId)

    Column {
        Box(
        ) {
            Image(
                painter = painterResource(id = R.drawable.intersleller),
                contentDescription = null,
            )
            Column() {
                if (item != null) {
                    Text(
                        text = item.title
                    )
                    Text(
                        text = item.genre
                    )
                    Text(
                        text = item.rating.toString()
                    )
                }
            }

        }

    }
}