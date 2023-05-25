package com.mvince.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mvince.compose.ui.forgotPassword.ForgotPasswordScreen
import com.mvince.compose.ui.mainPage.MainPageScreen
import com.mvince.compose.ui.mainPage.components.GameBody
import com.mvince.compose.ui.mainPage.components.GameEndBody
import com.mvince.compose.ui.signin.LoginScreen
import com.mvince.compose.ui.signup.SignupScreen

@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<ComposeViewModel>()
    val currentUser = viewModel.currentUser.collectAsState().value

    fun startRedirection(): String {
        if(currentUser)
            return Route.MAINPAGE
        return Route.LOGIN
    }

    NavHost(
        navController = navController,
        startDestination = startRedirection()
    ) {
        composable(route = Route.MAINPAGE) {MainPageScreen(navController)}
        composable(route = Route.GAME) {GameBody(navController)}
        composable(route = Route.ENDGAME) { GameEndBody(navController)}
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
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val FORGOTPASSWORD = "forgotPassword"
    const val RANKING = "ranking"
    const val GAME = "game"
    const val USER = "user"
    const val STARTGAME = "startgame"
    const val ENDGAME = "endgame"
}

