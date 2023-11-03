package com.ayberk.composeapp

import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl

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
    LazyVerticalGrid(GridCells.Fixed(1)){
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
            modifier = Modifier.padding(5.dp)
        ) {
            Row(
                modifier = Modifier.padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = times.MiladiTarihUzun,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 8.dp)
                        .padding(start = 8.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.mosque), // Değiştirmeniz gereken kısım
                    contentDescription = null,
                    tint = Color.Black, // İkon rengi
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Top)
                        .padding(8.dp)
                )
            }
            PrayerTime("İmsak Saati:", times.Imsak)
            PrayerTime("Öğle Saati:", times.Ogle)
            PrayerTime("İkindi Saati:", times.Ikindi)
            PrayerTime("Akşam Saati:", times.Aksam)
            PrayerTime("Yatsı Saati:", times.Yatsi)
        }
    }
}
@Composable
fun PrayerTime(title: String, time: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        Text(
            text = time,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
                .padding(top = 8.dp)
        )

    }
}

