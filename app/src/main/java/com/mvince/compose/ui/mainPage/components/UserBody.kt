package com.mvince.compose.ui.mainPage.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mvince.compose.ui.theme.*


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun UserBody(navController: NavController) {
    val iconList = listOf(
        R.drawable._1,
        R.drawable._2,
        R.drawable._3,
        R.drawable._4,
        R.drawable._5,
        R.drawable._6,
        R.drawable._7,
        R.drawable._8
    )
    val viewModel = hiltViewModel<MainPageViewModel>()
    var expanded by remember { mutableStateOf(false) }
    var ifModified by remember { mutableStateOf(false) }

    val user = viewModel.user.collectAsState().value
    val updateIsCorrect = viewModel.updateIsCorrect.collectAsState().value
    var pseudoValid by remember { mutableStateOf(true) }
    var avatar by remember { mutableStateOf<Int?>(null) }
    var pseudo by remember { mutableStateOf("") }
    var totalScore by remember { mutableStateOf(0) }
    var validB = validButton
    var invalidB = invalidButton
    var classicB = lambdaButton
    if(isSystemInDarkTheme()){
        validB = dvalidButton
        invalidB = dinvalidButton
        classicB = dlambdaButton
    }


    LaunchedEffect(user) {
        user.let { userResource ->
            pseudo = userResource.pseudo
            totalScore = userResource.totalScore
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.wrapContentSize()) {
            Image(
                painter = avatar?.let { painterResource(iconList[it]) }
                    ?: painterResource(iconList[user.avatar]),
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
                    .background(classicB)
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
                            avatar = iconList.indexOf(icon)
                            ifModified = true
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

        Spacer(modifier = Modifier.height(42.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Score total : $totalScore")
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(classicB)
                    .padding(3.dp)
            ) {
                IconButton(
                    onClick = {
                        totalScore = 0
                        ifModified = true
                    },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cached),
                        contentDescription = "Reset Score",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        CustomOutlinedTextField(
            value = pseudo,
            isErrorValue = !pseudoValid,
            onValueChange = {
                pseudo = it
                ifModified = true
            },
            label = { Text("Pseudo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(42.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = classicB),
            enabled = ifModified,
            onClick = {
                pseudoValid = pseudo.isNotEmpty()
                if(pseudoValid) viewModel.updateUser(UserFirebase(pseudo, totalScore, avatar ?: user.avatar)) else ifModified = false
            }) {
            Text(text = "Envoyer modification")
            Icon(
                painter = painterResource(id = R.drawable.levaitaico),
                contentDescription = "Icône question suivante"
            )
        }
        Spacer(modifier = Modifier.height(34.dp))

        Button(onClick = {
            viewModel.logout()
            navController.navigate(Route.LOGIN) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }, colors = ButtonDefaults.buttonColors(containerColor = invalidB)) {
            Text("Déconnexion")
        }
        if (updateIsCorrect != null) {
            Toast.makeText(LocalContext.current, "Profil mis à jour !", Toast.LENGTH_SHORT)
                .show()
            ifModified = false
            viewModel.stopToastUpdate()
        }
    }
}


