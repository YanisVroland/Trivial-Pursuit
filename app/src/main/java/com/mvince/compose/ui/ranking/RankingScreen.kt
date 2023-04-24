package com.mvince.compose.ui.ranking

import android.annotation.SuppressLint
import android.widget.TableLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
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
import com.mvince.compose.ui.signin.SigninViewModel
import com.mvince.compose.ui.theme.linkColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RankingScreen(navController: NavHostController) {

    val viewModel = hiltViewModel<SigninViewModel>()
    var user by remember { mutableStateOf("") }
    data class member(val name: String, val score: Int)
    val test = listOf<member>(
        member("Pipoune", 500),
        member("Yanis", 500),
        member("David",500),
        member("You",1)
    );

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Trivial Pursuit",
                        textAlign = TextAlign.Center,
                    )
                }
            )
        },
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(test){
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable{ },
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            buildAnnotatedString {
                                append(test.toString())
                            }
                        )
                    }
                }
            }
        }
    }
}