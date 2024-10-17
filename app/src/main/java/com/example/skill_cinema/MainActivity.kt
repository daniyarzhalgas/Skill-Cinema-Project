package com.example.skill_cinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MainScreen
import com.example.myapplication.screens.HomeScreen
import com.example.skill_cinema.ui.onboarding.Loader
import com.example.skill_cinema.ui.onboarding.OnBoardingScreen
import com.example.skill_cinema.ui.theme.SkillCinemaTheme

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

