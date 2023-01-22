package com.goldcompany.apps.koreabike.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.goldcompany.apps.koreabike.R

@Composable
fun CircularLoadingView(modifier: Modifier) {
    Box {
        CircularProgressIndicator(
            modifier = modifier,
            color = colorResource(id = R.color.colorPrimary)
        )
    }
}