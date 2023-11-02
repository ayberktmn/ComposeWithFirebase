package com.ayberk.composeapp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ayberk.composeapp.models.city.CityItem
import com.ayberk.composeapp.models.town.Town
import com.ayberk.composeapp.models.town.TownItem
import com.ayberk.composeapp.util.Resource
import com.ayberk.composeapp.viewmodel.countrylistviewmodel

@SuppressLint("SuspiciousIndentation")
@Composable
fun townScreen(navHostController: NavHostController, townCode: String, viewModel: countrylistviewmodel = hiltViewModel()) {

    val townItem = produceState<Resource<Town>>(initialValue = Resource.Loading()) {
        value = viewModel.getTown(townCode)
    }.value

      TownList(navHostController,towns = townItem.data ?: emptyList())

}

@Composable
fun TownList(navHostController: NavHostController,towns: List<TownItem>){
  TownItemGrid(navHostController = navHostController,towns )
}

@Composable
fun TownItemGrid(navHostController: NavHostController,towns: List<TownItem>){
    LazyVerticalGrid(GridCells.Fixed(2)){
        items(towns) { town ->
            com.ayberk.composeapp.TownItem(navHostController = navHostController, town = town)
        }
    }
}

@Composable
fun TownItem(navHostController: NavHostController, town: TownItem) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium // Yuvarlatılmış kenarlar
        ) {
            Column(
                modifier = Modifier
                    .clickable {
                         navHostController.navigate("times/${town.IlceID}")
                    }
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.house),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = town.IlceAdi,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
}