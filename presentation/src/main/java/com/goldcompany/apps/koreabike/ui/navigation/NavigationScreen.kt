package com.goldcompany.apps.koreabike.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.DefaultKBikeTopAppBar
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
        SearchNavigationView()
    }
}

@Composable
private fun SearchNavigationView() {
    Row(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(start = 10.dp)
        ) {
            TextField(
                modifier = Modifier,
                value = "",
                onValueChange = {}
            )
            TextField(
                modifier = Modifier,
                value = "",
                onValueChange = {}
            )
        }
        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxHeight()
                .weight(1f),
            shape = Shapes(medium = RoundedCornerShape(6.dp)).medium,
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