package com.mvince.compose.ui.mainPage

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.mainPage.components.GameBody
import com.mvince.compose.ui.ranking.RankingBody
import com.mvince.compose.ui.theme.JetpackComposeBoilerplateTheme
import kotlin.contracts.Returns

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun MainPageScreen(navController: NavHostController) {

    val appNavController = rememberNavController()
    val selectedTab = remember { mutableStateOf(Route.GAME) }

    JetpackComposeBoilerplateTheme() {
        Scaffold(
            bottomBar = {
                NavigationBar() {
                    NavigationBarItem(selected = false, onClick = {}, icon = {
                        Icon(
                            painter = painterResource(id = com.mvince.compose.R.drawable.accounticon),
                            contentDescription = "Profil"
                        )
                    }, label = { Text(text = "Profil") })
                    NavigationBarItem(
                        selected = selectedTab.value == Route.GAME,
                        onClick = {
                            selectedTab.value = Route.GAME
                            appNavController.navigate(Route.GAME)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.gamepad_variant),
                                contentDescription = "Trivial Pursuit"
                            )
                        },
                        label = { Text(text = "Trivial Pursuit") })
                    NavigationBarItem(
                        selected = selectedTab.value == Route.RANKING,
                        onClick = {
                            selectedTab.value = Route.RANKING
                            appNavController.navigate(Route.RANKING)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = com.mvince.compose.R.drawable.trophy),
                                contentDescription = "Classement"
                            )
                        },
                        label = { Text(text = "Classement") })
                }
            }
        ) {
            NavHost(
                navController = appNavController,
                startDestination = Route.GAME
            ) {
                composable(Route.GAME) {
                    GameBody(navController = appNavController)
                }
                composable(Route.RANKING) {
                    RankingBody(navController = appNavController)
                }
            }
        }
    }
}