package sdu.project.cinemaapp.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.skill_cinema.R
import kotlinx.coroutines.delay


@Composable
fun OnBoardingScreen(navController: NavController) {
    val state = rememberPagerState(pageCount = { 3 })

    val listOnBoarding = listOf(
        ListOnBoarding(text = "Узнавай\nо премьерах", image = R.drawable.onboarding_img1),
        ListOnBoarding(text = "Создавай\nколлекции", image = R.drawable.onboarding_img2),
        ListOnBoarding(text = "Делись\nс друзьями", image = R.drawable.onboarding_img3)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.skillcinema), contentDescription = null)
            TextButton(
                onClick = {
                    navController.navigate("loader_screen") {
                    }
                }
            ) {
                Text(
                    "Пропустить", style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.graphikmedium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFB5B5C9),
                        textAlign = TextAlign.Center,
                    )
                )
            }

        }
        HorizontalPager(
            state = state
        ) { page ->
            OnBoardingPage(listOnBoarding = listOnBoarding[page])
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp)
        )
        {
            repeat(state.pageCount) { iteration ->
                val color = if (state.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
}

@Composable
fun OnBoardingPage(listOnBoarding: ListOnBoarding) {
    Column(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Image(
            painter = painterResource(id = listOnBoarding.image), contentDescription = null,
            modifier = Modifier
                .padding(top = 166.dp)
                .fillMaxWidth()
        )
        Text(
            text = listOnBoarding.text, modifier = Modifier
                .padding(top = 110.dp), style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.graphikmedium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF272727),
            )
        )
    }
}

@Composable
fun Loader(navController: NavController) {
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
                .width(36.dp)
                .align(Alignment.Center),
            color = Color(0xFF3D3BFF),
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

        Image(
            painter = painterResource(id = R.drawable.onboarding_img1),
            contentDescription = null,
            Modifier.align(Alignment.BottomCenter)
        )
    }
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("home_screen") {
            popUpTo("loader_screen") { inclusive = true }
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun FirstOnBoardingScreenPreview() {
//    OnBoardingPage(listOnBoarding = listOnBoarding[0])
//}
//
//@Composable
//@Preview(showBackground = true)
//fun SecondOnBoardingScreenPreview() {
//    OnBoardingPage(listOnBoarding = listOnBoarding[1])
//}
//
//@Composable
//@Preview(showBackground = true)
//fun ThirdOnBoardingScreenPreview() {
//    OnBoardingPage(listOnBoarding = listOnBoarding[2])
//}
