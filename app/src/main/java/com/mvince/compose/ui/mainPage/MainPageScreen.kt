package com.mvince.compose.ui.mainPage

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.mainPage.components.GameBody
import com.mvince.compose.ui.mainPage.components.GameEndBody
import com.mvince.compose.ui.mainPage.components.GameStartBody
import com.mvince.compose.ui.mainPage.components.UserBody
import com.mvince.compose.ui.mainPage.components.RankingBody
import com.mvince.compose.ui.theme.JetpackComposeBoilerplateTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun MainPageScreen(navController: NavHostController) {

    hiltViewModel<MainPageViewModel>()
    val appNavController = rememberNavController()
    val selectedTab = remember { mutableStateOf(Route.STARTGAME) }

    JetpackComposeBoilerplateTheme() {
        Scaffold(
            bottomBar = {
                NavigationBar() {
                    NavigationBarItem(selected = selectedTab.value == Route.USER,
                        onClick = {
                            selectedTab.value = Route.USER
                            appNavController.navigate(Route.USER)
                        }, icon = {
                        Icon(
                            painter = painterResource(id = com.mvince.compose.R.drawable.accounticon),
                            contentDescription = "Profil"
                        )
                    }, label = { Text(text = "Profil") })
                    NavigationBarItem(
                        selected = selectedTab.value == Route.STARTGAME,
                        onClick = {
                            selectedTab.value = Route.STARTGAME
                            appNavController.navigate(Route.STARTGAME)
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
                startDestination = Route.STARTGAME
            ) {
                composable(Route.STARTGAME) {
                    GameStartBody(navController = navController)
                }
                composable(Route.USER) {
                    UserBody(navController = navController)
                }
                composable(Route.GAME) {
                    GameBody(navController = appNavController)
                }
                composable(Route.ENDGAME) {
                    GameEndBody(navController = appNavController)
                }
                composable(Route.RANKING) {
                    RankingBody()
                }
            }
        }
    }
}