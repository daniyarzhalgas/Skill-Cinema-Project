package sdu.project.cinemaapp.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sdu.project.cinemaapp.presentation.ui.screens.MainScreen
import sdu.project.cinemaapp.presentation.ui.screens.OnBoardingScreen

@Composable
fun OnboardingNavGraph(navController: NavHostController){
    NavHost(navController, "onboarding_screen"){
        composable("onboarding_screen"){
            OnBoardingScreen(onClick = {
                navController.navigate("home_screen"){
                    popUpTo("onboarding_screen"){
                        inclusive = true
                    }
                }
            })
        }
        composable("home_screen"){
            MainScreen()
        }
    }
}