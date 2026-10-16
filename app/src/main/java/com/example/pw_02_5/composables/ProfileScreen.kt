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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pw_02_5.Screen
import com.example.pw_02_5.components.ErrorMessage
import com.example.pw_02_5.components.InfoRow
import com.example.pw_02_5.components.MenuButton
import com.example.pw_02_5.components.ScreenTitle
import com.example.pw_02_5.data.findUser
import com.example.pw_02_5.data.logoutUser

@Composable
fun ProfileScreen(navController: NavHostController, email: String) {
    val user = findUser(email)

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
                ScreenTitle("Мій профіль")

                if (user != null) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            InfoRow("Ім'я:", user.name)
                            InfoRow("Email:", user.email)
                            // Пароль не показуємо, тільки зірочки
                            InfoRow("Пароль:", "*".repeat(user.password.length))
                        }
                    }
                } else {
                    ErrorMessage("Користувача не знайдено")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isLargeScreen) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MenuButton("Назад до меню", modifier = Modifier.weight(1f)) {
                            navController.popBackStack()
                        }
                        MenuButton("Вийти", modifier = Modifier.weight(1f), isOutlined = true) {
                            logoutUser()
                            navController.popBackStack(Screen.MainMenu.route, inclusive = false)
                        }
                    }
                } else {
                    MenuButton("Назад до меню", modifier = Modifier.fillMaxWidth()) {
                        navController.popBackStack()
                    }
                    MenuButton("Вийти", modifier = Modifier.fillMaxWidth(), isOutlined = true) {
                        logoutUser()
                        navController.popBackStack(Screen.MainMenu.route, inclusive = false)
                    }
                }
            }
        }
    }
}