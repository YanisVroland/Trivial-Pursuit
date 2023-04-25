package com.mvince.compose.ui.ranking

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.ui.signin.SigninViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RankingScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<SigninViewModel>()
    var user by remember { mutableStateOf("") }
    data class member(val name: String, val score: Int)
    var cpt = 0
    val test = listOf<member>(
        member("Pipoune", 500),
        member("Yanis", 500),
        member("David",500),
        member("You",1),
        member("You",1),
        member("You",1),
        member("You",1),
        member("You",1),
        member("You",1),
        member("You",1),
        member("You",1),
        member("You",1),
        member("You",1),
        member("You",1),
    );

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
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(test){ hu ->
                cpt += 1
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable{ },
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            buildAnnotatedString {
                                append(cpt.toString())
                                append(" ")
                                append(hu.name.toString())
                            }
                        )
                        Text(
                            buildAnnotatedString {
                                append(hu.score.toString())
                            }
                        )
                    }
                }
            }
        }
    }
}