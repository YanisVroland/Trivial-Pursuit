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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mvince.compose.ui.components.CustomButton
import com.mvince.compose.ui.components.CustomOutlinedTextField
import com.mvince.compose.R
import com.mvince.compose.ui.Route
import androidx.navigation.NavHostController
import com.mvince.compose.ui.theme.linkColor


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SigninViewModel>()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val authResource = viewModel.signupFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "APP TEST",
                        textAlign = TextAlign.Center,
                    )
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

                Spacer(modifier = Modifier.height(64.dp))

                CustomOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Mail") },
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Mot de passe") },
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
                    onClick = { viewModel.loginUser(email, password) },
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
