package com.example.skill_cinema.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skill_cinema.R
import com.example.skill_cinema.data.*
import com.example.skill_cinema.data.MockData.popular
import com.example.skill_cinema.data.MockData.premier
import com.example.skill_cinema.data.MockData.serial
import com.example.skill_cinema.data.MockData.top250
import com.example.skill_cinema.data.MockData.actionMovie
import com.example.skill_cinema.data.MockData.dramaFrance

@Composable
fun HomePage(onItemClick: (CinemaItem) -> Unit) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .border(1.dp, androidx.compose.ui.graphics.Color.Black),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = "Skill Cinema",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.graphikbold)),
            ),
            modifier = Modifier.padding(start = 30.dp, top = 30.dp)
        )
        CinemaSection("Премьеры", premier, onItemClick)
        CinemaSection("Популярное", popular, onItemClick)
        CinemaSection("Боевики США", actionMovie, onItemClick)
        CinemaSection("Топ-250", top250, onItemClick)
        CinemaSection("Драмы Франции", dramaFrance, onItemClick)
        CinemaSection("Сериалы", serial, onItemClick)
    }
}
