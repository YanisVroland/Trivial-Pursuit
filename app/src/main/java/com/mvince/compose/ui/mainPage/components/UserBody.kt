package com.mvince.compose.ui.mainPage.components

import android.annotation.SuppressLint
import android.content.res.AssetManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvince.compose.R
import com.mvince.compose.domain.UserFirebase
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.components.CustomOutlinedTextField
import com.mvince.compose.ui.mainPage.MainPageViewModel
import com.mvince.compose.ui.theme.invalidButton
import com.mvince.compose.ui.theme.lambdaButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun UserBody(navController: NavController) {
    val viewModel = hiltViewModel<MainPageViewModel>()
    var expanded by remember { mutableStateOf(false) }
    val iconList = listOf(R.drawable._1, R.drawable._2, R.drawable._3,R.drawable._4, R.drawable._5, R.drawable._6,R.drawable._7, R.drawable._8)

    var currentUserState by remember { mutableStateOf(UserFirebase("Youne", "Youne@dsqdqd.com", 10))}

    val currentUser by remember { derivedStateOf { currentUserState } }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.wrapContentSize()) {
            Image(
                painter = painterResource(currentUser.avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(lambdaButton)
                    .align(Alignment.BottomEnd)
                    .padding(3.dp)
            ) {
                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.pencil),
                        contentDescription = "Modifier l'avatar",
                        tint = Color.White
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    iconList.forEach { icon ->
                        DropdownMenuItem(onClick = {
                            currentUserState = currentUserState.copy(avatar = icon)
                        }, text = {
                            Image(
                                painter = painterResource(icon),
                                contentDescription = "Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        })
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(64.dp))

        CustomOutlinedTextField(
            value = currentUser.pseudo,
            onValueChange = { },
            label = { Text("Pseudo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(64.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = lambdaButton),
            onClick = { viewModel.nextQuestion() }) {
            Text(text = "Envoyer modification")
            Icon(
                painter = painterResource(id = R.drawable.levaitaico),
                contentDescription = "Icône question suivante"
            )
        }
        Spacer(modifier = Modifier.height(64.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                viewModel.logout()
                navController.navigate(Route.LOGIN)
            }, colors = ButtonDefaults.buttonColors(containerColor = invalidButton)) {
                Text("Déconnexion")
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = lambdaButton)
            ) {
                Text("Reset Score")
            }
        }

    }
}


