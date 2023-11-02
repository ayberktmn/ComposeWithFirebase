package com.ayberk.composeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayberk.composeapp.models.times.Times
import com.ayberk.composeapp.models.times.TimesItem
import com.ayberk.composeapp.util.Resource
import com.ayberk.composeapp.viewmodel.countrylistviewmodel

@Composable
fun timesScreen(navHostController: NavHostController, timesCode: String, viewModel: countrylistviewmodel = hiltViewModel()){
    val timesItem = produceState<Resource<Times>>(initialValue = Resource.Loading()) {
        value = viewModel.getTimes(timesCode)
    }.value
    TimesList(navHostController,times = timesItem.data ?: emptyList())
}

@Composable
fun TimesList(navHostController: NavHostController,times: List<TimesItem>){
    TimesItemGrid(navHostController = navHostController,times )
}


@Composable
fun TimesItemGrid(navHostController: NavHostController,times: List<TimesItem>){
    LazyVerticalGrid(GridCells.Fixed(2)){
        items(times) { times ->
            TimesItem(navHostController = navHostController, times = times)
        }
    }
}

@Composable
fun TimesItem(navHostController: NavHostController, times: TimesItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = times.Gunes,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

        }
    }
}
