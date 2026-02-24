package com.petlink.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView

@Composable
fun PetLinkTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    SideEffect {
        // no-op: placeholder for system bar tweaks
    }
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
