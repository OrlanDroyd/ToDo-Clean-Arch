package com.gmail.orlandroyd.notasnow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gmail.orlandroyd.notasnow.ui.theme.NotasNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotasNowTheme {

            }
        }
    }
}