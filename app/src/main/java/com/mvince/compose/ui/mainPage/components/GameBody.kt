package com.mvince.compose.ui.mainPage.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvince.compose.ui.mainPage.MainPageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun GameBody(navController: NavController) {
    val viewModel = hiltViewModel<MainPageViewModel>()
    val questions = viewModel.question.collectAsState().value
    val currentQuestions = viewModel.currentQuestion.collectAsState().value


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
        Column( modifier = androidx.compose.ui.Modifier.padding(it)) {
         questions.forEach {
             Text(text = it.question)
         }
        }
    }
}