package com.mvince.compose.ui.mainPage.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.mainPage.MainPageViewModel

@Composable

fun GameEndBody(navController: NavController) {
    val viewModel = hiltViewModel<MainPageViewModel>()
    val totalScore = viewModel.finalScore.collectAsState().value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        AsyncImage(
            modifier = Modifier
                .padding(vertical = 93.dp)
                .width(400.dp),
            model = "https://shuffle.cards/assets/images/licenses/trivial_pursuit/header_logo.png",
            contentDescription = "Logo trivial pursuit"
        )
        Spacer(modifier = Modifier.size(40.dp))
        Text(textAlign = TextAlign.Center, text = "Vous avez répondu à toutes les questions du jour !")
        Spacer(modifier = Modifier.size(20.dp))
        Text(textAlign = TextAlign.Center, text = "Score : $totalScore")
        Button(modifier = Modifier.padding(20.dp),onClick = { navController.navigate(Route.MAINPAGE) }) {
            Text(text = "Accueil")
        }
    }
}