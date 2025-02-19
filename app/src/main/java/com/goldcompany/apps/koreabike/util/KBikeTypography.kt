package com.goldcompany.apps.koreabike.util

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val KBikeTypography = Typography(
    h1 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    ),
    button = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    caption = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    )
)