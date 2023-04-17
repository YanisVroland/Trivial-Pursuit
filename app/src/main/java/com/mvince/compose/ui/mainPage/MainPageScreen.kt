package com.mvince.compose.ui.mainPage

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlin.contracts.Returns

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun MainPageScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<MainPageViewModel>()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Trivial Pursuit",
                        textAlign = TextAlign.Center,
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar() {
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(
                    painter = painterResource(id = com.mvince.compose.R.drawable.accounticon),
                    contentDescription = "account page"
                )}, label = { Text(text = "account page")})
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(
                    painter = painterResource(id = com.mvince.compose.R.drawable.homeicon),
                    contentDescription = "main page"
                )}, label = { Text(text = "main page")})
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(
                    painter = painterResource(id = com.mvince.compose.R.drawable.trophy),
                    contentDescription = "ranking page"
                )}, label = { Text(text = "ranking page")})
            }
        }
    ) {

    }
}