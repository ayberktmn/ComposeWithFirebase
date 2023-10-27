package com.ayberk.composeapp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ayberk.composeapp.models.CountryItem
import com.ayberk.composeapp.viewmodel.countrylistviewmodel

@Composable
fun anasayfa(navHostController: NavHostController){

    var allowBackNavigation by remember { mutableStateOf(true) }
    BackHandler(enabled = allowBackNavigation){}
    CryptoList(navHostController)
}

@Composable
fun CryptoList(
    navHostController: NavHostController,
    viewModel: countrylistviewmodel = hiltViewModel()
) {
    val countryList by remember { viewModel.countryList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    CountryListView(countrys = countryList,navHostController = navHostController)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = Color.Blue)
        }
        if(errorMessage.isNotEmpty()) {
            RetryView(error = errorMessage) {
                viewModel.loadCountry()
            }
        }
    }

}

@Composable
fun CountryListView(countrys: List<CountryItem>,navHostController: NavHostController) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(countrys) { country ->
            CoutryRow(navHostController = navHostController, country = country)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun CoutryRow(navHostController: NavHostController, country: CountryItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray) // Kenarlıkları ekleyin
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black)
                .clickable {

                    navHostController.navigate("country/${country.UlkeID}")
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.country), // Resminizi drawable'dan alın
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
            Column {
                Text(
                    text = country.UlkeAdi,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.Start), // Yazıyı sola hizala
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow,
                )
                Text(
                    text = country.UlkeAdiEn,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.Start), // Yazıyı sola hizala
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun RetryView(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}