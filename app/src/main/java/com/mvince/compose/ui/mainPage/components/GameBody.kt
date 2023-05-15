package com.mvince.compose.ui.mainPage.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mvince.compose.ui.mainPage.MainPageViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun GameBody(navController: NavController) {
    val viewModel = hiltViewModel<MainPageViewModel>()
    val question = viewModel.question.collectAsState().value
    val totalScore = viewModel.totalScore.collectAsState().value
    val answers = question?.incorrectAnswers?.plus(question?.correctAnswer)?.shuffled()
    val isCorrect = viewModel.isCorrect.collectAsState().value


    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Score : $totalScore")
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.Gray,
                            radius = this.size.minDimension + 5
                        )
                    }
                    .drawBehind {
                        drawCircle(
                            color = Color.Black,
                            radius = this.size.minDimension + 5,
                            style = Stroke(width = 2f)
                        )
                    },
                text = "${viewModel.currentIndex + 1}/${viewModel._questions.size}",
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 75.dp, vertical = 20.dp)
                .padding(vertical=20.dp), text = "Trivial Pursuit")
        }
        if (question != null) {
            Text(text = question.question)
            if (answers != null) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(answers) { answer ->
                        if (answer != null) {
                            Button(
                                modifier = Modifier.padding(10.dp),
                                onClick = { viewModel.validateAnswer(answer) },
                                enabled = !viewModel.answered
                            ) {
                                Text(text = answer)
                            }
                        }
                    }
                }
            }
        }
        if (isCorrect != null) {
            if (isCorrect) {
                Text(text = "Réponse Correcte !")
            } else {
                Text(text = "Mauvaise Réponse ...")
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Button(onClick = { viewModel.nextQuestion() }) {
                Text(text = "Question suivante")
            }
        }
    }
}