package com.example.dangnhapdangky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dangnhapdangky.ui.theme.DangnhapDangkyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DangnhapDangkyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Mynavigation()
                }
            }
        }
    }
}