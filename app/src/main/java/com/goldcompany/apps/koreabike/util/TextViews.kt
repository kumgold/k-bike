package com.goldcompany.apps.koreabike.util

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.goldcompany.apps.koreabike.R

@Composable
fun DefaultTextView(
    modifier: Modifier,
    @StringRes stringResource: Int
) {
    Text(
        text = stringResource(id = stringResource),
        modifier = modifier.fillMaxWidth(),
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

@Composable
fun AddressTextView(
    text: String
) {
    Text(
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}