package com.example.avslyceumkotlinexamapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.avslyceumkotlinexamapp.presentation.products.screens.NavGraphs
import com.example.avslyceumkotlinexamapp.presentation.theme.LyceumKotlinExamAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LyceumKotlinExamAppTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root
                )
            }
        }
    }
}

