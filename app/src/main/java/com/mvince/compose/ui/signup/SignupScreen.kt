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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.ui.components.CustomButton
import com.mvince.compose.ui.components.CustomOutlinedTextField
import com.mvince.compose.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<SignupViewModel>()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showPassword2 by remember { mutableStateOf(false) }

    val authResource = viewModel.signupFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Test APP") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                    }
                })
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
                "Inscription", style = TextStyle(
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
            CustomOutlinedTextField(
                value = password,
                onValueChange = { password2 = it },
                label = { Text(text = "Mot de passe ") },
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
                onClick = { viewModel.signupUser(email, password) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
