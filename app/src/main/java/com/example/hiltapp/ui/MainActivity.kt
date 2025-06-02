package com.example.hiltapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.hiltapp.network.ApiService
import com.example.hiltapp.ui.components.UserScreen
import com.example.hiltapp.ui.theme.HiltAppTheme
import com.example.hiltapp.ui.viewmodels.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var apiService: ApiService

    private val userViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HiltAppTheme {
                UserScreen(userViewModel)
            }
        }
    }
}