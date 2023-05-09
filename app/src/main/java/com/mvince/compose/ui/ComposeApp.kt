package com.mvince.compose.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mvince.compose.ui.details.DetailsScreen
import com.mvince.compose.ui.forgotPassword.ForgotPasswordScreen
import com.mvince.compose.ui.mainPage.MainPageScreen
import com.mvince.compose.ui.ranking.RankingBody
import com.mvince.compose.ui.signin.LoginScreen
import com.mvince.compose.ui.signup.SignupScreen
import com.mvince.compose.ui.users.UsersScreen

@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.MAINPAGE
    ) {
        composable(route = Route.MAINPAGE) { MainPageScreen(navController)}
        composable(route = Route.LOGIN) { LoginScreen(navController) }
        composable(route = Route.REGISTER) { SignupScreen(navController) }
        composable(route = Route.FORGOTPASSWORD) { ForgotPasswordScreen(navController) }
        /*composable(route = Route.USER) { backStackEntry ->
            UsersScreen(
                onUserClick = { username ->
                    // In order to discard duplicated navigation events, we check the Lifecycle
                    if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        navController.navigate("${Route.DETAIL}/$username")
                    }
                }
            )
        }*/
        /*composable(
            route = "${Route.DETAIL}/{${Argument.USERNAME}}",
            arguments = listOf(
                navArgument(Argument.USERNAME) {
                    type = NavType.StringType
                }
            ),
        ) {
            DetailsScreen(navController)
        }*/
    }
}

object Route {
    const val MAINPAGE = "mainpage"
    const val USER = "user"
    const val DETAIL = "detail"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val FORGOTPASSWORD = "forgotPassword"
    const val RANKING = "ranking"
    const val GAME = "game"
}

object Argument {
    const val USERNAME = "username"
}