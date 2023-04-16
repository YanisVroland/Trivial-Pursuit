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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.ui.components.CustomButton
import com.mvince.compose.ui.components.CustomOutlinedTextField
import com.mvince.compose.ui.components.NoNetwork


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<ForgotPasswordViewModel>()
    var email by remember { mutableStateOf("") }

    val boolResource = viewModel.resetPasswordFlow.collectAsState()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Test APP") }, navigationIcon = {
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
                Spacer(modifier = Modifier.height(64.dp))
                CustomOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Mail") },
                )
                Spacer(modifier = Modifier.height(64.dp))
                CustomButton(
                    title = "Envoyer",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                )

            }
    }

}
