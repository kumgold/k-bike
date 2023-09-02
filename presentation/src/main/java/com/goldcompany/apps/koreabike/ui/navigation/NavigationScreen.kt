package com.goldcompany.apps.koreabike.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.DefaultKBikeTopAppBar
import com.goldcompany.apps.koreabike.compose.LightGray
import com.goldcompany.apps.koreabike.util.KBikeTypography

@Composable
fun NavigationScreen(
    viewModel: NavigationViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        DefaultKBikeTopAppBar(
            title = R.string.navigation,
            navigateBack = {
                navController.popBackStack()
            }
        )
        SearchNavigationView(
            viewModel = viewModel
        )
    }
}

@Composable
private fun SearchNavigationView(
    viewModel: NavigationViewModel
) {
    val searchStartAddress by viewModel.searchStartAddress
    val searchEndAddress by viewModel.searchEndAddress
    val defaultMargin = 10.dp

    Row(
        modifier = Modifier
            .padding(top = defaultMargin)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        val shape = RoundedCornerShape(6.dp)

        Column(
            modifier = Modifier
                .padding(start = defaultMargin)
                .weight(2f)
        ) {
            val modifier = Modifier.fillMaxWidth().border(1.dp, LightGray, shape)
            val colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.white),
                textColor = colorResource(id = R.color.black),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )

            TextField(
                modifier = modifier,
                value = searchStartAddress,
                shape = shape,
                colors = colors,
                onValueChange = { address ->
                    viewModel.setSearchStartAddress(address)
                    viewModel.setIsStart(true)
                }
            )
            Spacer(modifier = Modifier.height(defaultMargin))
            TextField(
                modifier = modifier,
                value = searchEndAddress,
                shape = shape,
                colors = colors,
                onValueChange = { address ->
                    viewModel.setSearchEndAddress(address)
                    viewModel.setIsStart(false)
                }
            )
        }
        Button(
            modifier = Modifier
                .padding(horizontal = defaultMargin)
                .fillMaxHeight()
                .weight(1f),
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.colorPrimary),
                contentColor = colorResource(id = R.color.white)
            ),
            onClick = {},
            content = {
                Text(
                    text = stringResource(id = R.string.navigation_button),
                    textAlign = TextAlign.Center,
                    style = KBikeTypography.button
                )
            }
        )
    }
}