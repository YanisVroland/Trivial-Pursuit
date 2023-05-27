package com.mvince.compose.ui.mainPage.components

import android.annotation.SuppressLint
import android.text.Html
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mvince.compose.R
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.mainPage.MainPageViewModel
import com.mvince.compose.ui.theme.invalidButton
import com.mvince.compose.ui.theme.lambdaButton
import com.mvince.compose.ui.theme.validButton
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameBody(navController: NavController) {
    val viewModel = hiltViewModel<MainPageViewModel>()
    val question = viewModel.question.collectAsState().value
    val totalScore = viewModel.totalScore.collectAsState().value
    val answers = viewModel.answers.collectAsState().value
    val isCorrect = viewModel.isCorrect.collectAsState().value
    var progress by remember { mutableStateOf(1f) }
    var animateAction = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    var isPaused by remember { mutableStateOf(false) }



    LaunchedEffect(viewModel.currentIndex) {
        progress = 0f
        isPaused = false
        while (progress < 1f && !isPaused) {
            delay(1000)
            progress += 0.1f
        }
        viewModel.validateAnswer("null")
    }

    Column(
        Modifier.fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

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

        Spacer(modifier = Modifier.size(20.dp))
        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "https://shuffle.cards/assets/images/licenses/trivial_pursuit/header_logo.png",
                contentDescription = "Logo trivial pursuit"
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        if (question != null) {
            Text(
                color = viewModel.currentCardColor,
                text = question.category,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Spacer(modifier = Modifier.size(15.dp))
            Card(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(10.dp),
                    )
                    .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(containerColor = viewModel.currentCardColor)
            ) {
                Text(
                    modifier = Modifier
                        .padding(10.dp),
                    color = Color.White,
                    text = Html.fromHtml(question.question, Html.FROM_HTML_MODE_LEGACY)
                        .toString()
                )
            }
            Spacer(modifier = Modifier.size(15.dp))
            Box {
                LinearProgressIndicator(progress = animateAction, color = Color.Red)
                Icon(
                    painter = painterResource(id = R.drawable.run),
                    tint = viewModel.currentCardColor,
                    contentDescription = "Personnage qui court",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset(
                            x = with(LocalDensity.current) { animateAction * 85.dp.toPx() }.dp,
                            y = -10.dp
                        )
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(answers.size) { idx ->
                    val correct: Color = if (answers[idx] == question.correctAnswer) {
                        validButton
                    } else {
                        invalidButton
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                spotColor = Color.Black,
                                elevation = 5.dp,
                                shape = RoundedCornerShape(14.dp),
                                ambientColor = Color.Gray
                            ),
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = correct,
                            disabledContentColor = Color.White
                        ),
                        onClick = {
                            viewModel.validateAnswer(answers[idx])
                            isPaused = true
                        },
                        enabled = !viewModel.answered
                    ) {
                        Text(
                            text = Html.fromHtml(answers[idx], Html.FROM_HTML_MODE_LEGACY)
                                .toString()
                        )
                    }
                }
            }
        }
        if (isCorrect != null) {
            if (isCorrect) {
                Toast.makeText(LocalContext.current, "+ 10 !", Toast.LENGTH_SHORT).show()
                viewModel.stopToastQuestion()
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
                    onClick = {
                        viewModel.updateUserScore()
                        navController.navigate(Route.ENDGAME)
                    }) {
                    Text(text = "Terminer le quizz")
                }
            } else {
                Button(
                    modifier = Modifier.shadow(
                        spotColor = Color.Black,
                        elevation = 5.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color.Gray
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = lambdaButton,
                        disabledContainerColor = Color.Gray
                    ),
                    onClick = { viewModel.nextQuestion() },
                    enabled = viewModel.answered
                ) {
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