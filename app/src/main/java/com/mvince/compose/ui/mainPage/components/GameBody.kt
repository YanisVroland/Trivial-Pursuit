package com.mvince.compose.ui.mainPage.components

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mvince.compose.R
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.mainPage.MainPageViewModel
import com.mvince.compose.ui.theme.lambdaButton

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(fontWeight = FontWeight.Bold, text = "Score : $totalScore")
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = Color.LightGray,
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                AsyncImage(
                    model = "https://shuffle.cards/assets/images/licenses/trivial_pursuit/header_logo.png",
                    contentDescription = "Logo trivial pursuit"
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            if (question != null) {
                Card(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(10.dp),
                        )
                        .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(10.dp), text = question.question
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
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
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (viewModel.currentIndex == viewModel._questions.size - 1 && viewModel.answered) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = lambdaButton),
                        onClick = { navController.navigate(Route.ENDGAME) }) {
                        Text(text = "Terminer le quizz")
                    }
                } else {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = lambdaButton),
                        onClick = { viewModel.nextQuestion() }) {
                        Text(text = "Question suivante")
                        Icon(
                            painter = painterResource(id = R.drawable.levaitaico),
                            contentDescription = "Icône question suivante"
                        )
                    }
                }
            }
        }
    }
}