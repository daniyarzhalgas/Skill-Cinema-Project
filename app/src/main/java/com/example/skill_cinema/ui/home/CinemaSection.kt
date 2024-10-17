package com.example.skill_cinema.ui.home


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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.skill_cinema.R
import com.example.skill_cinema.data.CinemaItem

@Composable
fun CinemaSection(title: String, items: List<CinemaItem>, onItemClick: (CinemaItem) -> Unit) {
    Column(
        modifier = Modifier.padding(top = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title, style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.graphiksemibold))
                )
            )
            Text(
                text = "Все", modifier = Modifier.padding(end = 26.dp), style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.graphikmedium)),
                    color = Color(0xFF3D3BFF),
                    textAlign = TextAlign.Center
                )
            )
        }
        LazyRow {
            item {
                Spacer(Modifier.width(30.dp))
            }
            items(items) { item ->
                CinemaItemCard(item = item, onClick = onItemClick)
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
                        onClick = { /*TODO*/ },
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
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = "Next",
                                tint = Color(0xFF3D3BFF)
                            )
                        }
                    }
                    Text(text = "Показать все")
                }
            }
        }
    }
}
