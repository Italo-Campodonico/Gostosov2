package com.example.Gostoso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.Gostoso.ui.navigation.AppNavigation
import com.example.Gostoso.ui.theme.GostosoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GostosoTheme {
                // Scaffold útil para aplicar padding y un layout base.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Llama al composable de navegación
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}