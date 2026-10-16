package com.example.pw_02_5.data

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class User(
    val name: String,
    val email: String,
    val password: String
)

val users = mutableStateListOf(
    User("Test User", "test@gmail.com", "Test123!")
)

var currentUser by mutableStateOf<User?>(null)

const val MIN_PASSWORD_LENGTH = 8

fun checkName(name: String): String? {
    if (name.isBlank()) {
        return "Введіть ім'я"
    }
    if (name.trim().length < 2) {
        return "Ім'я має містити хоча б 2 символи"
    }
    return null
}

fun checkEmail(email: String): String? {
    if (email.isBlank()) {
        return "Введіть email"
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
        return "Неправильний формат email"
    }
    return null
}

fun checkPassword(password: String): String? {
    if (password.isEmpty()) {
        return "Введіть пароль"
    }
    if (password.length < MIN_PASSWORD_LENGTH) {
        return "Пароль має містити мінімум $MIN_PASSWORD_LENGTH символів"
    }
    if (!password.any { it.isLetter() }) {
        return "Пароль має містити хоча б одну літеру"
    }
    if (!password.any { it.isDigit() }) {
        return "Пароль має містити хоча б одну цифру"
    }
    if (!password.any { !it.isLetterOrDigit() && !it.isWhitespace() }) {
        return "Пароль має містити хоча б один спецсимвол (!@#$%...)"
    }
    return null
}

fun checkRepeatPassword(password: String, repeatPassword: String): String? {
    if (repeatPassword.isEmpty()) {
        return "Повторіть пароль"
    }
    if (password != repeatPassword) {
        return "Паролі не співпадають"
    }
    return null
}

fun findUser(email: String): User? {
    return users.find { it.email.equals(email.trim(), ignoreCase = true) }
}

fun registerUser(name: String, email: String, password: String): String? {
    if (findUser(email) != null) {
        return "Користувач з таким email вже існує"
    }
    users.add(User(name.trim(), email.trim(), password))
    return null
}

fun loginUser(email: String, password: String): Boolean {
    val user = findUser(email)
    if (user != null && user.password == password) {
        currentUser = user
        return true
    }
    return false
}

// Вихід користувача
fun logoutUser() {
    currentUser = null
}