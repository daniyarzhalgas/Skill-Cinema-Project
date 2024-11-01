package sdu.project.cinemaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MainScreen
import sdu.project.cinemaapp.ui.onboarding.Loader
import sdu.project.cinemaapp.ui.onboarding.OnBoardingScreen
import sdu.project.cinemaapp.ui.theme.SkillCinemaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkillCinemaTheme {
                val navController = rememberNavController();
                NavHost(navController, "onboarding_screen") {
                    composable("onboarding_screen") {
                        OnBoardingScreen(navController)
                    }
                    composable("loader_screen") {
                        Loader(navController)
                    }
                    composable("home_screen") {
                        MainScreen()
                    }
                }
            }
        }
    }
}

