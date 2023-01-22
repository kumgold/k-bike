package com.goldcompany.apps.koreabike.util

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goldcompany.apps.koreabike.R

enum class SearchAppBarState {
    OPENED,
    CLOSED
}

@Composable
fun ListPageTopAppBar(
    @StringRes title: Int,
    navigateBack: () -> Unit
) {
    TopAppBar(
        title = { 
            Text(
                text = stringResource(id = title),
                color = colorResource(id = R.color.white)
            )
        },
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = colorResource(id = R.color.colorPrimary)
    )
}

@Composable
fun SearchAppBar(
    @StringRes title: Int,
    place: String,
    searchAppBarState: SearchAppBarState,
    navigateBack: () -> Unit,
    onClickForSearch: () -> Unit,
    onClickForSearchClose: () -> Unit,
    onSearchPlaceChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    when (searchAppBarState) {
        SearchAppBarState.OPENED -> {
            SearchTextField(
                place = place,
                onClickForSearchClose = onClickForSearchClose,
                onSearchPlaceChange = onSearchPlaceChange,
                onSearch = onSearch
            )
        }
        SearchAppBarState.CLOSED -> {
            DefaultSearchAppBar(
                title = title,
                onClickForSearch = onClickForSearch,
                navigateBack = navigateBack
            )
        }
    }
}

@Composable
private fun DefaultSearchAppBar(
    @StringRes title: Int,
    onClickForSearch: () -> Unit,
    navigateBack: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{ onClickForSearch() },
                text = stringResource(id = title),
                color = colorResource(id = R.color.white)
            )
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_to_page),
                    tint = colorResource(id = R.color.white)
                )
            }
        },
        backgroundColor = colorResource(id = R.color.colorPrimary),
        actions = {
            IconButton(
                onClick = onClickForSearch
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = colorResource(id = R.color.white)
                )
            }
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchTextField(
    place: String,
    onSearchPlaceChange: (String) -> Unit,
    onClickForSearchClose: () -> Unit,
    onSearch: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = colorResource(id = R.color.colorPrimary)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            modifier = Modifier.padding(horizontal = 4.dp),
            value = place,
            onValueChange = {
                onSearchPlaceChange(it)
            },
            textStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight(700)
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.white),
                backgroundColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.white),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                placeholderColor = colorResource(id = R.color.white)
            ),
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = colorResource(id = R.color.white)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = onClickForSearchClose) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                        tint = colorResource(id = R.color.white)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                    onSearch(place)
                }
            ),
            singleLine = true,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun ListPageTopAppBarPreview() {
    MaterialTheme {
        Surface {
            ListPageTopAppBar(R.string.app_name, { })
        }
    }
}

@Preview
@Composable
private fun SearchAppBarPreview() {
    MaterialTheme {
        Surface {
            SearchAppBar(
                title = R.string.app_name,
                place = "",
                searchAppBarState = SearchAppBarState.OPENED,
                navigateBack = {},
                onClickForSearch = {},
                onClickForSearchClose = {},
                onSearchPlaceChange = {},
                onSearch = {}
            )
        }
    }
}