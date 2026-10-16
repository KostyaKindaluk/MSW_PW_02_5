package com.example.pw_02_5.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pw_02_5.data.MIN_PASSWORD_LENGTH

@Composable
fun PasswordHints() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 8.dp)
    ) {
        Text(
            text = "Вимоги до пароля:",
            style = MaterialTheme.typography.labelLarge
        )
        Text("- мінімум $MIN_PASSWORD_LENGTH символів", style = MaterialTheme.typography.bodySmall)
        Text("- хоча б одна літера", style = MaterialTheme.typography.bodySmall)
        Text("- хоча б одна цифра", style = MaterialTheme.typography.bodySmall)
        Text("- хоча б один спецсимвол (!@#$%...)", style = MaterialTheme.typography.bodySmall)
    }
}