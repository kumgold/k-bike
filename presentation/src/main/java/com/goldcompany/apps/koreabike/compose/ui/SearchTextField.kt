package com.goldcompany.apps.koreabike.compose.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.goldcompany.apps.koreabike.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    onSearch: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(searchText, isSearching) {
        if (!isSearching && searchText.isNotEmpty()) {
            delay(1000)
            focusManager.clearFocus()
            onSearch(searchText)
        }
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.default_margin_medium),
                vertical = dimensionResource(id = R.dimen.default_margin)
            ),
        value = searchText,
        onValueChange = {
            isSearching = false
            searchText = it
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                isSearching = true
                focusManager.clearFocus()
                onSearch(searchText)
            }
        ),
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    searchText = ""
                },
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        },
        label = { Text(stringResource(id = R.string.search)) },
        singleLine = true
    )
}

@Preview
@Composable
private fun SearchTextViewPreview() {
    SearchTextField {

    }
}