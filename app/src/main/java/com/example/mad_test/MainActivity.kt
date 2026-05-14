package com.example.mad_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mad_test.ui.navigation.NewsNavGraph
import com.example.mad_test.ui.theme.Mad_testTheme

class MainActivity : ComponentActivity() {``
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mad_testTheme {
                NewsNavGraph()
            }
        }
    }
}
