package com.goldcompany.apps.koreabike.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val colorPalette = lightColors(
    primary = Blue,
    primaryVariant = BlueAccent,
    background = White,
    surface = White
)

@Composable
fun KBikeComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colorPalette,
        content = content
    )
}