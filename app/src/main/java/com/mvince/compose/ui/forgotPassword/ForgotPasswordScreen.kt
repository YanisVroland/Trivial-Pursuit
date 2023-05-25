package com.mvince.compose.ui.forgotPassword


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.components.CustomButton
import com.mvince.compose.ui.components.CustomOutlinedTextField


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<ForgotPasswordViewModel>()
    var email by remember { mutableStateOf("") }
    var emailValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w+([.-]?\\w+)*\$")

    val resetPasswordFlow = viewModel.resetPasswordFlow.collectAsState().value
    val errorFlow = viewModel.errorFlow.collectAsState().value

    LaunchedEffect(resetPasswordFlow) {
        resetPasswordFlow?.let { isResetPasswordSuccessful ->
            if (isResetPasswordSuccessful) {
                navController.navigate(Route.LOGIN)
            }
        }
    }

    if (errorFlow != null) {
        errorMessage = when (errorFlow.errorCode) {
            "ERROR_INVALID_EMAIL" -> {
                "L'adresse e-mail n'est pas valide."
            }
            "ERROR_USER_NOT_FOUND" -> {
                "Il n'y a pas d'utilisateur correspondant à l'adresse email."
            }
            else -> {
                "Une erreur inconnue s'est produite."
            }
        }
    }


    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "TRIVIAL PURSUIT",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                }
            })
    }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Mot de passe oublié ?", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            )

            Spacer(modifier = Modifier.height(30.dp))
            if (errorMessage.isNotEmpty()) Text(
                errorMessage, color = MaterialTheme.colorScheme.error, style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            ) else Spacer(modifier = Modifier.height(15.dp))
            Spacer(modifier = Modifier.height(30.dp))

            CustomOutlinedTextField(
                value = email,
                isErrorValue = !emailValid,
                onValueChange = { email = it },
                label = { Text(text = "Mail") },
            )
            Spacer(modifier = Modifier.height(64.dp))
            CustomButton(
                title = "Envoyer",
                onClick = {
                    emailValid = email.matches(emailRegex)
                    if (emailValid) {
                        viewModel.resetPassword(email.trim())
                    } else {
                        errorMessage = "Il y a une erreur de saisie"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )

        }
    }

}
