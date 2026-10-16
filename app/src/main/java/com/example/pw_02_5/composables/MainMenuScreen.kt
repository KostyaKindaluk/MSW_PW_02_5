package com.example.pw_02_5.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pw_02_5.Screen
import com.example.pw_02_5.components.InfoRow
import com.example.pw_02_5.components.MenuButton
import com.example.pw_02_5.components.ScreenTitle
import com.example.pw_02_5.data.currentUser
import com.example.pw_02_5.data.logoutUser
import com.example.pw_02_5.data.users

@Composable
fun MainMenuScreen(navController: NavHostController) {
    val user = currentUser

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isLargeScreen = maxWidth > 600.dp

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .heightIn(min = maxHeight)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenTitle("Головне меню")

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (user != null) {
                            Text(
                                text = "Вітаємо, ${user.name}!",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            InfoRow("Статус:", "Авторизовано")
                            InfoRow("Email:", user.email)
                        } else {
                            InfoRow("Статус:", "Не авторизовано")
                        }
                        InfoRow("Користувачів у системі:", users.size.toString())
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (user != null) {
                    if (isLargeScreen) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            MenuButton("Мій профіль", modifier = Modifier.weight(1f)) {
                                navController.navigate("${Screen.Profile.route}/${user.email}")
                            }
                            MenuButton("Вийти", modifier = Modifier.weight(1f), isOutlined = true) {
                                logoutUser()
                            }
                        }
                    } else {
                        MenuButton("Мій профіль", modifier = Modifier.fillMaxWidth()) {
                            navController.navigate("${Screen.Profile.route}/${user.email}")
                        }
                        MenuButton("Вийти", modifier = Modifier.fillMaxWidth(), isOutlined = true) {
                            logoutUser()
                        }
                    }
                } else {
                    if (isLargeScreen) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            MenuButton("Вхід", modifier = Modifier.weight(1f)) {
                                navController.navigate(Screen.Login.route)
                            }
                            MenuButton("Реєстрація", modifier = Modifier.weight(1f), isOutlined = true) {
                                navController.navigate(Screen.Register.route)
                            }
                        }
                    } else {
                        MenuButton("Вхід", modifier = Modifier.fillMaxWidth()) {
                            navController.navigate(Screen.Login.route)
                        }
                        MenuButton("Реєстрація", modifier = Modifier.fillMaxWidth(), isOutlined = true) {
                            navController.navigate(Screen.Register.route)
                        }
                    }
                }
            }
        }
    }
}