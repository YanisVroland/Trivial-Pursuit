package com.mvince.compose.ui.mainPage.components

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.mainPage.MainPageViewModel
import com.mvince.compose.ui.theme.lambdaButton

@Composable

fun GameStartBody(navController: NavController) {
    val viewModel = hiltViewModel<MainPageViewModel>()

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        AsyncImage(
            modifier = Modifier.padding(vertical = 93.dp),
            model = "https://shuffle.cards/assets/images/licenses/trivial_pursuit/header_logo.png",
            contentDescription = "Logo trivial pursuit"
        )
        Spacer(modifier = Modifier.size(80.dp))
        Button(colors = ButtonDefaults.buttonColors(containerColor = lambdaButton), onClick = {
            navController.navigate(
                Route.GAME
            )
        }) {
            Text(text = "Démarrer une partie")
        }
    }
}