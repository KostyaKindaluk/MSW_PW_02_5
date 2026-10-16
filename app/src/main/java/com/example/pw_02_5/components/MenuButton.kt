package com.example.pw_02_5.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(
    text: String,
    modifier: Modifier = Modifier,
    isOutlined: Boolean = false,
    onClick: () -> Unit
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Text(text)
        }
    } else {
        Button(
            onClick = onClick,
            modifier = modifier.padding(vertical = 4.dp)
        ) {
            Text(text)
        }
    }
}