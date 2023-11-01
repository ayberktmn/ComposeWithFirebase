package com.ayberk.composeapp

import android.content.pm.LauncherApps
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

    CityList(navHostController,cities = cityItem.data ?: emptyList()) // İlleri sıralayın
}

@Composable
fun CityList(navHostController: NavHostController,cities: List<CityItem>) {
    CityItemGrid(navHostController, cities)
}
@Composable
fun CityItemGrid(navHostController: NavHostController, cities: List<CityItem>) {
    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(cities) { city ->
            CityItem(navHostController = navHostController, city = city)
        }
    }
}

@Composable
fun CityItem(navHostController: NavHostController, city: CityItem) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .height(70.dp)
            .border(1.dp, Color.Red)
            .background(Color.Black)
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { navHostController.navigate("town/${city.SehirID}")
            Toast.makeText(context,"${city.SehirAdi} İlçeleri Listeleniyor",Toast.LENGTH_SHORT).show()
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.city),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 8.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = city.SehirAdi,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}



