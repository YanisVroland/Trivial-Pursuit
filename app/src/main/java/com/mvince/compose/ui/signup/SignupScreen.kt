package com.mvince.compose.ui.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.ui.components.CustomButton
import com.mvince.compose.ui.components.CustomOutlinedTextField
import com.mvince.compose.R
import com.mvince.compose.ui.Route
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SignupViewModel>()

    var email by remember { mutableStateOf("") }
    var pseudo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showPassword2 by remember { mutableStateOf(false) }
    var emailValid by remember { mutableStateOf(true) }
    var pseudoValid by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var passwordValid by remember { mutableStateOf(true) }
    var password2Valid by remember { mutableStateOf(true) }

    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w+([.-]?\\w+)*\$")
    val passwordRegex = Regex("^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{6,}\$")

    val authResource = viewModel.isAuthentificated.collectAsState().value
    val errorFlow = viewModel.errorFlow.collectAsState().value

    LaunchedEffect(authResource) {
        authResource?.let { authResource ->
            if (authResource != null) {
                navController.navigate(Route.LOGIN)
            }
        }
    }

    if (errorFlow != null) {
        errorMessage = when (errorFlow.errorCode) {
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                "Cette adresse e-mail est déjà associée à un autre compte."
            }
            "ERROR_INVALID_EMAIL" -> {
                "L'adresse e-mail n'est pas valide."
            }
            "ERROR_WEAK_PASSWORD" -> {
                "Le mot de passe n'est pas assez sécurisé."
            }
            else -> {
                "Une erreur inconnue s'est produite."
            }
        }
    }

    fun inputVerification(): Boolean {
        emailValid = email.matches(emailRegex)
        passwordValid = password.matches(passwordRegex)
        password2Valid = password2.matches(passwordRegex) && password == password2
        pseudoValid = pseudo.isNotEmpty()

        return emailValid && passwordValid;
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
                "Inscription", style = TextStyle(
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

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = pseudo,
                isErrorValue = !pseudoValid,
                onValueChange = { pseudo = it },
                label = { Text(text = "Pseudo") },
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Mot de passe") },
                isErrorValue = !passwordValid,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword }
                    ) {
                        Icon(
                            painter = if (showPassword) painterResource(id = R.drawable.visibility) else painterResource(
                                id = R.drawable.visibility_off
                            ),
                            contentDescription = if (showPassword) "Hide password" else "Show password",
                        )
                    }
                }
            )
            Text(
                "Régle : 6 caractères dont un spécial et un 1 nombre",
                style = TextStyle(fontSize = 10.sp, fontStyle = FontStyle.Italic)
            )


            Spacer(modifier = Modifier.height(16.dp))
            CustomOutlinedTextField(
                value = password2,
                onValueChange = { password2 = it },
                label = { Text(text = "Mot de passe ") },
                isErrorValue = !password2Valid,
                visualTransformation = if (showPassword2) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword2 = !showPassword2 }
                    ) {
                        Icon(
                            painter = if (showPassword2) painterResource(id = R.drawable.visibility) else painterResource(
                                id = R.drawable.visibility_off
                            ),
                            contentDescription = if (showPassword2) "Hide password" else "Show password",
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(64.dp))
            CustomButton(
                title = "Valider",
                onClick = {
                    if (inputVerification()) {
                        viewModel.signupUser(email.trim(), password, pseudo.trim())
                    } else {
                        errorMessage = "Il y a une erreur de saisie"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
