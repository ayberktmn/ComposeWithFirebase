package com.ayberk.composeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayberk.composeapp.models.city.City
import com.ayberk.composeapp.models.city.CityItem
import com.ayberk.composeapp.util.Resource
import com.ayberk.composeapp.viewmodel.countrylistviewmodel

@Composable
fun countrydetails(navHostController: NavHostController, countryCode: String, viewModel: countrylistviewmodel = hiltViewModel()) {

    val cityItem = produceState<Resource<City>>(initialValue = Resource.Loading()) {
        value = viewModel.getCity(countryCode)
    }.value

    CityList(cities = cityItem.data ?: emptyList()) // İlleri sıralayın
}

@Composable
fun CityList(cities: List<CityItem>) {
    LazyColumn {
        items(cities) { city ->
            CityItem(city = city)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun CityItem(city: CityItem) {
    Spacer(modifier = Modifier.height(5.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray) // Kenarlıkları ekleyin
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .clickable {
                    // Şehre tıklama işlevini burada tanımlayabilirsiniz.
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.city), // Resminizi drawable'dan alın
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally // Metni yatayda ortala
            ) {
                Text(
                    text = city.SehirAdi,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow,
                    textAlign = TextAlign.Center // Metni dikeyde ortala
                )
                Text(
                    text = city.SehirAdiEn,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center // Metni dikeyde ortala
                )
            }
        }
    }
}






