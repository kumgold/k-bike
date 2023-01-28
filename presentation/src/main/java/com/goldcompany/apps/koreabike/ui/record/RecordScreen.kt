package com.goldcompany.apps.koreabike.ui.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun RecordScreen(
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {

        }
    }
}