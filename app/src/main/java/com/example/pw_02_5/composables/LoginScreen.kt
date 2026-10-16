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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pw_02_5.Screen
import com.example.pw_02_5.components.ErrorMessage
import com.example.pw_02_5.components.InputField
import com.example.pw_02_5.components.MenuButton
import com.example.pw_02_5.components.ScreenTitle
import com.example.pw_02_5.data.checkEmail
import com.example.pw_02_5.data.loginUser

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var showErrors by rememberSaveable { mutableStateOf(false) }

    var loginError by rememberSaveable { mutableStateOf<String?>(null) }

    val emailError = if (showErrors || email.isNotEmpty()) checkEmail(email) else null
    val passwordError = if (showErrors && password.isEmpty()) "Введіть пароль" else null

    val onLoginClick = {
        showErrors = true
        loginError = null
        if (checkEmail(email) == null && password.isNotEmpty()) {
            if (loginUser(email, password)) {
                navController.popBackStack(Screen.MainMenu.route, inclusive = false)
            } else {
                loginError = "Неправильний email або пароль"
            }
        }
    }

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
                    .widthIn(max = 500.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenTitle("Вхід")

                Text(
                    text = "Увійдіть у свій акаунт",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputField(
                    value = email,
                    onValueChange = {
                        email = it
                        loginError = null
                    },
                    label = "Email",
                    helperText = "Наприклад: name@gmail.com",
                    errorText = emailError,
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(4.dp))

                InputField(
                    value = password,
                    onValueChange = {
                        password = it
                        loginError = null
                    },
                    label = "Пароль",
                    helperText = "Введіть пароль від акаунта",
                    errorText = passwordError,
                    isPassword = true,
                    keyboardType = KeyboardType.Password
                )

                loginError?.let { ErrorMessage(it) }

                Spacer(modifier = Modifier.height(8.dp))

                if (isLargeScreen) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MenuButton("Увійти", modifier = Modifier.weight(1f)) { onLoginClick() }
                        MenuButton("Реєстрація", modifier = Modifier.weight(1f), isOutlined = true) {
                            navController.navigate(Screen.Register.route) {
                                popUpTo(Screen.MainMenu.route)
                            }
                        }
                    }
                } else {
                    MenuButton("Увійти", modifier = Modifier.fillMaxWidth()) { onLoginClick() }
                    MenuButton("Реєстрація", modifier = Modifier.fillMaxWidth(), isOutlined = true) {
                        navController.navigate(Screen.Register.route) {
                            popUpTo(Screen.MainMenu.route)
                        }
                    }
                }
            }
        }
    }
}