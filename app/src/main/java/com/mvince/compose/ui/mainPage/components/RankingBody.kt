package com.mvince.compose.ui.mainPage.components

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.mvince.compose.R
import com.mvince.compose.ui.mainPage.MainPageViewModel
import com.mvince.compose.ui.theme.primary
import com.mvince.compose.ui.theme.primaryDarkmode

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RankingBody() {
    val mainModel = hiltViewModel<MainPageViewModel>()
    val totalScoreList = mainModel.allUsers.collectAsState().value
    val dailyScoreList = mainModel.allUsersDaily.collectAsState().value
    var colorT = Color.Black
    var colorButtons = primary
    val itemsList = listOf("Global Ranking", "Daily Ranking")
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

    if (isSystemInDarkTheme()) {
        colorT = Color.LightGray
        colorButtons = primaryDarkmode
    }
    var selectedIndex by remember { mutableStateOf(1) }
    
    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "CLASSEMENT",
                color = colorT,
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,

            ) {

            val cornerRadius = 16.dp

            itemsList.forEachIndexed { index, item ->

                OutlinedButton(
                    onClick = { selectedIndex = index },
                    modifier = when (index) {
                        0 ->
                            Modifier
                                .offset(0.dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                        else ->
                            Modifier
                                .offset((-1 * index).dp, 0.dp)
                                .zIndex(if (selectedIndex == index) 1f else 0f)
                    },
                    shape = when (index) {
                        0 -> RoundedCornerShape(
                            topStart = cornerRadius,
                            topEnd = 0.dp,
                            bottomStart = cornerRadius,
                            bottomEnd = 0.dp
                        )
                        itemsList.size - 1 -> RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = cornerRadius,
                            bottomStart = 0.dp,
                            bottomEnd = cornerRadius
                        )
                        else -> RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    },
                    border = BorderStroke(
                        1.dp, if (selectedIndex == index) {
                            colorButtons
                        } else {
                            colorButtons.copy(alpha = 0.75f)
                        }
                    ),
                    colors = if (selectedIndex == index) {
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = colorButtons.copy(alpha = 0.8f),
                            contentColor = colorT
                        )
                    } else {
                        ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = colorT
                        )
                    }
                ) {
                    Text(item)
                }
            }
        }
        if(dailyScoreList.isEmpty() && selectedIndex != 0) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(textAlign = TextAlign.Center, text = "Personne n'a encore joué aujourd'hui !")
            }
        }
        LazyColumn(
            Modifier.padding(horizontal = 16.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(if(selectedIndex == 0)totalScoreList else dailyScoreList) { score ->
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingContent = {
                        if(selectedIndex == 0){
                            Text(
                                text = (totalScoreList.indexOf(score) + 1).toString()
                            )
                        }else{
                            Text(
                                text = (dailyScoreList.indexOf(score) + 1).toString()
                            )
                        }

                    },
                    headlineText = {
                        Row() {
                            Image(
                                painter = painterResource(iconList[score!!.avatar]),
                                contentDescription = "Avatar",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.Gray, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = score.pseudo
                            )
                        }
                    },
                    trailingContent = {
                        if(selectedIndex == 0){
                            Text(
                                text = score?.totalScore.toString()
                            )
                        }else{
                            Text(
                                text = score?.dailyScore.toString()
                            )
                        }
                    }
                )
            }
        }
    }
}
