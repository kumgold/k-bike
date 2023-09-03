package com.goldcompany.apps.koreabike.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.goldcompany.apps.koreabike.R

@Composable
fun DefaultTextView(
    modifier: Modifier
) {
    Text(
        text = stringResource(id = R.string.init_page),
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ErrorMessageTextView(
    modifier: Modifier
) {
    Text(
        text = stringResource(id = R.string.error_code),
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}