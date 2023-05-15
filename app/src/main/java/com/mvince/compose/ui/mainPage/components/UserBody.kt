package com.mvince.compose.ui.mainPage.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvince.compose.R
import com.mvince.compose.ui.components.CustomOutlinedTextField
import com.mvince.compose.ui.mainPage.MainPageViewModel
import com.mvince.compose.ui.theme.invalidButton
import com.mvince.compose.ui.theme.lambdaButton

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserBody(navController: NavController) {
    val viewModel = hiltViewModel<MainPageViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.trophy), // Replace with your avatar icon
            contentDescription = "Avatar Icon",
            modifier = Modifier.size(96.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = "",
            onValueChange = { /* TODO: Handle text field value change */ },
            label = { Text("Pseudo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { }) {
            Text("Modifier")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { }, colors= ButtonDefaults.buttonColors(containerColor = invalidButton)) {
                Text("Déconnexion")
            }
            Button(onClick = { },colors= ButtonDefaults.buttonColors(containerColor = lambdaButton)) {
                Text("Reset Score")
            }
        }

    }
}