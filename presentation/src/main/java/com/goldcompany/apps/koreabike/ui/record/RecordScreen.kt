package com.goldcompany.apps.koreabike.ui.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.goldcompany.apps.koreabike.R
import com.goldcompany.apps.koreabike.compose.DefaultKBikeTopAppBar

@Composable
fun RecordScreen(
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            DefaultKBikeTopAppBar(title = R.string.record_screen)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = colorResource(id = R.color.colorPrimary),
                contentColor = colorResource(id = R.color.white)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_floating_add),
                    contentDescription = null,
                    tint = colorResource(id = R.color.white)
                )
            }
        }
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {

        }
    }
}