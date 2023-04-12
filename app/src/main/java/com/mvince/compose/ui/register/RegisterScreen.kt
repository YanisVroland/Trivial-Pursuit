package com.mvince.compose.ui.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.mvince.compose.ui.components.NoNetwork

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<RegisterViewModel>()
    val uiState = viewModel.uiState.collectAsState().value


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
        if (uiState.offline) {
            NoNetwork()
        } else {
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
                    value = uiState.email,
                    onValueChange = { uiState.email = it },
                    label = { Text(text = "Mail") },
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomOutlinedTextField(
                    value = uiState.password,
                    onValueChange = { uiState.password = it },
                    label = { Text(text = "Mot de passe") },
                    visualTransformation = if (uiState.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { uiState.showPassword = !uiState.showPassword }
                        ) {
                            Icon(
                                painter = if (uiState.showPassword) painterResource(id = R.drawable.visibility) else painterResource(
                                    id = R.drawable.visibility_off
                                ),
                                contentDescription = if (uiState.showPassword) "Hide password" else "Show password",
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                CustomOutlinedTextField(
                    value = uiState.password,
                    onValueChange = { uiState.password2 = it },
                    label = { Text(text = "Mot de passe ") },
                    visualTransformation = if (uiState.showPassword2) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { uiState.showPassword2 = !uiState.showPassword2 }
                        ) {
                            Icon(
                                painter = if (uiState.showPassword2) painterResource(id = R.drawable.visibility) else painterResource(
                                    id = R.drawable.visibility_off
                                ),
                                contentDescription = if (uiState.showPassword2) "Hide password" else "Show password",
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(64.dp))
                CustomButton(
                    title = "Valider",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
