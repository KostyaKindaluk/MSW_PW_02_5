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
import com.example.pw_02_5.components.PasswordHints
import com.example.pw_02_5.components.ScreenTitle
import com.example.pw_02_5.data.checkEmail
import com.example.pw_02_5.data.checkName
import com.example.pw_02_5.data.checkPassword
import com.example.pw_02_5.data.checkRepeatPassword
import com.example.pw_02_5.data.registerUser

@Composable
fun RegisterScreen(navController: NavHostController) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }

    var showErrors by rememberSaveable { mutableStateOf(false) }

    var registerError by rememberSaveable { mutableStateOf<String?>(null) }

    val nameError = if (showErrors || name.isNotEmpty()) checkName(name) else null
    val emailError = if (showErrors || email.isNotEmpty()) checkEmail(email) else null
    val passwordError = if (showErrors || password.isNotEmpty()) checkPassword(password) else null
    val repeatError = if (showErrors || repeatPassword.isNotEmpty()) {
        checkRepeatPassword(password, repeatPassword)
    } else {
        null
    }

    val onRegisterClick = {
        showErrors = true
        registerError = null
        if (checkName(name) == null &&
            checkEmail(email) == null &&
            checkPassword(password) == null &&
            checkRepeatPassword(password, repeatPassword) == null
        ) {
            val result = registerUser(name, email, password)
            if (result == null) {
                navController.popBackStack(Screen.MainMenu.route, inclusive = false)
            } else {
                registerError = result
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
                    .widthIn(max = if (isLargeScreen) 800.dp else 500.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenTitle("Реєстрація")

                Text(
                    text = "Створіть новий акаунт",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isLargeScreen) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        PersonalDataFields(
                            name = name,
                            onNameChange = { name = it; registerError = null },
                            nameError = nameError,
                            email = email,
                            onEmailChange = { email = it; registerError = null },
                            emailError = emailError,
                            modifier = Modifier.weight(1f)
                        )
                        PasswordFields(
                            password = password,
                            onPasswordChange = { password = it },
                            passwordError = passwordError,
                            repeatPassword = repeatPassword,
                            onRepeatPasswordChange = { repeatPassword = it },
                            repeatError = repeatError,
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    PersonalDataFields(
                        name = name,
                        onNameChange = { name = it; registerError = null },
                        nameError = nameError,
                        email = email,
                        onEmailChange = { email = it; registerError = null },
                        emailError = emailError
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PasswordFields(
                        password = password,
                        onPasswordChange = { password = it },
                        passwordError = passwordError,
                        repeatPassword = repeatPassword,
                        onRepeatPasswordChange = { repeatPassword = it },
                        repeatError = repeatError
                    )
                }

                registerError?.let { ErrorMessage(it) }

                Spacer(modifier = Modifier.height(8.dp))

                if (isLargeScreen) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        MenuButton("Зареєструватися", modifier = Modifier.weight(1f)) { onRegisterClick() }
                        MenuButton("Вже є акаунт? Увійти", modifier = Modifier.weight(1f), isOutlined = true) {
                            // Замінюємо екран реєстрації на вхід (назад - у меню)
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.MainMenu.route)
                            }
                        }
                    }
                } else {
                    MenuButton("Зареєструватися", modifier = Modifier.fillMaxWidth()) { onRegisterClick() }
                    MenuButton("Вже є акаунт? Увійти", modifier = Modifier.fillMaxWidth(), isOutlined = true) {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.MainMenu.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PersonalDataFields(
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String?,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Особисті дані",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = name,
            onValueChange = onNameChange,
            label = "Ім'я",
            helperText = "Як до вас звертатися",
            errorText = nameError
        )
        Spacer(modifier = Modifier.height(4.dp))

        InputField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            helperText = "Наприклад: name@gmail.com",
            errorText = emailError,
            keyboardType = KeyboardType.Email
        )
    }
}

@Composable
private fun PasswordFields(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    repeatPassword: String,
    onRepeatPasswordChange: (String) -> Unit,
    repeatError: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Пароль",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Пароль",
            helperText = "Літера, цифра і спецсимвол",
            errorText = passwordError,
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        PasswordHints()

        InputField(
            value = repeatPassword,
            onValueChange = onRepeatPasswordChange,
            label = "Повторіть пароль",
            helperText = "Введіть пароль ще раз",
            errorText = repeatError,
            isPassword = true,
            keyboardType = KeyboardType.Password
        )
    }
}