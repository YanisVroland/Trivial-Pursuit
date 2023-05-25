package com.mvince.compose.ui.mainPage.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.mvince.compose.ui.theme.lambdaButton
import java.time.LocalDate

@Composable

fun GameStartBody(navController: NavController) {
    val viewModel = hiltViewModel<MainPageViewModel>()
    val alreadyPlayed = viewModel.user.collectAsState().value.lastGameDate == LocalDate.now().toString()

    Column(modifier = Modifier
        .fillMaxWidth().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        AsyncImage(
            modifier = Modifier
                .padding(vertical = 93.dp)
                .width(200.dp),
            model = "https://shuffle.cards/assets/images/licenses/trivial_pursuit/header_logo.png",
            contentDescription = "Logo trivial pursuit"
        )
        Spacer(modifier = Modifier.size(40.dp))
        if(alreadyPlayed) {
            Text(textAlign = TextAlign.Center, text = "Vous avez déjà joué aujourd'hui, revenez demain pour une nouvelle partie !")
        }
        Spacer(modifier = Modifier.size(40.dp))
        Button(colors = ButtonDefaults.buttonColors(containerColor = lambdaButton), enabled = !alreadyPlayed, onClick = {
            navController.navigate(
                Route.GAME
            )
        }) {
            Text(text = "Démarrer une partie")
        }
    }
}