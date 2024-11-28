package com.rupak.analyticsdemo.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rupak.analyticsdemo.presentation.common.CustomButton
import com.rupak.analyticsdemo.presentation.intents.HomeIntents
import com.rupak.analyticsdemo.presentation.viewmodel.MainActivityViewModel


@Composable
fun HomeScreen(
    mainActivityViewModel: MainActivityViewModel
) {
    val uiState by mainActivityViewModel.homeUiState.collectAsState()
    Column {


        uiState.eventList?.let { eventList ->
            LazyColumn {
                items(eventList) { event ->
                    Text(event.name, color = Color.Red)
                }
            }
        }

        uiState.sessionId?.let {
            CustomButton({
                mainActivityViewModel.apply {
                    handleIntents(HomeIntents.OnSessionEndClick(it))
                }
            }, text = "Stop Recording Session", textColor = Color.White)
        } ?: CustomButton({
            mainActivityViewModel.apply {
                handleIntents(HomeIntents.OnSessionStartClick)
            }
        }, text = "Start Recording Session", textColor = Color.White)


    }
}