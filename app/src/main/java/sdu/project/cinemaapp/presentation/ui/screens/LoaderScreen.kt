package sdu.project.cinemaapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import sdu.project.cinemaapp.R

@Composable
fun LoaderScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(26.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.skillcinema),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 18.dp)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .padding(36.dp)
                .align(Alignment.Center),
            color = Color(0xFF2196F3),
            trackColor = Color.White
        )

        Image(
            painter = painterResource(id = R.drawable.onboarding_img1),
            contentDescription = null,
            Modifier.align(Alignment.BottomCenter)
        )
    }
}