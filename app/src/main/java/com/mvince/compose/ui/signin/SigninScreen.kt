package com.mvince.compose.ui.signin

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.R
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.components.CustomButton
import com.mvince.compose.ui.components.CustomOutlinedTextField
import com.mvince.compose.ui.theme.linkColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SigninViewModel>()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var emailValid by remember { mutableStateOf(true) }
    var passwordValid by remember { mutableStateOf(true) }

    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w+([.-]?\\w+)*\$")
    val passwordRegex = Regex("^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-]).{6,}\$")

    val authResource = viewModel.signinFlow.collectAsState().value
    val errorFlow = viewModel.errorFlow.collectAsState().value

    LaunchedEffect(authResource) {
        authResource?.let {
            navController.navigate(Route.MAINPAGE)
        }
    }

    if(errorFlow !=null){
        errorMessage = when (errorFlow.errorCode) {
            "ERROR_WRONG_PASSWORD" -> {
                "Le mot de passe n'est pas valide."
            }
            "ERROR_INVALID_EMAIL" -> {
                "L'adresse e-mail n'est pas valide."
            }
            "ERROR_USER_DISABLED" -> {
                "L'utilisateur correspondant à l'email donné a été désactivé."
            }
            "ERROR_USER_NOT_FOUND" -> {
                "Utilisateur non trouvé."
            }
            else -> {
                "Une erreur inconnue s'est produite."
            }
        }
    }

    fun inputVerification(): Boolean {
        emailValid = email.matches(emailRegex)
        passwordValid = password.matches(passwordRegex)

        return emailValid && passwordValid
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "TRIVIAL PURSUIT",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold                    )
                }
            )
        },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Connexion", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            )

            Spacer(modifier = Modifier.height(30.dp))
            if (errorMessage.isNotEmpty()) Text(
                errorMessage,color = MaterialTheme.colorScheme.error,  style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            )else Spacer(modifier = Modifier.height(15.dp))

            Spacer(modifier = Modifier.height(30.dp))

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Mail") },
                keyboardType = KeyboardType.Email,
                isErrorValue = !emailValid,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Mot de passe") },
                keyboardType = KeyboardType.Password,
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
            Text("Régle : 6 caractères dont un spécial et un 1 nombre", style = TextStyle(fontSize = 10.sp, fontStyle = FontStyle.Italic))

            Spacer(modifier = Modifier.height(16.dp))

            ClickableText(
                text = AnnotatedString("Mot de passe oublié ?"),
                onClick = {
                    navController.navigate(Route.FORGOTPASSWORD)
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    color = linkColor
                ),
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(64.dp))

            CustomButton(
                title = "Valider",
                onClick = {
                    if(inputVerification()) {
                        viewModel.loginUser(email.trim(), password)
                    }else{
                        errorMessage = "Il y a une erreur de saisie"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(32.dp))

            ClickableText(
                text = AnnotatedString("S'inscrire ?"),
                onClick = {
                    navController.navigate(Route.REGISTER)
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    color = linkColor
                ),
            )

        }
    }
}
