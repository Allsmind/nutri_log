package com.projeto.nutrilog

import androidx.compose.runtime.Composable
import com.projeto.nutrilog.navigation.NavGraph
import com.projeto.nutrilog.theme.NutriLogTheme

@Composable
fun App() {
    NutriLogTheme {
        NavGraph()
    }
}
